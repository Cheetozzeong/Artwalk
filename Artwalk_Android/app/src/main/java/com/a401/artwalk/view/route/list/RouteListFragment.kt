package com.a401.artwalk.view.route.list

import android.os.Bundle
import android.view.View
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FrgmentRouteListBinding
import com.mapbox.maps.Style

class RouteListFragment : BaseFragment<FrgmentRouteListBinding>(R.layout.frgment_route_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        set()
    }

    private fun set() {
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }
}

