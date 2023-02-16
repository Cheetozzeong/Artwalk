package com.a401.artwalk.view.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.App.Companion.prefs
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentSettingBinding
import com.a401.artwalk.view.SampleActivity
import com.a401.artwalk.view.login.LoginActivity

class SettingFragment: BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

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
            prefs.clear()
            startActivity(
                Intent(context, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK ))
        }
    }

}