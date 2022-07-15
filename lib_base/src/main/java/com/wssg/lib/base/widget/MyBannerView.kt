package com.wssg.lib.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide.init
import com.wssg.lib.base.R
import com.wssg.lib.base.adapter.BannerAdapter
import com.wssg.lib.base.bean.BannerBean
import java.text.FieldPosition

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:
 */
class MyBannerView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attr, defStyleAttr) {
    private var lastPosition = 0
    private var llPoint: LinearLayout
    private var vpBanner: ViewPager2
    private var tvTitle: TextView
    private var data: List<BannerBean>? = null
    val bannerHandler = BannerHandler()

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, this, true)
        vpBanner = findViewById(R.id.vp_banner)
        tvTitle = findViewById(R.id.tv_banner_title)
        llPoint = findViewById(R.id.ll_banner_point)
        initView()
    }

    private fun initView() {
        val urls = mutableListOf<String>()

        if (data != null) {
            if (data!!.size > 10) data = data?.subList(0, 11)
            for (i in data!!.indices) {
                urls.add(data!![i].url)
                addPoint(i)
            }
        }
        val adapter = BannerAdapter(this.context, urls)
        vpBanner.adapter = adapter
        vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (data != null) {
                    tvTitle.text = data!![position % data!!.size].title
                    llPoint.getChildAt(position % data!!.size)?.isEnabled = true
                    if (position != lastPosition) {
                        llPoint.getChildAt(lastPosition % data!!.size)?.isEnabled = false
                    }
                    lastPosition = position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    bannerHandler.removeCallbacksAndMessages(null)
                }
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    bannerHandler.removeCallbacksAndMessages(null)
                    bannerHandler.sendEmptyMessageDelayed(0, 3000)
                }
            }
        })
        adapter.setItemClickedListener(object : BannerAdapter.ItemOnClickedListener {
            override fun dispose(position: Int) {
                listener?.onLicked(position % urls.size)
            }
        })
    }

    private fun addPoint(i: Int) {
        if (llPoint.childCount <= 10) {
            //添加指示点
            val point = ImageView(this.context)
            point.setBackgroundResource(R.drawable.point_selector)
            //设置每个指示点的大小
            val params = LinearLayout.LayoutParams(20, 20)
            //设置第一个高亮
            if (i == 0) {
                point.isEnabled = true
            } else {
                point.isEnabled = false
                //设置point的间距
                params.leftMargin = 10
            }
            point.layoutParams = params
            llPoint.addView(point)
        }
    }

    fun submitData(data: List<BannerBean>) {
        bannerHandler.removeCallbacksAndMessages(null)
        this.data = data
        initView()
//        requestLayout()
        bannerHandler.sendEmptyMessageDelayed(0, 3000)
    }

    @SuppressLint("HandlerLeak")
    inner class BannerHandler() : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            vpBanner.currentItem++
            sendEmptyMessageDelayed(0, 3000)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bannerHandler.removeCallbacksAndMessages(null)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bannerHandler.removeCallbacksAndMessages(null)
        bannerHandler.sendEmptyMessageDelayed(0, 3000)
    }

    var listener: OnItemClicked? = null

    interface OnItemClicked {
        fun onLicked(position: Int)
    }

    fun setOnItemClicked(l: OnItemClicked) {
        this.listener = l
    }
}