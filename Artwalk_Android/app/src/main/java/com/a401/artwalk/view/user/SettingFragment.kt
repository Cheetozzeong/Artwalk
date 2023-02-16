package com.a401.artwalk.view.user

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentSettingBinding

class SettingFragment: BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearLayoutSettingPrivacyPolicy.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingToPolicy(
                title = getString(R.string.privacy_policy_title),
                policyContent = getString(R.string.privacy_policy_content)
            )
            findNavController().navigate(action)
        }

        binding.linearLayoutSettingLocationPolicy.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingToPolicy(
                title = getString(R.string.location_policy_title),
                policyContent = getString(R.string.location_policy_content)
            )
            findNavController().navigate(action)
        }
        setBackButton()
    }
    
    private fun setBackButton(){
        val backButton = view?.findViewById<ImageButton>(R.id.ImageButton_setting_back)
        backButton?.setOnClickListener{
            findNavController().popBackStack()
        }
    }

}