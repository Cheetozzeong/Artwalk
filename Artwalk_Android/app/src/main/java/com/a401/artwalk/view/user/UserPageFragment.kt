package com.a401.artwalk.view.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.App
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.databinding.FragmentRouteDrawBinding
import com.a401.artwalk.databinding.FragmentUserPageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.mapbox.maps.plugin.annotation.generated.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserPageFragment : BaseFragment<FragmentUserPageBinding> (R.layout.fragment_user_page) {

    // UserPageView 모델이라는 모델을 가지고 와서 변수로 설정
    private val userPageViewModel by viewModels<UserpageViewModel> { defaultViewModelProviderFactory }

    // 뷰가 생성될 때 View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()

        binding.toolbarUserPage.menu.findItem(R.id.setting).setOnMenuItemClickListener {
            findNavController().navigate(UserPageFragmentDirections.actionUserPageToSetting())
            true
        }
        // TODO: 내 기록, 뱃지 등 가져오기

        lifecycleScope.launch {
            setUser()
        }
    }

    // 초기에 VM 이라는 데이터를 모델에 바인딩 한다.
    private suspend fun setUser() {
        userPageViewModel.userInfo.collect { user ->
            binding.nickName = user.nickName
            binding.numOfRecord = user.numOfRecord.toString()
            binding.numOfRoute = user.numOfRoute.toString()

            Glide.with(binding.imageViewUserPageProfile)
                .load(
                    GlideUrl(
                        BuildConfig.PROFILE_URL + "?userId=" + user.userId,
                        LazyHeaders.Builder().addHeader("accessToken", "Bearer ${App.prefs.getString("accessToken", "")}").build()
                    )
                )
                .into(binding.imageViewUserPageProfile)
        }
    }

    private fun setToolBar() {
        binding.toolbarUserPage.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.setting -> {
                    // TODO: setting fragment
                    true
                }
                else -> false
            }
        }
    }

}