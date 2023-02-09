package com.a401.artwalk.view.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentPolicyBinding

class PolicyFragment: BaseFragment<FragmentPolicyBinding>(R.layout.fragment_policy) {

    private val arguments by navArgs<PolicyFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = arguments.title
        binding.content.text = arguments.policyContent
    }
}