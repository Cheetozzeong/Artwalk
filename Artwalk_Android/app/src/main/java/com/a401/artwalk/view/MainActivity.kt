package com.a401.artwalk.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.a401.artwalk.view.login.LoginActivity
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseActivity
import com.a401.artwalk.databinding.ActivityMainBinding
import com.a401.domain.usecase.PostLoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var postLogin: PostLoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        binding.imageViewMainLoading.animation = animation;

        val prefs = applicationContext.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("accessToken", null)
        val refreshToken = prefs.getString("refreshToken", null)
        val intent =
            if(accessToken == null) {
                Intent(this, LoginActivity::class.java)
            }else {
                lifecycleScope.launch {
                    val accessTokenForLogin = "Bearer $accessToken"
                    val refreshTokenForLogin = "Bearer $refreshToken"
                    postLogin.invoke(
                            accessToken = accessTokenForLogin,
                            refreshToken = refreshTokenForLogin
                        )
                    }
                Intent(this, SampleActivity::class.java)
                // TODO: 자동 로그인 실패시 login page로 이동
            }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent)
    }

}