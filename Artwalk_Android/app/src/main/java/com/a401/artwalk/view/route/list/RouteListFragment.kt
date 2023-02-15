package com.a401.artwalk.view.route.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.a401.artwalk.R
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.databinding.FragmentRouteListBinding
import com.a401.artwalk.view.SampleActivity
import com.a401.artwalk.view.record.ROUTE_POLYLINE
import com.a401.artwalk.view.record.RecordService
import com.a401.artwalk.view.record.RecordState
import com.a401.artwalk.view.record.SERVICE_COMMAND
import com.a401.artwalk.view.route.draw.ROUTE_COLOR
import com.mapbox.geojson.Point
import com.mapbox.geojson.utils.PolylineUtils
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteListFragment : UsingMapFragment<FragmentRouteListBinding>(R.layout.fragment_route_list) {

    private lateinit var mainActivity: SampleActivity
    private val routeListViewModel: RouteListViewModel by viewModels() { defaultViewModelProviderFactory }
    private lateinit var polylineAnnotationManager: PolylineAnnotationManager

    private val routeListAdapter = RouteListAdapter(
        StartButtonClickListener { geometry ->
            val action = RouteListFragmentDirections.actionRouteListToRecordMain()
            mainActivity.startService(
                Intent(mainActivity.applicationContext, RecordService::class.java).apply {
                    putExtra(SERVICE_COMMAND, RecordState.SET_ROUTE as Parcelable)
                    putExtra(ROUTE_POLYLINE, geometry)
                }
            )
            findNavController().navigate(action)
        },
        containerClickListener = { geometry ->
            setRoute(geometry)
        }
    )

    private fun setRoute(route: String) {
        polylineAnnotationManager.deleteAll()
        onCameraTrackingDismissed()

        val encodedRoute: List<Point> = PolylineUtils.decode(route, 5)
        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
            .withPoints(encodedRoute)
            .withLineColor(ROUTE_COLOR)
            .withLineOpacity(0.498)
            .withLineWidth(7.0)

        mapView.getMapboxMap().easeTo(CameraOptions.Builder().center(encodedRoute[0]).build(), MapAnimationOptions.mapAnimationOptions {
            this.duration(1000)
        })
        polylineAnnotationManager.create(polylineAnnotationOptions)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as SampleActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMapView()
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setToDrawButtonClickListener()

        lifecycleScope.launch {
            collectListItem()
        }
    }

    private fun setMapView() {
        mapView = binding.mapViewRouteList
        polylineAnnotationManager = binding.mapViewRouteList.annotations.createPolylineAnnotationManager()
    }

    private fun setupRecyclerView() = with(binding.recyclerViewRouteList) {
        val decoration = DividerItemDecoration(requireContext(), VERTICAL)
        this.addItemDecoration(decoration)

        adapter = routeListAdapter
    }

    private suspend fun collectListItem() {
        routeListViewModel.routes.collect { routeForList ->
            routeListAdapter.submitList(routeForList)
        }
    }

    private fun setToDrawButtonClickListener() {
        binding.buttonRouteListToRouteDraw.setOnClickListener {
            val action = RouteListFragmentDirections.actionRouteListToRouteDraw()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        routeListViewModel.getRoutes()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

