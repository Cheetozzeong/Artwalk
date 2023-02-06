package com.a401.artwalk.view.record

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.mapbox.maps.Style
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.utills.LocationPermissionHelper
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
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class RecordFragment : UsingMapFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val arguments by navArgs<RecordFragmentArgs>()
    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private var timerTaskforPolyLine : Timer? = null
    private var timerTaskforTime: Timer? = null
    private var timerTaskforDistance: Timer? = null
    private var curPoint : Point = Point.fromLngLat(0.0,0.0)
    private var time = 0
    private var distance = 0.0
    private var flagForWalk = true



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMapView()
        super.onViewCreated(view, savedInstanceState)

        val onIndicatorPositionChangedListenerForDraw = OnIndicatorPositionChangedListener {
            curPoint = it
        }
        mapView.location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListenerForDraw)

        setInitBinding()
        startButtonPressed()
        pauseButtonPressed()
        stopButtonPressed()
        changeQuitButtonState()
        changeCurButtonState()
        setDistanceText()
        setDurationText()
        setRoute()
    }

    private fun setRoute() {
        arguments.routeArgument ?: return
        Toast.makeText(context, arguments.routeArgument, Toast.LENGTH_SHORT).show()
        // TODO: 받아온 geometry를 화면에 띄우기
    }

    private fun setInitBinding(){
        binding.vm = recordViewModel
    }

    private fun setMapView() {
        polylineAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
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
        timerTaskforPolyLine = timer(period = 2000){
            setPolyline(curPoint,lastPoint)
            distance += getDistance(curPoint,lastPoint)
            lastPoint = Point.fromLngLat(curPoint.longitude(),curPoint.latitude())
        }
    }

    private fun setDistanceText() {
        recordViewModel.distance.observe(requireActivity()) { distance ->
            binding.distance = distance / 1000
        }
    }

    private fun getDistance(cur: Point, last:Point): Double {
        val dLat = Math.toRadians(last.latitude() - cur.latitude())
        val dLong = Math.toRadians(last.longitude() - cur.longitude())
        val a = sin(dLat/2).pow(2.0) + sin(dLong/2).pow(2.0) * cos(Math.toRadians(cur.latitude()))
        val c = 2 * asin(sqrt(a))
        return (6372.8 * 1000 * c) // 상수 입니다..!
    }

    private fun setDurationText() {
        recordViewModel.totalDuration.observe(requireActivity()) { totalDuration ->
            binding.hour = totalDuration/3600
            binding.minute = (totalDuration % 3600) / 60
            binding.second = totalDuration % 60
        }
    }

    private fun changeQuitButtonState(){
        val quitButton = binding.imagebuttonRecordQuitbutton
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton){
                quitButton.isEnabled = !quitButton.isEnabled
                quitButton.isVisible = !quitButton.isVisible
            }
        }
    }

    private fun changeCurButtonState() {
        binding.imagebuttonChangeCameraView.setOnClickListener {
            onCameraTracking()
        }
    }

    private fun changeStartButtonState(){
        val startbutton = binding.imagebuttonRecordStartbutton
        val pausebutton = binding.imagebuttonRecordPausebutton
        val stopbutton = binding.imagebuttonRecordStopbutton
        startbutton.isEnabled = !startbutton.isEnabled
        startbutton.isVisible = !startbutton.isVisible
        pausebutton.isGone = !pausebutton.isGone
        stopbutton.isGone = !stopbutton.isGone
    }

    private fun startButtonPressed() {
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton) {
                changeStartButtonState()
                startRun()
            }
        }

    }

    private fun pauseButtonPressed(){
        recordViewModel.pauseButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordPausebutton){
                changeStartButtonState()
                stopRun()
            }
        }
    }

    private fun stopButtonPressed(){
        recordViewModel.stopButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStopbutton){
                changeStartButtonState()
                stopRun()
                saveRecord()
            }
        }
    }

    private fun startRun(){
        if(flagForWalk){
            addPolyLineToMap()
            flagForWalk = false
            timerTaskforTime = timer(period = 1000){
                time++
                val hour = time / 3600
                val minute = (time % 3600) / 60
                val sec = time % 60
                requireActivity().runOnUiThread(){
                    binding.second = sec
                    binding.minute = minute
                    binding.hour = hour
                }
            }
            timerTaskforDistance = timer(period = 2000) {
                requireActivity().runOnUiThread() {
                    binding.distance = distance / 1000
                }
            }
        }
    }

    private fun stopRun() {
        if(!flagForWalk){
            flagForWalk = true
            timerTaskforTime?.cancel()
            timerTaskforDistance?.cancel()
            timerTaskforPolyLine?.cancel()
        }
    }

    private fun saveRecord(){
        val recordRoute: String = polylineAnnotationManager.getTotalPolyline()
        Toast.makeText(context, recordRoute, Toast.LENGTH_SHORT).show()
        Log.d("recordRoute", recordRoute)
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