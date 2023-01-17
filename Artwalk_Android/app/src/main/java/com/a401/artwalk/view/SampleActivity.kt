package com.a401.artwalk.view

import android.os.Bundle
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.SampleViewBinding
import com.mapbox.maps.Style

class SampleActivity: BaseActivity<SampleViewBinding>(R.layout.sample_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }
}