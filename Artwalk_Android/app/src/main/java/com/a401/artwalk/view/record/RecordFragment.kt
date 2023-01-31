package com.a401.artwalk.view.record

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.mapbox.maps.Style
import androidx.fragment.app.viewModels
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


class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager
    private lateinit var mapView: MapView
    lateinit var curPoint : Point
    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val PositionChangedListenerCentercur = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
        // it은 현재 위치이고, onIndicatorPositionChangedListner는 위치가 변경될 때마다 호출되는 친구이다.
        addPolylineToMap(it)
        curPoint = it
    }

    private val PositionChangedListenerFree = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().build())
        addPolylineToMap(it)
        curPoint = it
    }

    private fun addPolylineToMap(cur: Point) {
        //현재 위치가 it을 기억 이 전 위치
        val lastlong = cur.longitude()+0.000001
        val lastlat = cur.latitude()+0.000001
        val points = listOf(
            Point.fromLngLat(cur.longitude(), cur.latitude()),
            Point.fromLngLat(lastlong ,lastlat)
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
        changeCurButtonState()
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }


    private fun setInitBinding(){
        binding.vm = recordViewModel
    }
    private fun changeStartButtonState(){
        var quitbutton = binding.imagebuttonRecordQuitbutton
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton){
                isSelected= !isSelected
                quitbutton.isEnabled = !quitbutton.isEnabled
                quitbutton.isVisible = !quitbutton.isVisible
            }
        }
    }

    private fun changeCurButtonState(){
        val Trackingbutton = binding.backToCameraTrackingMode
        recordViewModel.curButtonEvent.observe(requireActivity()){
            with(Trackingbutton){
                isSelected= !isSelected
            }
            if (Trackingbutton.isSelected) {
                //TODO: 현재위치 버튼 클릭시 시점 토글 기능 구현
                Log.d("isSelected", "isSelected")

            } else {
                Log.d("isUnSelected", "isUnSelected")
            }
        }
    }

    private fun setMapView() {
        polylineAnnotationManager = binding.mapViewRecord.annotations.createPolylineAnnotationManager()
        mapView = binding.mapViewRecord
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(32.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
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
        locationComponentPlugin.addOnIndicatorPositionChangedListener(PositionChangedListenerCentercur)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        headerTo()
    }

    private fun onCameraTrackingDismissed() {
        mapView.location2
            .removeOnIndicatorPositionChangedListener(PositionChangedListenerCentercur)
        mapView.location2
            .addOnIndicatorPositionChangedListener(PositionChangedListenerFree)
        mapView.location2
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location2
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location2
            .removeOnIndicatorPositionChangedListener(PositionChangedListenerCentercur)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // 마커의 헤더가 원래 위치를 잘 찾지 못하는 버그 해결
    fun headerTo(){
        mapView.location2.updateSettings2 {
            puckBearingSource = PuckBearingSource.HEADING
        }
    }
}