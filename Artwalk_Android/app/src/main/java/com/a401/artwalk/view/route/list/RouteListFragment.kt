package com.a401.artwalk.view.route.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FrgmentRouteListBinding
import com.mapbox.maps.Style
import kotlinx.coroutines.launch

class RouteListFragment : BaseFragment<FrgmentRouteListBinding>(R.layout.frgment_route_list) {

    private val routeListViewModel: RouteListViewModel by viewModels()

    private val routeListAdapter = RouteListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        set()
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            collectListItem()
        }
    }

    private fun set() {
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun setupRecyclerView() = with(binding.recyclerViewRouteList) {
        adapter = routeListAdapter
    }

    private suspend fun collectListItem() {
        routeListViewModel.routes.collect { routeForList ->
            routeListAdapter.submitList(routeForList)
        }
    }
}

