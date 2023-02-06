package com.a401.artwalk.view.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel by viewModels<LoginViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        KakaoSdk.init(requireActivity(), BuildConfig.KAKAO_NATIVE_KEY)
        setInitBinding()
        clickedLoginButton()

    }

    private fun setInitBinding() {
        binding.vm = loginViewModel
    }

    private fun clickedLoginButton() {
        loginViewModel.kakaoLoginButtonEvent.observe(requireActivity()){
            with(binding.buttonKakaoLogin){
                kakaoLogin()
            }
        }
    }

    //TODO: 데이터 모듈로 이동
    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
            } else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                        token.idToken?.let { loginViewModel.saveKakaoIdToken(it) }

                }
            }

        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
            UserApiClient.instance.loginWithKakaoTalk(requireActivity()) { token, error ->
                if (error != null) {

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
                } else if (token != null) {
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
        }
    }
}