package com.a401.artwalk.view.user

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
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
        setBackButton()
    }

    private fun setBackButton(){
        val backButton = view?.findViewById<ImageButton>(R.id.ImageButton_policy_back)
        backButton?.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}