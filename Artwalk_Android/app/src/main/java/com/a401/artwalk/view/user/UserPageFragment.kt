package com.a401.artwalk.view.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.base.UsingMapFragment
import com.a401.artwalk.databinding.FragmentRouteDrawBinding
import com.a401.artwalk.databinding.FragmentUserPageBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.mapbox.maps.plugin.annotation.generated.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPageFragment : BaseFragment<FragmentUserPageBinding> (R.layout.fragment_user_page) {

    // UserPageView 모델이라는 모델을 가지고 와서 변수로 설정
    private val userPageViewModel by viewModels<UserpageViewModel> { defaultViewModelProviderFactory }

    // 뷰가 생성될 때 View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()

        // 사용자 게시글 수 받아와서 바인딩
        // 사용자 경로 정보 받아와서 바인딩
    }

    // 초기에 VM 이라는 데이터를 모델에 바인딩 한다.
    private fun setInitBinding() {
        binding.vm = userPageViewModel;
    }
}