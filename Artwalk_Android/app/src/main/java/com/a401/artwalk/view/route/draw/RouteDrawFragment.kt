package com.a401.artwalk.view.route.draw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.RouteDrawFragmentBinding
import com.mapbox.maps.Style

class RouteDrawFragment : BaseFragment<RouteDrawFragmentBinding> (R.layout.route_draw_fragment) {

    private val routeDrawViewModel by viewModels<RouteDrawViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()
        changeDrawButtonState()
        binding.mapViewRouteDraw.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
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