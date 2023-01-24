package com.a401.artwalk.view.route.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRouteDrawBinding
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteDrawFragment : BaseFragment<FragmentRouteDrawBinding> (R.layout.fragment_route_draw) {

    private val routeDrawViewModel by viewModels<RouteDrawViewModel> { defaultViewModelProviderFactory }
    private lateinit var mapboxMap: MapboxMap
    private lateinit var pointAnnotationManager: PointAnnotationManager
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()
        changeDrawButtonState()
        deleteLastMarker()
        setMapBoxView()
        addPolylineToMap()
    }

    private fun setInitBinding() {
        binding.vm = routeDrawViewModel
    }

    private fun changeDrawButtonState() {
        routeDrawViewModel.drawButtonEvent.observe(requireActivity()) {
            with(binding.textViewRouteDrawDrawButton) {
                isSelected = !isSelected
            }
        }
    }

    private fun deleteLastMarker() {
        routeDrawViewModel.lastPointId.observe(requireActivity()) { lastId ->
            pointAnnotationManager.delete(pointAnnotationManager.annotations.filter{
                it.id == lastId
            })
        }
    }

    private fun setMapBoxView() {
        mapboxMap = binding.mapViewRouteDraw.getMapboxMap()
        pointAnnotationManager = binding.mapViewRouteDraw.annotations.createPointAnnotationManager()
        polylineAnnotationManager = binding.mapViewRouteDraw.annotations.createPolylineAnnotationManager()

        mapboxMap.addOnMapClickListener { point ->

            if(binding.textViewRouteDrawDrawButton.isSelected) {
                addAnnotationToMap(point)

            }

            true
        }
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun addPolylineToMap() {
        routeDrawViewModel.lastRoute.observe(requireActivity()) { route ->
            val polylineOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
                .withGeometry(LineString.fromPolyline(route.geometry, 5))
            polylineAnnotationManager.create(polylineOptions)
        }
    }

    private fun addAnnotationToMap(point: Point) {
        bitmapFromDrawableRes(
            requireContext(),
            R.drawable.ic_route_draw_marker_16
        )?.let {

            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(it)

            val action = pointAnnotationManager.create(pointAnnotationOptions)

            routeDrawViewModel.addPointEvent(action.id, point.latitude(), point.longitude())
            action
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }




}