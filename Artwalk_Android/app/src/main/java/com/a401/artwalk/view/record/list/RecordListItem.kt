package com.a401.artwalk.view.record.list

import androidx.recyclerview.widget.DiffUtil
import com.a401.domain.model.RecordForList

data class RecordListItem(
    val recordForList: RecordForList
) {

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<RecordListItem>() {

                override fun areItemsTheSame(oldItem: RecordListItem, newItem: RecordListItem): Boolean =
                    oldItem.recordForList.recordId == newItem.recordForList.recordId


                override fun areContentsTheSame(oldItem: RecordListItem, newItem: RecordListItem): Boolean =
                    oldItem == newItem

            }
        }
    }
}