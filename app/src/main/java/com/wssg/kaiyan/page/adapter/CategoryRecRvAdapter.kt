package com.wssg.kaiyan.page.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.utils.durationToStr

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/20
 * @Description:
 */
class CategoryRecRvAdapter :
    PagingDataAdapter<VideoInfoData, CategoryRecRvAdapter.ViewHolder>(COMPARATOR) {
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
        val coverIv: ImageView
        val collectionCountTv: TextView
        val durationTv: TextView
        val titleTv: TextView
        val kindTv: TextView

        init {
            view.run {
                coverIv = findViewById(R.id.iv_rvItemCategoryRec_cover)
                collectionCountTv = findViewById(R.id.tv_rvItemCategoryRec_agree)
                durationTv = findViewById(R.id.tv_rvItemCategoryRec_duration)
                titleTv = findViewById(R.id.tv_rvItemCategoryRec_title)
                kindTv = findViewById(R.id.tv_rvItemCategoryRec_kind)
                coverIv.setOnClickListener {
                    listener?.onClicked(getItem(absoluteAdapterPosition)!!,coverIv)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_rec_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            getItem(position)!!.run {
                Glide.with(itemView).load(coverUrl).into(coverIv)
                collectionCountTv.text = consumption.collectionCount.toString()
                titleTv.text = title
                durationTv.text = duration.durationToStr()
                kindTv.text = kind
            }
        }
    }

    interface OnClickedListener {
        fun onClicked(detailBean: VideoInfoData, view: View)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}