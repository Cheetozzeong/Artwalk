package com.a401.artwalk.view.user

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

    private val scope = CoroutineScope(Dispatchers.Main)

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
        binding.toolbarUserPage.menu.findItem(R.id.setting).setOnMenuItemClickListener {
            findNavController().navigate(UserPageFragmentDirections.actionUserPageToSetting())
            true
        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            setUser()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            collectListItem()
        }
    }

    override fun onPause() {
        super.onPause()
        viewLifecycleOwner.lifecycleScope.cancel()
    }

    private suspend fun setUser() {
            userPageViewModel.userInfo.collect { user ->
                binding.nickName = user.nickName
                binding.numOfRecord = user.numOfRecord.toString()
                Log.d("resume","getright?")
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