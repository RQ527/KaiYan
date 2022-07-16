package com.wssg.kaiyan.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.bean.HomeBean
import com.wssg.kaiyan.bean.HomeDetailBean
import com.wssg.kaiyan.bean.VideoDetailBean
import com.wssg.kaiyan.utils.durationToStr
import com.wssg.lib.base.widget.banner.BannerBean
import com.wssg.lib.base.widget.banner.MyBannerView
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:
 */
class HomeRvAdapter(
    private var data: List<VideoDetailBean>,
    private var bannerData: List<VideoDetailBean>
) :
    RecyclerView.Adapter<HomeRvAdapter.InnerHolder>() {

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTv: TextView? = null
        var kindTv: TextView? = null
        var descrTv: TextView? = null
        var durationTv: TextView? = null
        var picIv: ImageView? = null
        var headIv: ImageView? = null
        var videoCardView: CardView? = null

        init {
            if (itemViewType != 0) {
                titleTv = view.findViewById(R.id.tv_rvItemHome_title)
                kindTv = view.findViewById(R.id.tv_rvItemHome_kind)
                descrTv = view.findViewById(R.id.tv_rvItemHome_description)
                durationTv = view.findViewById(R.id.tv_rvItemHome_duration)
                picIv = view.findViewById(R.id.iv_rvItemHome_pic)
                headIv = view.findViewById(R.id.iv_rvItemHome_head)
                videoCardView = view.findViewById(R.id.cd_rvItemHome_video)
            } else {
                view.findViewById<MyBannerView>(R.id.banner_rv_homeFrag)
                    .setOnItemClicked(object : MyBannerView.OnItemClicked {
                        @SuppressLint("SimpleDateFormat")
                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onLicked(position: Int) {
                            listener?.onClicked(bannerData[position])
                        }
                    })
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return if (viewType == 0) InnerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_rv_header, parent, false)
        ) else {
            InnerHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val bannerBeans = mutableListOf<BannerBean>()
            for (d in bannerData) {
                bannerBeans.add(BannerBean(d.coverUrl, d.title))
            }
            holder.itemView.findViewById<MyBannerView>(R.id.banner_rv_homeFrag)
                .submitData(bannerBeans)
        } else {

            holder.apply {
                val bean = data[position-1 % data.size]
                titleTv?.text = bean.title
                descrTv?.text = bean.author
                kindTv?.text = bean.kind
                durationTv?.text =
                    bean.duration.durationToStr()
                headIv?.let {
                    Glide.with(this.itemView)
                        .load(bean.authorHeader)
                        .into(
                            it
                        )
                }
                picIv?.let {
                    Glide.with(this.itemView)
                        .load(bean.coverUrl)
                        .into(it)
                }
            }

        }
    }

    override fun getItemCount(): Int = data.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        return 1
    }

    interface OnClickedListener {
        fun onClicked(detailBean: VideoDetailBean)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}