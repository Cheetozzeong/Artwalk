package com.a401.artwalk.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.a401.artwalk.view.login.LoginActivity
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        binding.imageViewMainLoading.animation = animation;
        val pref = applicationContext.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
        val accessToken = pref.getString("accessToken", null)

        val intent =
            if(accessToken == null) {
                Intent(this, LoginActivity::class.java)
            }else {
                Intent(this, SampleActivity::class.java)
                // TODO: 자동 로그인
                // TODO: 자동 로그인 실패시 login page로 이동
            }
        startActivity(intent)
    }

}