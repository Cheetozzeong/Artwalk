package com.a401.artwalk.view.route.list

import androidx.recyclerview.widget.DiffUtil
import com.a401.domain.model.RouteForList

data class RouteListItem(
    val routeForList: RouteForList
) {

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<RouteListItem>() {

                override fun areItemsTheSame(oldItem: RouteListItem, newItem: RouteListItem): Boolean =
                    oldItem.routeForList.routeId == newItem.routeForList.routeId


                override fun areContentsTheSame(oldItem: RouteListItem, newItem: RouteListItem): Boolean =
                    oldItem == newItem

            }
        }
    }
}