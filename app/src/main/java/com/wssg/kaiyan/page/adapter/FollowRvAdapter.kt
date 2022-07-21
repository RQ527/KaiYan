package com.wssg.kaiyan.page.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
class FollowRvAdapter :
    PagingDataAdapter<VideoInfoData, FollowRvAdapter.InnerViewHolder>(COMPARATOR) {
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

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerIv: ImageView
        val nicknameTv: TextView
        val timeTv: TextView
        val titleTv: TextView
        val descriptionTv: TextView
        val agreeTv: TextView
        val shareTv: TextView
        val commentTv: TextView
        val coverIv: ImageView

        init {
            view.run {
                headerIv = findViewById(R.id.iv_rvItemFollow_header)
                coverIv = findViewById(R.id.iv_rvItemFollow_pic)
                nicknameTv = findViewById(R.id.tv_rvItemFollow_nickname)
                timeTv = findViewById(R.id.tv_rvItemFollow_time)
                titleTv = findViewById(R.id.tv_rvItemFollow_title)
                descriptionTv = findViewById(R.id.tv_rvItemFollow_description)
                agreeTv = findViewById(R.id.tv_rvItemFollow_agree)
                shareTv = findViewById(R.id.tv_rvItemFollow_share)
                commentTv = findViewById(R.id.tv_rvItemFollow_comment)
                coverIv.setOnClickListener {
                    listener?.onClicked(getItem(absoluteAdapterPosition)!!)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.run {
                getItem(position)!!.run {
                    Glide.with(itemView).load(authorHeader).into(headerIv)
                    Glide.with(itemView).load(coverUrl).into(coverIv)
                    nicknameTv.text = author
                    timeTv.text =
                        SimpleDateFormat("MM/dd HH:mm").format(Date(releaseDate))
                    titleTv.text = title
                    descriptionTv.text = description
                    agreeTv.text = consumption.collectionCount.toString()
                    shareTv.text = consumption.shareCount.toString()
                    commentTv.text = consumption.replyCount.toString()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder =
        InnerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_follow_rv, parent, false)
        )
    interface OnClickedListener {
        fun onClicked(detailBean: VideoInfoData)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}