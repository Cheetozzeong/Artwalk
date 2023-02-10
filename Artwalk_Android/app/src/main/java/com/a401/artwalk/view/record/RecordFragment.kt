package com.a401.artwalk.view.record

import android.icu.text.AlphabeticIndex.Record
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
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
import kotlin.concurrent.timer
import kotlin.math.absoluteValue
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

@AndroidEntryPoint
class RecordFragment : UsingMapFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val arguments by navArgs<RecordFragmentArgs>()
    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var routeAnnotationManager: PolylineAnnotationManager
    private lateinit var pointAnnotaionManager: PointAnnotationManager
    private var timerTaskforPolyLine : Timer? = null

    private var curPoint : Point = Point.fromLngLat(0.0,0.0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMapView()
        super.onViewCreated(view, savedInstanceState)

        val onIndicatorPositionChangedListenerForDraw = OnIndicatorPositionChangedListener {
            curPoint = it
        }
        mapView.location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListenerForDraw)

        setInitBinding()
        startButtonPressed()
        stopButtonPressed()
        changeCurButtonState()
        setDistanceText()
        setDurationText()
        setRoute()

        recordViewModel.isSuccessSave.observe(viewLifecycleOwner) { isSuccessSave ->
            if(isSuccessSave) {
                findNavController().popBackStack()
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

    private fun setInitBinding(){
        binding.vm = recordViewModel
    }

    private fun setMapView() {
        routeAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
        polylineAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
        pointAnnotaionManager = binding.mapViewRecord.annotations.createPointAnnotationManager()
        mapView = binding.mapViewRecord
    }

    private fun setPolyline(cur:Point, last:Point) {
        val points = listOf(
            Point.fromLngLat(cur.longitude(), cur.latitude()),
            Point.fromLngLat(last.longitude() ,last.latitude()),
        )
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(points)
            .withLineColor("#ee4e8b")
            .withLineWidth(7.0)
        requireActivity().runOnUiThread() {
            polylineAnnotationManager.create(polylineAnnotationOptions)
        }
    }

    private fun addPolyLineToMap() {
        var lastPoint = Point.fromLngLat(curPoint.longitude(),curPoint.latitude())
        timerTaskforPolyLine = timer(period = 2000) {
            setPolyline(curPoint, lastPoint)
            recordViewModel.addDistance(getDistance(curPoint, lastPoint))
            lastPoint = Point.fromLngLat(curPoint.longitude(), curPoint.latitude())
        }
    }

    private fun setDistanceText() {
        recordViewModel.distance.observe(requireActivity()) { distance ->
            binding.distance = distance / 1000
        }
    }

    private fun setDurationText() {
        recordViewModel.totalDuration.observe(requireActivity()) { totalDuration ->
            binding.hour = totalDuration/3600
            binding.minute = (totalDuration % 3600) / 60
            binding.second = totalDuration % 60
        }
    }

    private fun changeCurButtonState() {
        binding.imagebuttonChangeCameraView.setOnClickListener {
            onCameraTracking()
        }
    }

    private fun changeStartButtonState(){
        val startbutton = binding.imagebuttonRecordStartbutton
        val stopbutton = binding.imagebuttonRecordStopbutton
        startbutton.isEnabled = !startbutton.isEnabled
        startbutton.isVisible = !startbutton.isVisible
        stopbutton.isGone = !stopbutton.isGone
    }

    private fun startButtonPressed() {
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton) {
                changeStartButtonState()
                recordViewModel.startRun()
                addPolyLineToMap()
            }
        }

    }

    private fun stopButtonPressed(){
        recordViewModel.stopButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStopbutton){
                changeStartButtonState()
                recordViewModel.stopRun()
                timerTaskforPolyLine?.cancel()
                showSaveSheet()
            }
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
}