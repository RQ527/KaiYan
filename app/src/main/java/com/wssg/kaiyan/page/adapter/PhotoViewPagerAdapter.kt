package com.wssg.kaiyan.page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.wssg.kaiyan.model.bean.CommunityData

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/23
 * @Description:
 */
class PhotoViewPagerAdapter(private val urls: List<String>) :
    RecyclerView.Adapter<PhotoViewPagerAdapter.ViewHolder>() {
    inner class ViewHolder(val photoView: PhotoView) : RecyclerView.ViewHolder(photoView) {
        init {
            photoView.setOnClickListener { click?.onClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(PhotoView(parent.context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(urls[position]).into(holder.photoView)
    }

    override fun getItemCount(): Int = urls.size

    private var click: OnClickListener? = null
    fun interface OnClickListener{
        fun onClick()
    }
    fun setOnClickedListener(click: OnClickListener) {
        this.click = click
    }

}