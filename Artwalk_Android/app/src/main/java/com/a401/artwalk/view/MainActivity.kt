package com.a401.artwalk.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.a401.artwalk.view.login.LoginActivity
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.ActivityMainBinding
import com.a401.domain.usecase.PostLoginUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val postLogin: PostLoginUseCase? = null
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
                // TODO: 자동 로그인
                MainScope().launch {
                    val accessToken = pref.getString("accessToken", "")
                    val refreshToken = pref.getString("refreshToken", "")
                    postLogin?.let {
                        it(
                            accessToken = accessToken.toString(),
                            refreshToken = refreshToken.toString()
                        )
                    }
                }
                Intent(this, SampleActivity::class.java)
                // TODO: 자동 로그인 실패시 login page로 이동
            }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent)
    }

}