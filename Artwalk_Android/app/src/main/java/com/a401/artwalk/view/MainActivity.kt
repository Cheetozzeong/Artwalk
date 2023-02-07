package com.a401.artwalk.view

import android.os.Bundle
import android.view.animation.AnimationUtils
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        binding.imageViewMainLoading.animation = animation;
    }

}