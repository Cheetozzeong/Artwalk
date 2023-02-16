package com.a401.artwalk.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.SampleViewBinding
import com.a401.artwalk.view.record.RecordService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity: BaseActivity<SampleViewBinding>(R.layout.sample_view) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation()
        this.applicationContext.getSystemService(RecordService::class.java) ?: startService(Intent(this.applicationContext, RecordService::class.java))
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