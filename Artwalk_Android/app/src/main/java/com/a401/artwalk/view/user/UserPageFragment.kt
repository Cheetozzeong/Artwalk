package com.a401.artwalk.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a401.artwalk.App
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseFragment
import com.a401.artwalk.databinding.FragmentUserPageBinding
import com.a401.artwalk.view.record.list.RecordListAdapter
import com.a401.artwalk.view.record.list.ThumbnailClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserPageFragment : BaseFragment<FragmentUserPageBinding> (R.layout.fragment_user_page) {

    // UserPageView 모델이라는 모델을 가지고 와서 변수로 설정
    private val userPageViewModel by viewModels<UserpageViewModel> { defaultViewModelProviderFactory }

    private val recordListAdapter = RecordListAdapter(
        ThumbnailClickListener { recordForList ->
            val action = UserPageFragmentDirections.actionRecordListToRecordDetail(recordForList)
            findNavController().navigate(action) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPageViewModel.getRecords()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setToolBar()
        lifecycleScope.launch {
            setUser()
        }
        lifecycleScope.launch {
            collectListItem()

        }

        binding.toolbarUserPage.menu.findItem(R.id.setting).setOnMenuItemClickListener {
            findNavController().navigate(UserPageFragmentDirections.actionUserPageToSetting())
            true
        }
    }

    private suspend fun setUser() {
            userPageViewModel.userInfo.collect { user ->
                binding.nickName = user.nickName
                binding.numOfRecord = user.numOfRecord.toString()
                binding.numOfRoute = user.numOfRoute.toString()

                Glide.with(binding.imageViewUserPageProfile)
                    .load(
                        GlideUrl(
                            BuildConfig.PROFILE_URL + "?userId=" + user.userId,
                            LazyHeaders.Builder().addHeader("accessToken", "Bearer ${App.prefs.getString("accessToken", "")}").build()
                        )
                    )
                    .into(binding.imageViewUserPageProfile)

        }
    }

    private fun setToolBar() {
        binding.toolbarUserPage.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.setting -> {
                    // TODO: setting fragment
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() = with(binding.recyclerViewRecordList) {
        adapter = recordListAdapter
    }

    private suspend fun collectListItem() {
        userPageViewModel.records.collect { recordForList ->
            recordListAdapter.submitList(recordForList)
        }
    }
}