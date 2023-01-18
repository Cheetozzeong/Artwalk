package com.a401.artwalk.view.route.draw

import android.os.Bundle
import android.view.View
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.RouteDrawFragmentBinding
import com.mapbox.maps.Style

class RouteDrawFragment : BaseFragment<RouteDrawFragmentBinding> (R.layout.route_draw_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapViewRouteDraw.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        binding.textViewRouteDrawDrawButton.setOnClickListener{
            it.isSelected = !it.isSelected
        }
    }
}