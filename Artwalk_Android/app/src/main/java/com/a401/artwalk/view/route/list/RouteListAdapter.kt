package com.a401.artwalk.view.route.list

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.a401.artwalk.BR
import com.a401.artwalk.base.BaseViewHolder

class RouteListAdapter : ListAdapter<RouteListItem, BaseViewHolder<ViewDataBinding>>(RouteListItem.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> = BaseViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        holder.binding.setVariable(BR.item, currentList[position])
        holder.binding.executePendingBindings()
    }
}