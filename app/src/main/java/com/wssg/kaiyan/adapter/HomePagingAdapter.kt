package com.wssg.kaiyan.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.bean.VideoInfoData
import com.wssg.kaiyan.utils.durationToStr
import com.wssg.lib.base.widget.banner.BannerBean
import com.wssg.lib.base.widget.banner.MyBannerView

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class HomePagingAdapter : PagingDataAdapter<VideoInfoData, RecyclerView.ViewHolder>(COMPARATOR) {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            holder as HomePagingAdapter.BannerHolder
            val bannerBeans = mutableListOf<BannerBean>()
            for (d in getItem(0)!!.bannerData!!) {
                bannerBeans.add(BannerBean(d.coverUrl, d.title))
            }
            holder.itemView.findViewById<MyBannerView>(R.id.banner_rv_homeFrag)
                .submitData(bannerBeans)
        } else {
            holder as HomePagingAdapter.HomeDataHolder
            holder.apply {
                val bean = getItem(position)!!
                titleTv.text = bean.title
                descrTv.text = bean.author
                kindTv.text = bean.kind
                durationTv.text =
                    bean.duration.durationToStr()
                headIv.let {
                    Glide.with(this.itemView)
                        .load(bean.authorHeader)
                        .into(
                            it
                        )
                }
                picIv.let {
                    Glide.with(this.itemView)
                        .load(bean.coverUrl)
                        .into(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 0) BannerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_header, parent, false)
        ) else HomeDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    inner class HomeDataHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTv: TextView
        var kindTv: TextView
        var descrTv: TextView
        var durationTv: TextView
        var picIv: ImageView
        var headIv: ImageView
        var videoCardView: CardView

        init {
            titleTv = view.findViewById(R.id.tv_rvItemHome_title)
            kindTv = view.findViewById(R.id.tv_rvItemHome_kind)
            descrTv = view.findViewById(R.id.tv_rvItemHome_description)
            durationTv = view.findViewById(R.id.tv_rvItemHome_duration)
            picIv = view.findViewById(R.id.iv_rvItemHome_pic)
            headIv = view.findViewById(R.id.iv_rvItemHome_head)
            videoCardView = view.findViewById(R.id.cd_rvItemHome_video)
            videoCardView.setOnClickListener {
                listener?.onClicked(getItem(absoluteAdapterPosition)!!)
            }
        }

    }

    inner class BannerHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.findViewById<MyBannerView>(R.id.banner_rv_homeFrag)
                .setOnItemClicked(object : MyBannerView.OnItemClicked {
                    @SuppressLint("SimpleDateFormat")
                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onLicked(position: Int) {
                        listener?.onClicked(getItem(0)!!.bannerData!![position])
                    }
                })
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