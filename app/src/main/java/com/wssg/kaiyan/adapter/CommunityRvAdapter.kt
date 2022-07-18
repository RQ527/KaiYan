package com.wssg.kaiyan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.bean.CommunityData
import com.wssg.kaiyan.bean.VideoInfoData

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
class CommunityRvAdapter :
    PagingDataAdapter<CommunityData, CommunityRvAdapter.InnerRecommendViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<CommunityData>() {
                override fun areItemsTheSame(
                    oldItem: CommunityData,
                    newItem: CommunityData
                ): Boolean {
                    return oldItem.headerUrl == newItem.headerUrl
                }

                override fun areContentsTheSame(
                    oldItem: CommunityData,
                    newItem: CommunityData
                ): Boolean {
                    return oldItem.title == newItem.title
                }
            }
    }

    inner class InnerRecommendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverIv: ImageView
        val kindIv: ImageView
        val titleTv: TextView
        val headerIv: ShapeableImageView
        val nicknameTv: TextView
        val agreeCountTv: TextView

        init {
            view.run {
                coverIv = findViewById(R.id.iv_rvItemInnerFind_pic)
                headerIv = findViewById(R.id.iv_rvItemInnerFind_header)
                titleTv = findViewById(R.id.tv_rvItemInnerFind_title)
                nicknameTv = findViewById(R.id.tv_rvItemInnerFind_nickName)
                agreeCountTv = findViewById(R.id.tv_rvItemInnerFind_agree)
                kindIv = findViewById(R.id.iv_rvItemHome_kind)
                coverIv.setOnClickListener {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerRecommendViewHolder {
        return InnerRecommendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_inner_find_recommend, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerRecommendViewHolder, position: Int) {
        holder.run {
            getItem(position)!!.run {
                Glide.with(itemView).load(coverUrl).into(coverIv)
                Glide.with(itemView).load(headerUrl).into(headerIv)
                titleTv.text = title
                nicknameTv.text = nickname
                agreeCountTv.text = agreeCount.toString()
                if (kind == "ugc_video") kindIv.setImageResource(R.drawable.video)
            }
        }
    }

    interface OnClickedListener {
        fun onClicked(detailBean: VideoInfoData)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}