package com.a401.artwalk.view.route.list

import androidx.recyclerview.widget.DiffUtil
import com.a401.domain.model.RouteForList

data class RouteListItem(
    val routeForList: RouteForList,
//    val onClick: () -> Unit
) {

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<RouteListItem>() {

                override fun areItemsTheSame(oldItem: RouteListItem, newItem: RouteListItem): Boolean {
                    TODO("Not yet implemented")
                }

                override fun areContentsTheSame(oldItem: RouteListItem, newItem: RouteListItem): Boolean {
                    TODO("Not yet implemented")
                }

            }
        }
    }
}