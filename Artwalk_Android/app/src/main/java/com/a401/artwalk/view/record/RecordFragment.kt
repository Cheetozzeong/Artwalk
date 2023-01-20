package com.a401.artwalk.view.record

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentRecordBinding
import com.mapbox.maps.Style
import androidx.fragment.app.viewModels

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val recordViewModel by viewModels<RecordViewModel>{defaultViewModelProviderFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitBinding()
        changeStartButtonState()
        binding.mapViewRecord.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun setInitBinding(){
        binding.vm = recordViewModel
    }

    private fun changeStartButtonState(){
        var quitbutton = binding.imagebuttonRecordQuitbutton
        recordViewModel.startButtonEvent.observe(requireActivity()){
            with(binding.imagebuttonRecordStartbutton){
                isSelected= !isSelected
                quitbutton.isEnabled = !quitbutton.isEnabled
                quitbutton.isVisible = !quitbutton.isVisible
            }
        }
    }
}