package com.a401.artwalk.view.record

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.text.AlphabeticIndex.Record
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.mapbox.maps.Style
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.utills.LocationPermissionHelper
import com.a401.artwalk.view.SampleActivity
import com.a401.artwalk.view.route.draw.ROUTE_COLOR
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearingSource
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location2
import java.lang.ref.WeakReference
import com.mapbox.geojson.Point
import com.mapbox.geojson.utils.PolylineUtils
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.cos
import java.lang.Math.sin
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.math.absoluteValue
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

@AndroidEntryPoint
class RecordFragment : UsingMapFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    private lateinit var statusReceiver: BroadcastReceiver
    private lateinit var durationReceiver: BroadcastReceiver
    private lateinit var locationReceiver: BroadcastReceiver

    lateinit var mainActivity: SampleActivity
    private var isRecordRunning = false

    private val arguments by navArgs<RecordFragmentArgs>()
    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var routeAnnotationManager: PolylineAnnotationManager
    private lateinit var pointAnnotaionManager: PointAnnotationManager
    private var timerTaskforPolyLine : Timer? = null

    private var curPoint : Point = Point.fromLngLat(0.0,0.0)
    private var lastLocation: Point? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as SampleActivity
    }

    override fun onResume() {
        super.onResume()

        sendCommandToForegroundService(RecordState.GET_STATUS)

        statusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                isRecordRunning = when(intent?.extras?.getSerializable(IS_RECORD_RUNNING) as RecordState){
                    RecordState.START -> true
                    else -> false
                }
                val timeElapsed = intent.getIntExtra(TIME_ELAPSED, 0)
                val totalLocation = intent.getSerializableExtra(TOTAL_LOCATION) as ArrayList<Location>

                updateLayout(isRecordRunning)
                updateDurationValue(timeElapsed)
                updateTotalAnnotation(totalLocation)
            }
        }

        durationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val timeElapsed = intent?.getIntExtra(TIME_ELAPSED, 0)!!
                updateDurationValue(timeElapsed)
            }
        }

        locationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val location = intent?.getParcelableExtra<Location>(LOCATION)
                if (location != null) {
                    updateRecord(location)
                }
            }
        }

        mainActivity.registerReceiver(statusReceiver, IntentFilter(RECORD_STATUS))
        mainActivity.registerReceiver(durationReceiver, IntentFilter(RECORD_TICK))
        mainActivity.registerReceiver(locationReceiver, IntentFilter(RECORD_LOCATION))
    }

    override fun onPause() {
        super.onPause()

        mainActivity.unregisterReceiver(statusReceiver)
        mainActivity.unregisterReceiver(durationReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMapView()
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setRoute()

        val onIndicatorPositionChangedListenerForDraw = OnIndicatorPositionChangedListener {
            curPoint = it
        }
        mapView.location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListenerForDraw)

        binding.imagebuttonRecordStartbutton.setOnClickListener {
            startOrPause()
        }

        recordViewModel.isSuccessSave.observe(viewLifecycleOwner) { isSuccessSave ->
            if(isSuccessSave) {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateTotalAnnotation (totalLocation: ArrayList<Location>) {

        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(
                totalLocation.map {  location ->
                    Point.fromLngLat(location.longitude, location.latitude)
                }
            )
            .withLineColor(R.color.main.toString())
            .withLineWidth(7.0)

        polylineAnnotationManager.create(polylineAnnotationOptions)
    }

    private fun updateRecord(location: Location) {

        if(lastLocation == null) lastLocation = curPoint

        setPolyline(lastLocation!!, location)
//            polylineAnnotationManager.create(
//                PolylineAnnotationOptions()
//                    .withPoints(
//                        listOf(lastLocation, Point.fromLngLat(location.longitude, location.latitude)) as List<Point>
//                    )
//                    .withLineColor(R.color.main.toString())
//                    .withLineWidth(7.0)
//            )
        lastLocation = Point.fromLngLat(location.longitude, location.latitude)

    }

    private fun startOrPause() {
        if (isRecordRunning){
            sendCommandToForegroundService(RecordState.PAUSE)
        }else{
            if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION);
            } else {
                sendCommandToForegroundService(RecordState.START)
                lastLocation = curPoint
            }
        }
    }

    private fun setRoute() {
        arguments.routeArgument.let { route ->
            if(route != null) {
                val encodedRoute: List<Point> = PolylineUtils.decode(route, 5)
                val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
                    .withPoints(encodedRoute)
                    .withLineColor(ROUTE_COLOR)
                    .withLineOpacity(0.498)
                    .withLineWidth(7.0)

                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(encodedRoute[0])
                    .withPoint(encodedRoute[encodedRoute.lastIndex])

                routeAnnotationManager.create(polylineAnnotationOptions)
                pointAnnotaionManager.create(pointAnnotationOptions)
            }
        }
    }

    private fun setViewModel(){
        binding.vm = recordViewModel
    }

    private fun setMapView() {
        routeAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
        polylineAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
        pointAnnotaionManager = binding.mapViewRecord.annotations.createPointAnnotationManager()
        mapView = binding.mapViewRecord
    }

    private fun setPolyline(last: Point, cur: Location) {
        val points = listOf(
            last,
            Point.fromLngLat(cur.longitude ,cur.latitude),
        )
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(points)
            .withLineColor("#ee4e8b")
            .withLineWidth(7.0)

        polylineAnnotationManager.create(polylineAnnotationOptions)
    }

    private fun addPolyLineToMap() {
        var lastPoint = Point.fromLngLat(curPoint.longitude(),curPoint.latitude())
        timerTaskforPolyLine = timer(period = 2000) {
//            setPolyline(curPoint, lastPoint)
            recordViewModel.addDistance(getDistance(curPoint, lastPoint))
            lastPoint = Point.fromLngLat(curPoint.longitude(), curPoint.latitude())
        }
    }

    private fun setDistanceText() {
        recordViewModel.distance.observe(requireActivity()) { distance ->
            binding.distance = distance / 1000
        }
    }

    private fun changeCurButtonState() {
        binding.imagebuttonChangeCameraView.setOnClickListener {
            onCameraTracking()
        }
    }

    private fun showSaveSheet(){
        val dialog = BottomSheetDialog(requireActivity())
        val encodedUrl : String = URLEncoder.encode(polylineAnnotationManager.getTotalPolyline(),"EUC-KR")
        val recordUrl =
            "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/path-8+ff0000($encodedUrl)/auto/1280x1280?access_token=pk.eyJ1IjoieWNoNTI2IiwiYSI6ImNsY3B2djAxNzI4dmIzd21tMjl4aXB4bDkifQ.HXaG-IdHhpXBsOByFTPVlA"
        dialog.setContentView(R.layout.fragment_record_dialog)
        val recordSaveButton = dialog.findViewById<TextView>(R.id.button_record_save)
        val recordQuitButton = dialog.findViewById<TextView>(R.id.button_record_quit)
        val recordImage = dialog.findViewById<ImageView>(R.id.imageview_record_save)
        val recordDetail = dialog.findViewById<TextView>(R.id.edittext_record_detail)
        if (recordImage != null) {
            Glide.with(requireActivity()).load(recordUrl).into(recordImage)
        }
        recordSaveButton?.setOnClickListener {
            val detail = recordDetail?.text.toString()
            recordViewModel.setText(detail)
            recordViewModel.saveRecord(polylineAnnotationManager.getTotalPolyline())

            Toast.makeText(context, "저장되었습니다!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        recordQuitButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun getDistance(cur: Point, last: Point): Double {
        val dLat = Math.toRadians(last.latitude() - cur.latitude())
        val dLong = Math.toRadians(last.longitude() - cur.longitude())
        val a = Math.sin(dLat / 2).pow(2.0) + Math.sin(dLong / 2)
            .pow(2.0) * Math.cos(Math.toRadians(cur.latitude()))
        val c = 2 * asin(sqrt(a))
        return (6372.8 * 1000 * c) // 상수 입니다..!
    }

    private fun PolylineAnnotationManager.getTotalPolyline(): String {
        val pointList: ArrayList<Point> = ArrayList()
        annotations.forEach() { polylineAnnotation ->
            polylineAnnotation.points.forEach() { point ->
                pointList.add(point)
            }
        }
        return PolylineUtils.encode(pointList, 5)
    }

    private fun updateDurationValue(elapsedTime: Int) {
        binding.hour = elapsedTime / 3600
        binding.minute = (elapsedTime % 3600) / 60
        binding.second = elapsedTime % 60
    }

    private fun updateLayout(isRecordRunning: Boolean) {
        if(isRecordRunning) {
            binding.imagebuttonRecordStartbutton.setImageResource(R.drawable.ic_stop_circle_100)
        }else {
            binding.imagebuttonRecordStartbutton.setImageResource(R.drawable.ic_start_circle_100)
        }
    }

    private fun sendCommandToForegroundService(recordState: RecordState) {
        ContextCompat.startForegroundService(mainActivity, getServiceIntent(recordState))
    }

    private fun getServiceIntent(command: RecordState) =
        Intent(mainActivity.applicationContext, RecordService::class.java).apply {
            putExtra(SERVICE_COMMAND, command as Parcelable)
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendCommandToForegroundService(RecordState.START)
            }else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}