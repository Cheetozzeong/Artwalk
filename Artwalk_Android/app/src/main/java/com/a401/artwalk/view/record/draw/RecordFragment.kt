package com.a401.artwalk.view.record.draw

import android.os.Bundle
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.R
import com.a401.artwalk.databinding.FragmentRecordBinding
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.view.route.draw.ROUTE_COLOR
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.a401.artwalk.view.SampleActivity
import com.a401.artwalk.view.record.*
import com.mapbox.geojson.Point
import com.mapbox.geojson.utils.PolylineUtils
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.util.*

@AndroidEntryPoint
class RecordFragment : UsingMapFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    private lateinit var statusReceiver: BroadcastReceiver
    private lateinit var durationReceiver: BroadcastReceiver
    private lateinit var locationReceiver: BroadcastReceiver

    private lateinit var mainActivity: SampleActivity
    private var isRecordRunning = false

    private val arguments by navArgs<RecordFragmentArgs>()
    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var routeAnnotationManager: PolylineAnnotationManager
    private lateinit var pointAnnotaionManager: PointAnnotationManager
    private var totalLocation = ArrayList<DoubleArray>()

    private var curPoint : Point = Point.fromLngLat(0.0,0.0)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as SampleActivity
    }

    override fun onResume() {
        super.onResume()

        mainActivity.startService(getServiceIntent(RecordState.GET_STATUS))

        statusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val state = intent?.extras?.getSerializable(IS_RECORD_RUNNING) as RecordState
                isRecordRunning = when(state){
                    RecordState.START -> true
                    else -> false
                }
                val timeElapsed = intent.getIntExtra(TIME_ELAPSED, 0)
                val distance = intent.getDoubleExtra(DISTANCE, 0.0)
                totalLocation = intent.getSerializableExtra(TOTAL_LOCATION) as ArrayList<DoubleArray>

                updateLayout(isRecordRunning)
                updateDurationValue(timeElapsed)
                updateDistanceValue(distance)
                updateTotalAnnotation(totalLocation)

                when(state) {
                    RecordState.PAUSE -> showSaveSheet()
                }
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
                val lastLngLat = intent?.getDoubleArrayExtra(LAST_LOCATION)
                val curLngLat = intent?.getDoubleArrayExtra(CURRENT_LOCATION)
                val distance = intent?.getDoubleExtra(DISTANCE, 0.0)
                if(lastLngLat != null && curLngLat != null && distance != null) {
                    updateRecord(lastLngLat, curLngLat)
                    updateDistanceValue(distance)
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
        mainActivity.unregisterReceiver(locationReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v("LIFECYCLE", "onViewCreated")
        setMapView()
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setRoute()
        setCameraStateButton()

        recordViewModel.msg.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

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

    private fun updateTotalAnnotation (totalLocation: ArrayList<DoubleArray>) {

        if(totalLocation.isNotEmpty()) {
            val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
                .withPoints(
                    totalLocation.map {  location ->
                        Point.fromLngLat(location[0], location[1])
                    }
                )
                .withLineColor(R.color.main.toString())
                .withLineWidth(7.0)

            polylineAnnotationManager.create(polylineAnnotationOptions)
        }
    }

    private fun updateRecord(lastLngLat: DoubleArray, curLngLat: DoubleArray) {
        setPolyline(lastLngLat, curLngLat)
    }

    private fun startOrPause() {
        if (isRecordRunning){
            sendCommandToForegroundService(RecordState.PAUSE)
        }else{
            if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION);
            } else {
                sendCommandToForegroundService(RecordState.START)
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

    private fun setPolyline(lastLngLat: DoubleArray, curLngLat: DoubleArray) {
        val points = listOf(
            Point.fromLngLat(lastLngLat[0], lastLngLat[1]),
            Point.fromLngLat(curLngLat[0], curLngLat[1]),
        )
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(points)
            .withLineColor("#ee4e8b")
            .withLineWidth(7.0)

        polylineAnnotationManager.create(polylineAnnotationOptions)
    }

    private fun updateDistanceValue(distance: Double) {
         binding.distance = distance
    }

    private fun setCameraStateButton() {
        binding.imagebuttonChangeCameraView.setOnClickListener {
            onCameraTracking()
        }
    }

    private fun showSaveSheet(){
        val dialog = BottomSheetDialog(requireActivity())
        val encodedUrl : String = URLEncoder.encode(getTotalPolyline(),"EUC-KR")
        val recordUrl =
            "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/path-8+ff0000($encodedUrl)/auto/1280x1280?access_token=${BuildConfig.MAPBOX_PUBLIC_ACCESS_TOKEN}"
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
            recordViewModel.setTitle(detail)
            recordViewModel.saveRecord(
                getTotalPolyline(),
                (binding.hour ?: 0) * 3600 + (binding.minute ?: 0) * 60 + (binding.second ?: 0),
                binding.distance ?: 0.0
            )

            sendCommandToForegroundService(RecordState.STOP)
            dialog.dismiss()
        }
        recordQuitButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

    private fun getTotalPolyline(): String {
        val result = StringBuilder()
        val factor = Math.pow(10.0, 5.0)
        var lastLat: Long = 0
        var lastLng: Long = 0

        totalLocation.forEach() { lngLat ->
            val lat = Math.round(lngLat[1] * factor)
            val lng = Math.round(lngLat[0] * factor)
            val varLat = lat - lastLat
            val varLng = lng - lastLng
            encode(varLat, result)
            encode(varLng, result)
            lastLat = lat
            lastLng = lng
        }
        return result.toString()
    }

    private fun encode(variable: Long, result: StringBuilder) {
        var variable = variable
        variable = if (variable < 0) (variable shl 1).inv() else variable shl 1
        while (variable >= 0x20) {
            result.append(Character.toChars((0x20 or (variable and 0x1f).toInt()) + 63))
            variable = variable shr 5
        }
        result.append(Character.toChars((variable + 63).toInt()))
    }
}