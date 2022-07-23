package com.wssg.kaiyan.page.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.utils.durationToStr

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/22
 * @Description:
 */
class HotRankRvAdapter() :
    ListAdapter<VideoInfoData,HotRankRvAdapter.ViewHolder>(COMPARATOR){
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<VideoInfoData>() {
                override fun areItemsTheSame(
                    oldItem: VideoInfoData,
                    newItem: VideoInfoData
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: VideoInfoData,
                    newItem: VideoInfoData
                ): Boolean {
                    return oldItem.title == newItem.title
                }

            }
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverIv:ImageView
        val titleTv:TextView
        val durationTv:TextView
        init {
            view.run {
                coverIv = findViewById(R.id.iv_rvItemHot_pic)
                titleTv = findViewById(R.id.tv_rvItemHot_title)
                durationTv = findViewById(R.id.tv_rvItemHot_duration)
                coverIv.setOnClickListener {
                    listener?.onClicked(getItem(absoluteAdapterPosition),coverIv)
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hot_rank_rv,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            getItem(position).run {
                Glide.with(itemView).load(coverUrl).into(coverIv)
                titleTv.text = title
                durationTv.text = duration.durationToStr()
            }
        }
    }


    interface OnClickedListener {
        fun onClicked(videoInfoData: VideoInfoData,view: View)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}