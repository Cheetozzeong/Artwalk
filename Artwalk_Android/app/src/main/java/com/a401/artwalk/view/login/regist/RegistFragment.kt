package com.a401.artwalk.view.login.regist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentLoginRegistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistFragment: BaseFragment<FragmentLoginRegistBinding>(R.layout.fragment_login_regist) {

    private val registViewModel by viewModels<RegistViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        binding.vm = registViewModel
    }

}