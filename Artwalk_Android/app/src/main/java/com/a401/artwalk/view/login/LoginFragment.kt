package com.a401.artwalk.view.login

import android.os.Bundle
import android.view.View
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


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel by viewModels<LoginViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        KakaoSdk.init(requireActivity(), BuildConfig.KAKAO_NATIVE_KEY)
        setInitBinding()
        setClickListener()

    }

    private fun setInitBinding() {
        binding.vm = loginViewModel
    }

    //TODO:VIEWMODEL로
    private fun setClickListener() {
        binding.buttonKakaoLogin.setOnClickListener {
            kakaoLogin() //로그인
        }
        binding.buttonKakaoLogout.setOnClickListener {
            kakaoLogout() //로그아웃
        }
        binding.buttonKakaoUnlink.setOnClickListener {
            kakaoUnlink() //연결해제
        }
    }

    //TODO: 데이터 모듈로 이동
    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                TextMsg(this, "카카오계정으로 로그인 실패 : ${error}")
                setLogin(false)
            } else if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
                UserApiClient.instance.me { user, error ->
                    TextMsg(this, "카카오계정으로 로그인 성공 \n\n " +
                            "token: ${token.idToken} \n\n " +
                            "me: ${user}")
                    setLogin(true)
                }
            }

        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
            UserApiClient.instance.loginWithKakaoTalk(requireActivity()) { token, error ->
                if (error != null) {
                    TextMsg(this, "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
                } else if (token != null) {
                    TextMsg(this, "카카오톡으로 로그인 성공 ${token.idToken}")
                    setLogin(true)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
        }
    }
    private fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                TextMsg(this, "로그아웃 실패. SDK에서 토큰 삭제됨: ${error}")
            }
            else {
                TextMsg(this, "로그아웃 성공. SDK에서 토큰 삭제됨")
                setLogin(false)
            }
        }
    }
    private fun kakaoUnlink(){
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                TextMsg(this, "연결 끊기 실패: ${error}")
            }
            else {
                TextMsg(this, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                setLogin(false)
            }
        }
    }
    private fun TextMsg(act: LoginFragment, msg: String){
        binding.viewToken.text = msg
    }

    //홈 프레그먼트 구성 완료 이후 삭제 예정
    private fun setLogin(bool: Boolean){
        binding.buttonKakaoLogin.visibility = if(bool) View.GONE else View.VISIBLE
        binding.buttonKakaoLogout.visibility = if(bool) View.VISIBLE else View.GONE
        binding.buttonKakaoUnlink.visibility = if(bool) View.VISIBLE else View.GONE
        binding.logoMain.visibility = if(bool) View.GONE else View.VISIBLE
        binding.wordboxSubtitle.visibility = if(bool) View.GONE else View.VISIBLE
        binding.wordboxTitle.visibility = if(bool) View.GONE else View.VISIBLE
    }
}