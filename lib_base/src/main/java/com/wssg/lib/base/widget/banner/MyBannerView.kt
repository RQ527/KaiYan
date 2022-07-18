package com.wssg.lib.base.widget.banner

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.wssg.lib.base.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:不能说封装吧，算是包装，方便调用而已，banner实现思路是vp+handler
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
    private var data: List<BannerBean>? = null//外部设置数据
    private val bannerHandler = BannerHandler()//用handler实现固定时间切换vp

    init {
        //加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, this, true)
        vpBanner = findViewById(R.id.vp_banner)
        tvTitle = findViewById(R.id.tv_banner_title)
        llPoint = findViewById(R.id.ll_banner_point)
        initView()
    }

    private fun initView() {
        val urls = mutableListOf<String>()

        if (data != null) {//防止没有设置数据
            if (data!!.size > 10) data = data?.subList(0, 11)
            for (i in data!!.indices) {
                urls.add(data!![i].url)
                addPoint(i)
            }
        }
        val adapter = BannerAdapter(urls)
        vpBanner.adapter = adapter
        vpBanner.setPageTransformer(BannerTranAnim())
        vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (data != null) {//切换选中的小圆点
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
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {//拖动就停止自动滑动
                    bannerHandler.removeCallbacksAndMessages(null)
                }
                if (state == ViewPager2.SCROLL_STATE_IDLE) {//空闲即开始自动滑动
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
        if (llPoint.childCount < 10) {
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
            vpBanner.setCurrentItem(vpBanner.currentItem+1, 500)//用假托动模拟自动滑动
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