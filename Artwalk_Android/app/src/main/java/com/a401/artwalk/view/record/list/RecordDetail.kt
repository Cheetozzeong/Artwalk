package com.a401.artwalk.view.record.list

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.a401.artwalk.App
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.DetailRecordListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordDetail : BaseFragment<DetailRecordListBinding>(R.layout.detail_record_list) {
    private val arguments by navArgs<RecordDetailArgs>()
    private val recordDetailViewModel by viewModels<RecordDetailViewModel> { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            setInitBinding()
            setThumbnail()
            setDuration()
            setDistance()
            setDetail()
            setToolBar()
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

    private fun setDuration() {
        arguments.detailArgument.duration.let {
            binding.hour = it / 3600
            binding.minute = (it % 3600) / 60
            binding.second = it % 60
        }
    }

    private fun setDistance() {
        arguments.detailArgument.distance.let {
            binding.distance = it / 1000
        }
    }

    private fun setDetail() {
        arguments.detailArgument.detail.let {
            binding.detail = it
        }
    }

    private fun setToolBar() {
        binding.recordDetailToolbar.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.button_detail_share -> {
                    initShareButton()
                    true
                }
                R.id.button_detail_delete -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initShareButton() {
        val shareButton = view?.findViewById<ActionMenuItemView>(R.id.button_detail_share)
        shareButton?.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://naver.com" // 전달하려는 Data(Value)
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}