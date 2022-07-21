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
import com.wssg.kaiyan.model.bean.CategorySquareData
import com.wssg.kaiyan.model.bean.VideoInfoData
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class CategorySquareRvAdapter: PagingDataAdapter<CategorySquareData, CategorySquareRvAdapter.InnerViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<CategorySquareData>() {
                override fun areItemsTheSame(
                    oldItem: CategorySquareData,
                    newItem: CategorySquareData
                ): Boolean {
                    return oldItem.description == newItem.description
                }

                override fun areContentsTheSame(
                    oldItem: CategorySquareData,
                    newItem: CategorySquareData
                ): Boolean {
                    return oldItem.nickname == newItem.nickname
                }
            }
    }

    inner class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerIv: ImageView
        val nicknameTv: TextView
        val timeTv: TextView
        val descriptionTv: TextView
        val agreeTv: TextView
        val shareTv: TextView
        val commentTv: TextView
        val coverIv: ImageView
        val videoView:View
        init {
            view.run {
                headerIv = findViewById(R.id.iv_rvItemCategorySquare_header)
                coverIv = findViewById(R.id.iv_rvItemCategorySquare_pic)
                nicknameTv = findViewById(R.id.tv_rvItemCategorySquare_nickname)
                timeTv = findViewById(R.id.tv_rvItemCategorySquare_time)
                descriptionTv = findViewById(R.id.tv_rvItemCategorySquare_description)
                agreeTv = findViewById(R.id.tv_rvItemCategorySquare_agree)
                shareTv = findViewById(R.id.tv_rvItemCategorySquare_share)
                commentTv = findViewById(R.id.tv_rvItemCategorySquare_comment)
                videoView = findViewById(R.id.view_rvItemCategorySquare_video)
                coverIv.setOnClickListener {
                    listener?.onClicked(getItem(absoluteAdapterPosition)!!)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.run {
            getItem(position)!!.run {
                Glide.with(itemView).load(headerUrl).into(headerIv)
                Glide.with(itemView).load(coverUrl).into(coverIv)
                nicknameTv.text = nickname
                timeTv.text =
                    SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(releaseDate))
                descriptionTv.text = description
                agreeTv.text = consumption.collectionCount.toString()
                shareTv.text = consumption.shareCount.toString()
                commentTv.text = consumption.replyCount.toString()
                if (playUrl==null) videoView.visibility = View.GONE else videoView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder =
        InnerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_square_rv, parent, false)
        )
    interface OnClickedListener {
        fun onClicked(categorySquareData: CategorySquareData)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}