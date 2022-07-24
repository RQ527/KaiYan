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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.utils.durationToStr
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/22
 * @Description:
 */
class VideoRelatedRvAdapter(var data: List<VideoInfoData>?, val headerData: VideoInfoData) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverIv: ImageView
        val durationTv: TextView
        val titleTv: TextView
        val markTv: TextView
        val headerTv: TextView

        init {
            view.run {
                coverIv = findViewById(R.id.iv_rvItemPlayActivity_cover)
                durationTv = findViewById(R.id.tv_rvItemPlayActivity_duration)
                titleTv = findViewById(R.id.tv_rvItemPlayActivity_title)
                markTv = findViewById(R.id.tv_rvItemPlayActivity_mark)
                headerTv = findViewById(R.id.tv_rvItemPlayActivity_header)
                findViewById<ConstraintLayout>(R.id.cl_rvItemPlayActivity_total).setOnClickListener {
                    listener?.onClicked(data!![absoluteAdapterPosition-1], coverIv)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTv: TextView
        val subTitleTv: TextView
        val descriptionTv: TextView
        val collectionTv: TextView
        val shareTv: TextView
        val commentTv: TextView

        init {
            view.run {
                titleTv = findViewById(R.id.tv_playVideo_title)
                subTitleTv = findViewById(R.id.tv_playVideo_subtitle)
                descriptionTv = findViewById(R.id.tv_playVideo_description)
                collectionTv = findViewById(R.id.tv_playVideo_agree)
                shareTv = findViewById(R.id.tv_playVideo_share)
                commentTv = findViewById(R.id.tv_playVideo_comment)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) HeaderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_play_video_related_header, parent, false)
        )
        else ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_play_video_related, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1 && data != null) {
            holder as ViewHolder
            holder.run {
                data?.get(position-1)!!.run {
                    if (bannerData == null) headerTv.visibility = View.GONE else headerTv.run {
                        visibility = View.VISIBLE
                        text = bannerData!![0].kind
                    }
                    Glide.with(itemView).load(coverUrl).into(coverIv)
                    titleTv.text = title
                    durationTv.text = duration.durationToStr()
                    markTv.text = "#$kind / $author"
                }
            }
        } else if (holder.itemViewType == 0) {
            holder as HeaderViewHolder
            holder.run {
                titleTv.text = headerData.title
                subTitleTv.text = "#${headerData.kind} ${
                    SimpleDateFormat("/ yyyy/MM/dd HH:mm").format(Date(headerData.releaseDate))
                }"
                descriptionTv.text = headerData.description
                collectionTv.text = headerData.consumption.collectionCount.toString()
                shareTv.text = headerData.consumption.shareCount.toString()
                commentTv.text = headerData.consumption.replyCount.toString()
            }
        }
    }

    override fun getItemCount(): Int = (data?.size?.plus(1)) ?: 1
    override fun getItemViewType(position: Int): Int {
        if (position != 0) return 1
        return super.getItemViewType(position)
    }

    fun interface OnClickedListener {
        fun onClicked(videoInfoData: VideoInfoData, view: View)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}