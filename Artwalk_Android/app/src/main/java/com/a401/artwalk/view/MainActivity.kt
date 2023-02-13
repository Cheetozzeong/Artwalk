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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
        var intent: Intent = Intent()

        lifecycleScope.launch {
            val accessTokenForLogin = "Bearer $accessToken"
            val refreshTokenForLogin = "Bearer $refreshToken"
            postLogin(accessToken = accessTokenForLogin, refreshToken = refreshTokenForLogin).collectLatest { result ->
                delay(1300)
                intent =
                    if(result == "FAIL") {
                        Intent(this@MainActivity, LoginActivity::class.java)
                    }else {
                        Intent(this@MainActivity, SampleActivity::class.java)
                    }
            }


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent)
        }

    }

}