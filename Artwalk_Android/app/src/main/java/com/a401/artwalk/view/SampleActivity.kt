package com.a401.artwalk.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.SampleViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity: BaseActivity<SampleViewBinding>(R.layout.sample_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation()

        val window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.policy,
                R.id.setting,
                R.id.record_main,
                R.id.detail_record,
                R.id.route_draw -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}