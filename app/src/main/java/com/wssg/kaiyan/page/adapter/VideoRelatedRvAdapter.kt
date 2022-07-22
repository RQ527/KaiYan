package com.wssg.kaiyan.page.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.utils.durationToStr

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/22
 * @Description:
 */
class VideoRelatedRvAdapter(private var data: List<VideoInfoData>) :
    RecyclerView.Adapter<VideoRelatedRvAdapter.ViewHolder>() {

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
                    listener?.onClicked(data[absoluteAdapterPosition])
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_play_video_related, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            data[position].run {
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
    }

    override fun getItemCount(): Int = data.size

    interface OnClickedListener {
        fun onClicked(videoInfoData: VideoInfoData)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}