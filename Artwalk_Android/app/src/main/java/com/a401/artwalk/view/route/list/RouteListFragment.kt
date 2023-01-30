package com.a401.artwalk.view.route.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRouteListBinding
import com.mapbox.maps.Style
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteListFragment : BaseFragment<FragmentRouteListBinding>(R.layout.fragment_route_list) {

    private val routeListViewModel: RouteListViewModel by viewModels() { defaultViewModelProviderFactory }

    private val routeListAdapter = RouteListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        set()
        setupRecyclerView()

        lifecycleScope.launch {
            collectListItem()
        }
    }

    private fun set() {
        binding.mapViewRouteList.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
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

