package com.a401.artwalk.view.route.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRouteDrawBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class RouteDrawFragment : BaseFragment<FragmentRouteDrawBinding> (R.layout.fragment_route_draw) {

    private val routeDrawViewModel by viewModels<RouteDrawViewModel> { defaultViewModelProviderFactory }
    private lateinit var mapboxMap: MapboxMap
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()
        changeDrawButtonState()
        setMapBoxView()
    }

    private fun setMapBoxView() {
        mapView = binding.mapViewRouteDraw
        mapboxMap = mapView.getMapboxMap()

        mapboxMap.addOnMapClickListener { point ->

            when(binding.textViewRouteDrawDrawButton.isSelected) {
                true -> addAnnotationToMap(point)
            }
            true
        }
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun addAnnotationToMap(point: Point) {
        bitmapFromDrawableRes(
            requireContext(),
            R.drawable.ic_route_draw_marker_16
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()

            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(it)

            pointAnnotationManager.create(pointAnnotationOptions)
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
}