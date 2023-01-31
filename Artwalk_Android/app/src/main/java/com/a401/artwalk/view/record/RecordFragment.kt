package com.a401.artwalk.view.record

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.a401.artwalk.utils.LocationPermissionHelper
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearingSource
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location2
import kotlinx.coroutines.flow.*
import java.lang.ref.WeakReference
import com.mapbox.geojson.Point
import java.util.*
import kotlin.concurrent.timer

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var mapView: MapView
    private var time = 0
    private var timerTask: Timer? = null
    var flag = true

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val positionChangedListenerCenterCur = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val positionChangedListenerFree = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().build())
    }

    private fun addPolylineToMap(cur: Point) {
        val lastLong = cur.longitude()+0.000001
        val lastLat = cur.latitude()+0.000001
        val points = listOf(
            Point.fromLngLat(cur.longitude(), cur.latitude()),
            Point.fromLngLat(lastLong ,lastLat)
        )
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(points)
            .withLineColor("#ee4e8b")
            .withLineWidth(5.0)
        polylineAnnotationManager.create(polylineAnnotationOptions)
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
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
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
                .zoom(18.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun changeStartButtonState(){
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton){
                isSelected= !isSelected
            }
            if(binding.imagebuttonRecordStartbutton.isSelected)
            {
                startRun()
            } else {
                stopRun()
            }
        }

    }

    private fun startRun(){
        if(flag){
            flag = false
            timerTask = timer(period = 10){
                time++
                var sec = time/100
                var milli = time%100

                requireActivity().runOnUiThread(){
                    binding.sec.text = "$sec"
                    binding.millisec.text = "$milli"
                }
            }
        }
    }

    private fun stopRun(){
        if(!flag){
            flag=true
            timerTask?.cancel()
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

    private fun changeCurButtonState(){
        val trackingButton = binding.imagebuttonChangeCameraView
        trackingButton.isSelected = true
        recordViewModel.curButtonEvent.observe(requireActivity()){
            with(trackingButton){
                isSelected= !isSelected
            }
                changeCameraView()
        }
    }

    private fun changeCameraView(){
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

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun headerTo(){
        mapView.location2.updateSettings2 {
            puckBearingSource = PuckBearingSource.HEADING
        }
    }

}