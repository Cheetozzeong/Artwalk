package com.a401.artwalk.view.user

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.a401.artwalk.App
import com.a401.artwalk.BR
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseViewHolder
import com.a401.artwalk.databinding.ItemRecordListBinding
import com.a401.domain.model.RecordForList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class RecordListAdapter(
    private val thumbnailClickListener: ThumbnailClickListener,
) : ListAdapter<RecordListItem, BaseViewHolder<ItemRecordListBinding>>(RecordListItem.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemRecordListBinding> = BaseViewHolder(parent, R.layout.item_record_list)

    override fun onBindViewHolder(holder: BaseViewHolder<ItemRecordListBinding>, position: Int) {
        holder.binding.setVariable(BR.item, currentList[position])
        holder.binding.executePendingBindings()
        holder.binding.thumbnailClickListener = thumbnailClickListener

        holder.apply {
            val recordId = currentList[position].recordForList.recordId
            Glide.with(holder.itemView)
                .load(
                    GlideUrl(
                        BuildConfig.IMAGE_BASE_URL_RECORD + recordId,
                        LazyHeaders.Builder().addHeader("accessToken", "Bearer ${App.prefs.getString("accessToken", "")}").build()
                    )
                )
                .into(binding.imageViewRecordItemThumbnail)
          }
    }
}

class ThumbnailClickListener(val clickListener: (recordId: String) -> Unit) {
    fun onClick(recordForList: RecordForList) = clickListener(recordForList.recordId.toString())
}