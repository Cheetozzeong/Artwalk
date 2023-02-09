package com.a401.artwalk.view.route.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.databinding.FragmentRouteDrawBinding
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.geojson.utils.PolylineUtils
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

const val ROUTE_COLOR: String = "#0601bd"

@AndroidEntryPoint
class RouteDrawFragment : UsingMapFragment<FragmentRouteDrawBinding> (R.layout.fragment_route_draw) {

    private val routeDrawViewModel by viewModels<RouteDrawViewModel> { defaultViewModelProviderFactory }
    private lateinit var mapboxMap: MapboxMap
    private lateinit var pointAnnotationManager: PointAnnotationManager
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = binding.mapViewRouteDraw
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()
        changeDrawButtonState()
        deleteLastMarker()
        setMapBoxView()
        addPolylineToMap()
        setDurationText()
        setDistanceText()
        setSaveButtonClickListener()

        routeDrawViewModel.isSuccessSave.observe(viewLifecycleOwner) { isSuccessSave ->
            if(isSuccessSave) {
                findNavController().popBackStack()
            }
        }
        Log.d("LifeCycle2", "onViewCreated")

    }

    private fun setInitBinding() {
        binding.vm = routeDrawViewModel
    }

    private fun setDurationText() {
        routeDrawViewModel.totalDuration.observe(requireActivity()) { totalDuration ->
            binding.hour = totalDuration/3600
            binding.minute = (totalDuration % 3600) / 60
            binding.second = totalDuration % 60
        }
    }

    private fun setDistanceText() {
        routeDrawViewModel.distance.observe(requireActivity()) { distance ->
            binding.distance = distance / 1000
        }
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
        mapboxMap = mapView.getMapboxMap()
        pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
        polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()

        mapboxMap.addOnMapClickListener { point ->

            if(binding.textViewRouteDrawDrawButton.isSelected) {
                addAnnotationToMap(point)

            }

            true
        }
    }

    private fun addPolylineToMap() {
        routeDrawViewModel.lastRoute.observe(requireActivity()) { route ->
            val polylineOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
                .withGeometry(LineString.fromPolyline(route.geometry, 5))
                .withLineColor(ROUTE_COLOR)
                .withLineOpacity(0.498)
                .withLineWidth(7.0)
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

    fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
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

    private fun setSaveButtonClickListener() {
        binding.buttonRouteDrawSave.setOnClickListener {
            routeDrawViewModel.saveDrawRoute(polylineAnnotationManager.getTotalPolyline())
        }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("LifeCycle2", "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LifeCycle2", "onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle2", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle2", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle2", "onPause")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("LifeCycle2", "onDetach")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle2", "onStart")
    }

}