package com.a401.artwalk.view.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.App.Companion.prefs
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentSettingBinding
import com.a401.artwalk.view.SampleActivity
import com.a401.artwalk.view.login.LoginActivity
import com.google.android.material.dialog.MaterialDialogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val settingViewModel by viewModels<SettingViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewSettingPrivacyPolicy.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingToPolicy(
                title = getString(R.string.privacy_policy_title),
                policyContent = getString(R.string.privacy_policy_content)
            )
            findNavController().navigate(action)
        }

        binding.textViewSettingLocationPolicy.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingToPolicy(
                title = getString(R.string.location_policy_title),
                policyContent = getString(R.string.location_policy_content)
            )
            findNavController().navigate(action)
        }

        binding.linearLayoutSettingLogoutBox.setOnClickListener {
            logout()
        }

        settingViewModel.isSuccessRemoveUser.observe(requireActivity()) { isSuccessRemoveUser ->
            if(isSuccessRemoveUser) {
                logout()
                Toast.makeText(context, "회원 탈퇴 성공...", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "회원 탈퇴 실패...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.linearLayoutSettingWithdrawBox.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("회원 탈퇴")
                .setMessage("정말정말.. 탈퇴하실 건가요..?   탈퇴하시면..정보는 사라집니다!")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        settingViewModel.removeUser()
                    }
                )
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->

                    }
                )
            builder.show()
        }
    }

    private fun logout() {
        prefs.clear()
        startActivity(
            Intent(context, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK ))
    }
}