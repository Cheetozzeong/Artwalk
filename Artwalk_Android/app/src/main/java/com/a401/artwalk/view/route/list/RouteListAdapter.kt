package com.a401.artwalk.view.route.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.a401.artwalk.BR
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseViewHolder
import com.a401.artwalk.databinding.ItemRouteListBinding
import com.a401.domain.model.RouteForList

class RouteListAdapter(private val startButtonClickListener: StartButtonClickListener) : ListAdapter<RouteListItem, BaseViewHolder<ItemRouteListBinding>>(RouteListItem.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemRouteListBinding> = BaseViewHolder(parent, R.layout.item_route_list)

    override fun onBindViewHolder(holder: BaseViewHolder<ItemRouteListBinding>, position: Int) {
        holder.binding.setVariable(BR.item, currentList[position])
        holder.binding.executePendingBindings()
        holder.binding.startButtonClickListener = startButtonClickListener
    }
}

class StartButtonClickListener(val clickListener: (geometry: String) -> Unit) {
    fun onClick(routeForList: RouteForList) = clickListener(routeForList.geometry)
}