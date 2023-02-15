package com.a401.artwalk.view.record.list

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.App
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.DetailRecordListBinding
import com.a401.domain.model.RecordForPut
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordDetail : BaseFragment<DetailRecordListBinding>(R.layout.detail_record_list) {
    private val arguments by navArgs<RecordDetailArgs>()
    private val recordDetailViewModel by viewModels<RecordDetailViewModel> { defaultViewModelProviderFactory }
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            setInitBinding()
            setThumbnail()
            setButtons()
        }
        setRecordInfo()
    }

    override fun onPause() {
        super.onPause()
        scope.cancel()
    }

    override fun onResume() {
        super.onResume()
        scope.launch(){
            setRecordInfo()
        }
    }

    private fun setInitBinding() {
        binding.vm = recordDetailViewModel
    }

    private fun setThumbnail() {
        arguments.detailArgument.recordId.let { recordId ->
            Glide.with(requireActivity())
                .load(
                    GlideUrl(
                        BuildConfig.IMAGE_BASE_URL_RECORD + recordId,
                        LazyHeaders.Builder().addHeader(
                            "accessToken",
                            "Bearer ${App.prefs.getString("accessToken", "")}"
                        ).build()
                    )
                )
                .into(binding.imageviewDetailThumbnail)
        }
    }

    private fun setRecordInfo(){
        arguments.detailArgument.duration.let {
            binding.hour = it / 3600
            binding.minute = (it % 3600) / 60
            binding.second = it % 60
        }

        arguments.detailArgument.distance.let {
            binding.distance = it / 1000
        }

        arguments.detailArgument.title.let {
            binding.detail = it
        }
    }

    private fun setButtons(){
        val shareButton = view?.findViewById<ActionMenuItemView>(R.id.button_detail_share)
        val deleteButton = view?.findViewById<ActionMenuItemView>(R.id.button_detail_delete)
        val backButton = view?.findViewById<ImageButton>(R.id.ImageButton_detail_back)
        val saveButton = view?.findViewById<Button>(R.id.button_detail_save)

        shareButton?.setOnClickListener {
            openEditPage()
        }

        deleteButton?.setOnClickListener {
            checkRecordDelete()
        }

        backButton?.setOnClickListener{
            findNavController().navigate(RecordDetailDirections.actionDetailToList())
        }

        saveButton?.setOnClickListener{
            checkChangeTitle()
        }
    }

    private fun checkRecordDelete() {
        val dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(R.layout.fragment_record_detail_dialog)
        val recordDeleteButton = dialog.findViewById<TextView>(R.id.button_record_detail_delete)
        val recordQuitButton = dialog.findViewById<TextView>(R.id.button_record_detail_quit)
        recordDeleteButton?.setOnClickListener {
            recordDetailViewModel.recordDelete(arguments.detailArgument.recordId)
            findNavController().navigate(RecordDetailDirections.actionDeleteRecord())
            dialog.dismiss()
        }
        recordQuitButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun checkChangeTitle() {
        val dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(R.layout.fragment_record_save_dialog)
        val saveOkButton = dialog.findViewById<TextView>(R.id.button_record_save_ok)
        val saveQuitButton = dialog.findViewById<TextView>(R.id.button_record_save_quit)
        val recordDetail = view?.findViewById<TextView>(R.id.edittext_edit_detail)

        saveOkButton?.setOnClickListener {
            val editDetail = recordDetail?.text.toString()
            recordDetailViewModel.editTitle(RecordForPut(editDetail, arguments.detailArgument.recordId))
            findNavController().navigate(RecordDetailDirections.actionDetailToList())
            dialog.dismiss()
        }
        saveQuitButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun openEditPage(){
        lifecycleScope.launch {
            val url = recordDetailViewModel.getLink(arguments.detailArgument.recordId)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.`package` = "com.android.chrome"
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                intent.`package` = null
                startActivity(intent)
            }
        }
    }

}