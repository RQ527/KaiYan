package com.wssg.lib.base.widget.banner

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.lib.base.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/1
 */
class BannerAdapter(private val urls: List<String>) :
    RecyclerView.Adapter<BannerAdapter.InnerHolder>() {
    private var listener: ItemOnClickedListener? = null

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_banner,parent, false)
//        view.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        if (urls.isNotEmpty())
            Glide.with(holder.itemView).load(urls[position % urls.size]).into(holder.imageView)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    inner class InnerHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.iv_banner_item)
            imageView.setOnClickListener {
                listener?.dispose(adapterPosition)
            }
        }
    }

    interface ItemOnClickedListener {
        fun dispose(position: Int)
    }

    fun setItemClickedListener(l: ItemOnClickedListener) {
        listener = l
    }

}