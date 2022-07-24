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
import com.wssg.kaiyan.model.bean.SearchResultData
import com.wssg.kaiyan.utils.durationToStr

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class SearchResultRvAdapter :
    PagingDataAdapter<SearchResultData, RecyclerView.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<SearchResultData>() {
                override fun areItemsTheSame(
                    oldItem: SearchResultData,
                    newItem: SearchResultData
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SearchResultData,
                    newItem: SearchResultData
                ): Boolean {
                    return if (oldItem.title == null) oldItem.title == newItem.title else oldItem.videoInfoData?.title == newItem.videoInfoData?.title
                }
            }
    }

    inner class AuthorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeTv: TextView
        val coverIv: ShapeableImageView
        val nameTv: TextView
        val descriptionTv: TextView

        init {
            view.run {
                typeTv = findViewById(R.id.tv_rvItemSearchActivityAuthor_type)
                coverIv = findViewById(R.id.iv_rvItemSearchActivityAuthor_header)
                nameTv = findViewById(R.id.tv_rvItemSearchActivityAuthor_name)
                descriptionTv = findViewById(R.id.tv_rvItemSearchActivityAuthor_description)
            }
        }
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeTv: TextView
        val coverIv: ShapeableImageView
        val nameTv: TextView

        init {
            view.run {
                typeTv = findViewById(R.id.tv_rvItemSearchActivityUser_type)
                coverIv = findViewById(R.id.iv_rvItemSearchActivityUser_header)
                nameTv = findViewById(R.id.tv_rvItemSearchActivityUser_name)
            }
        }
    }

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverIv: ImageView
        val collectionCountTv: TextView
        val durationTv: TextView
        val titleTv: TextView
        val typeTv: TextView
        val categoryTv: TextView

        init {
            view.run {
                coverIv = findViewById(R.id.iv_rvItemSearchActivityVideo_cover)
                collectionCountTv = findViewById(R.id.tv_rvItemSearchActivityVideo_agree)
                durationTv = findViewById(R.id.tv_rvItemSearchActivityVideo_duration)
                titleTv = findViewById(R.id.tv_rvItemSearchActivityVideo_title)
                typeTv = findViewById(R.id.tv_rvItemSearchActivityVideo_type)
                categoryTv = findViewById(R.id.tv_rvItemSearchActivityVideo_kind)
                findViewById<ConstraintLayout>(R.id.cl_rvItemSearchActivityVideo_total).setOnClickListener {
                    listener?.onClicked(getItem(absoluteAdapterPosition)!!,coverIv)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("RQ", "onCreateViewHolder: $viewType")
        LayoutInflater.from(parent.context).run {
            return when (viewType) {
                1 -> AuthorViewHolder(inflate(R.layout.item_search_author_rv, parent, false))
                2 -> UserViewHolder(inflate(R.layout.item_search_use_rv, parent, false))
                3 -> VideoViewHolder(inflate(R.layout.item_search_video_rv, parent, false))
                else -> error("onCreateHolder类型查找错误")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)!!.run {
            holder.run {
                when (type) {
                    "author" -> {
                        this as AuthorViewHolder
                        if (position != 0)
                            if (getItem(position - 1)!!.type == type) typeTv.visibility = View.GONE
                        Glide.with(itemView).load(imageUrl).into(coverIv)
                        nameTv.text = title
                        descriptionTv.text = authorDescription
                    }
                    "user" -> {
                        this as UserViewHolder
                        if (position != 0)
                            if (getItem(position - 1)!!.type == type) typeTv.visibility = View.GONE
                        Glide.with(itemView).load(imageUrl).into(coverIv)
                        nameTv.text = title
                    }
                    "video" -> {
                        this as VideoViewHolder
                        if (position != 0)
                            if (getItem(position - 1)!!.type == type) typeTv.visibility = View.GONE
                        videoInfoData!!.run {
                            Glide.with(itemView).load(coverUrl).into(coverIv)
                            collectionCountTv.text = consumption.collectionCount.toString()
                            titleTv.text = title
                            durationTv.text = duration.durationToStr()
                            categoryTv.text = kind
                        }
                    }
                    else -> {}
                }
            }
        }

    }

    fun interface OnClickedListener {
        fun onClicked(searchResultData: SearchResultData,view: View)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)!!.run {
            Log.d("RQ", "getItemViewType: $type")
            return when (type) {
                "author" -> 1
                "user" -> 2
                "video" -> 3
                else -> error("search类型匹配错误")
            }
        }
    }
}