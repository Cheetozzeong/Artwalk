package com.a401.artwalk.view.record

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.mapbox.maps.Style
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.utils.LocationPermissionHelper
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
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private var curPoint : Point = Point.fromLngLat(0.0,0.0)
    private val arguments by navArgs<RecordFragmentArgs>()
    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var mapView: MapView
    private var timerTaskforPolyLine : Timer? = null
    private var timerTaskforTime: Timer? = null
    private var timerTaskforDistance: Timer? = null
    private var time = 0
    private var distance = 0.0
    private var flagForWalk = true

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val positionChangedListenerCenterCur = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
        curPoint = it
    }

    private val positionChangedListenerFree = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().build())
        curPoint = it
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitBinding()
        setMapView()
        changeStartButtonState()
        changeQuitButtonState()
        changeCurButtonState()
        setDistanceText()
        setDurationText()
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
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

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun changeStartButtonState() {
        recordViewModel.startButtonEvent.observe(requireActivity()) {
            with(binding.imagebuttonRecordStartbutton) {
                isSelected= !isSelected
            }
            if(binding.imagebuttonRecordStartbutton.isSelected) {
                startRun()
            } else {
                stopRun()
            }
        }

    }

    private fun startRun(){
        if(flagForWalk){
            addPolyLinetoMap()
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

    private fun setPolyline(cur:Point, last:Point) {
        val points = listOf(
            Point.fromLngLat(cur.longitude(), cur.latitude()),
            Point.fromLngLat(last.longitude() ,last.latitude())
        )
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(points)
            .withLineColor("#ee4e8b")
            .withLineWidth(7.0)
        requireActivity().runOnUiThread() {
            polylineAnnotationManager.create(polylineAnnotationOptions)
        }
    }

    private fun addPolyLinetoMap() {
        var lastPoint = Point.fromLngLat(curPoint.longitude(),curPoint.latitude())
        timerTaskforPolyLine = timer(period = 2000){
            setPolyline(curPoint,lastPoint)
            distance += getDistance(curPoint,lastPoint)
            lastPoint = Point.fromLngLat(curPoint.longitude(),curPoint.latitude())
        }
    }

    private fun getDistance(cur: Point, last:Point): Double {
        val dLat = Math.toRadians(last.latitude() - cur.latitude())
        val dLong = Math.toRadians(last.longitude() - cur.longitude())
        val a = sin(dLat/2).pow(2.0) + sin(dLong/2).pow(2.0) * cos(Math.toRadians(cur.latitude()))
        val c = 2 * asin(sqrt(a))
        return (6372.8 * 1000 * c) // 상수 입니다..!
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
        val trackingButton = binding.imagebuttonChangeCameraView
        trackingButton.isSelected = true
        recordViewModel.curButtonEvent.observe(requireActivity()){
            with(trackingButton){
                isSelected= !isSelected
            }
                changeCameraView()
        }
    }

    private fun changeCameraView() {
        if(binding.imagebuttonChangeCameraView.isSelected) {
            mapView.location2.removeOnIndicatorPositionChangedListener(positionChangedListenerFree)
            mapView.location2.addOnIndicatorPositionChangedListener(positionChangedListenerCenterCur)
            mapView.location2.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        } else {
            mapView.location2.removeOnIndicatorPositionChangedListener(positionChangedListenerCenterCur)
            mapView.location2.addOnIndicatorPositionChangedListener(positionChangedListenerFree)
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location2
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                topImage = AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.ic_mylocation,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.mylocation_bg,
                )
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(positionChangedListenerCenterCur)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        headerTo()
    }

    private fun onCameraTrackingDismissed() {
        mapView.location2
            .removeOnIndicatorPositionChangedListener(positionChangedListenerCenterCur)
        mapView.location2
            .addOnIndicatorPositionChangedListener(positionChangedListenerFree)
        if(binding.imagebuttonChangeCameraView.isSelected) {
            binding.imagebuttonChangeCameraView.isSelected = !binding.imagebuttonChangeCameraView.isSelected
            changeCameraView()
        }
        mapView.location2
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location2
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location2
            .removeOnIndicatorPositionChangedListener(positionChangedListenerCenterCur)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun headerTo() {
        mapView.location2.updateSettings2 {
            puckBearingSource = PuckBearingSource.HEADING
        }
    }

}