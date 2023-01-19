package com.a401.artwalk.view.record

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.RecordFragmentBinding
import com.mapbox.maps.Style
import androidx.fragment.app.viewModels

class RecordFragment : BaseFragment<RecordFragmentBinding>(R.layout.record_fragment) {

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
                quitbutton.isSelected = !quitbutton.isSelected
                if (quitbutton.isSelected)
                {
                    quitbutton.setVisibility(View.GONE);
                }
                else{
                    quitbutton.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}