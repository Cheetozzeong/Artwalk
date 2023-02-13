package com.a401.artwalk.view.route.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.databinding.FragmentRouteListBinding
import com.mapbox.maps.Style
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteListFragment : UsingMapFragment<FragmentRouteListBinding>(R.layout.fragment_route_list) {

    private val routeListViewModel: RouteListViewModel by viewModels() { defaultViewModelProviderFactory }

    private val routeListAdapter = RouteListAdapter(
        StartButtonClickListener { geometry ->
        val action = RouteListFragmentDirections.actionRouteListToRecordMain(geometry)
        findNavController().navigate(action) }
    )

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
    }

    private fun setupRecyclerView() = with(binding.recyclerViewRouteList) {
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

