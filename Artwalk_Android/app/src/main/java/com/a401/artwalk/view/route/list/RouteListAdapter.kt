package com.a401.artwalk.view.route.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a401.artwalk.BR
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.R
import com.a401.artwalk.base.BaseViewHolder
import com.a401.artwalk.databinding.ItemRouteListBinding
import com.a401.domain.model.RouteForList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

class RouteListAdapter(
    private val startButtonClickListener: StartButtonClickListener,
) : ListAdapter<RouteListItem, BaseViewHolder<ItemRouteListBinding>>(RouteListItem.diffUtil) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemRouteListBinding> = BaseViewHolder(parent, R.layout.item_route_list)

    override fun onBindViewHolder(holder: BaseViewHolder<ItemRouteListBinding>, position: Int) {
        holder.binding.setVariable(BR.item, currentList[position])
        holder.binding.executePendingBindings()
        holder.binding.startButtonClickListener = startButtonClickListener

        holder.apply {

            val accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDA3YmFlQG5hdmVyLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NzU2ODg4NTd9.cQ0ot0d3joap981k6XICgGHWMB-HoxGd17UUVQrNr-Rf-ckzA7Z6POf_dr-SrLKOnaOF8VGaY1Dwn0aiAVl42Q"

            val routeId = currentList[position].routeForList.routeId
            Glide.with(holder.itemView)
                .load(
                    GlideUrl(
                        BuildConfig.IMAGE_BASE_URL + routeId,
                        LazyHeaders.Builder().addHeader("accessToken", accessToken).build()
                    )
                )
                .into(binding.imageViewRouteItemThumbnail)
          }
    }
}

class StartButtonClickListener(val clickListener: (geometry: String) -> Unit) {
    fun onClick(routeForList: RouteForList) = clickListener(routeForList.geometry)
}