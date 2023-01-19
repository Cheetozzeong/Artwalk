package com.a401.artwalk.view.record

import android.os.Bundle
import android.view.View
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.RecordFragmentBinding
import com.mapbox.maps.Style

class RecordFragment : BaseFragment<RecordFragmentBinding>(R.layout.record_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapViewRecord.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        binding.imagebuttonRecordStartbutton.setOnClickListener{
            it.isSelected = !it.isSelected
            binding.imagebuttonRecordQuitbutton.isSelected= !binding.imagebuttonRecordQuitbutton.isSelected
            if (binding.imagebuttonRecordQuitbutton.isSelected == true)
            {
                binding.imagebuttonRecordQuitbutton.visibility = View.INVISIBLE
                binding.imagebuttonRecordQuitbutton.isEnabled = true
            }
            else
            {
                binding.imagebuttonRecordQuitbutton.visibility = View.VISIBLE
                binding.imagebuttonRecordQuitbutton.isEnabled = false
            }
        }
    }
}