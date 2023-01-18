package com.a401.artwalk.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.SampleViewBinding
import com.mapbox.maps.Style

class SampleActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_fragment)
    }
}