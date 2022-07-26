package com.wssg.kaiyan.widget.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Scroller
import android.widget.TextView
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.utils.dpToPx
import com.wssg.lib.base.utils.achieveValue
import com.wssg.lib.base.utils.saveValue
import java.util.*
import kotlin.math.abs

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/23
 * @Description:    自定义下拉刷新控件
 */
class MyRefreshView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent3 {
    /**
     * 是否开启此控件的刷新功能，因为我发现在一些复杂的嵌套情况下会出问题，因此选择性开启
     */
    var isUsed = true

    /**
     * 刷新头部
     */
    @SuppressLint("InflateParams")
    private val headerView: View =
        LayoutInflater.from(context).inflate(R.layout.refresh_top, null)
    private lateinit var recyclerView: RecyclerView

    /**
     * 系统最小的滑动距离
     */
    private val minDist = ViewConfiguration.get(context).scaledTouchSlop

    /**
     * 滑动帮助类
     */
    private val mScroller = Scroller(getContext())

    /**
     * 头部高度，其实可以进一步把它解耦出去
     */
    private val headerHeight = 100

    /**
     * 刷新提示的一些控件
     */
    private val animIv: ImageView
    private val tipTv: TextView
    private val timeTv: TextView

    /**
     * 刷新提示语
     */
    private val refreshingText = "正在刷新"
    private val refreshedText = "刷新完成"
    private val readyToRefreshText = "下拉刷新"
    private val canRefreshText = "释放刷新"

    /**
     * 将具体刷新操作放到了handler同时利用延时消息达到动画暂停的效果，让动画不那么快就结束
     */
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (state == REFRESHING) {
                listener?.onRefreshListen()
                if (animator.isRunning) animator?.cancel()
            } else if (state == REFRESHED) {
                mScroller.startScroll(0, scrollY, 0, -scrollY)
                invalidate()
                state = IDLE
            }
        }
    }

    /**
     * 属性动画
     */
    private lateinit var animator: ObjectAnimator

    /**
     * 刷新状态
     */
    private val READY_TO_REFRESH = 0
    private val CAN_REFRESH = 1
    private val REFRESHING = 2
    private val REFRESHED = 3
    private val IDLE = 4

    /**
     * 初始化状态为空闲
     */
    private var state = IDLE

    /**
     * 刷新监听
     */
    private var listener: OnRefreshListener? = null

    /**
     * 设置的名称用作记录刷新时间的唯一名称,如果没设置直接保错
     */
    var keyName: String? = null

    /**
     * 初始坐标
     */
    private var startX = 0
    private var startY = 0

    private var distance = 0

    init {//加入头部布局
        headerView.layoutParams =
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                headerHeight.dpToPx()
            ).apply { topMargin = -headerHeight.dpToPx() }
        addView(headerView, 0)
        tipTv = headerView.findViewById(R.id.tv_refresh_layout_tip)
        timeTv = headerView.findViewById(R.id.tv_refresh_layout_time)
        animIv = headerView.findViewById(R.id.iv_refresh_layout_anim)
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 2) throw RuntimeException("MyRefreshView只能有一个子布局")
        val view = getChildAt(1)
        if (view is RecyclerView) recyclerView = view
        else throw RuntimeException("MyRefreshView子布局只能是RecyclerView")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        keyName?.let { timeTv.text = achieveValue(it) }
    }

    /**
     * 帮助平滑回弹的方法
     */
    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            val currY = mScroller.currY.toFloat()
            scrollTo(0, currY.toInt())
            invalidate()
        }
    }

    /**
     * 刷新的动画
     */
    private fun refreshingAnim() {
        animator = ObjectAnimator.ofFloat(animIv, "rotation", 0f, 360f)
        animator.duration = 1000
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animIv.setImageResource(R.drawable.refresh_loading)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                tipTv.text = refreshingText
            }

            @SuppressLint("SetTextI18n")
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                tipTv.text = refreshedText
                val calendar = Calendar.getInstance()
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val hour = calendar.get(Calendar.HOUR)
                val minute = calendar.get(Calendar.MINUTE)
                timeTv.text = "上次刷新时间:$month/$day $hour:$minute"
                if (keyName == null && isUsed) throw RuntimeException("keyName未被初始化")
                if (isUsed) saveValue(keyName!!, timeTv.text.toString())
                state = REFRESHED
                animIv.visibility = View.INVISIBLE
                mHandler.sendEmptyMessageDelayed(0, 500)
            }
        })
        animator.start()
        mHandler.sendEmptyMessageDelayed(0, 500)
    }

    /**
     * 准备刷新的动画
     */
    private fun readyToRefresh() {
        if (state != READY_TO_REFRESH) {
            animIv.visibility = View.VISIBLE
            animIv.setImageResource(R.drawable.refresh_arrow)
            val animator = ObjectAnimator.ofFloat(animIv, "rotation", 180f, 0f)
            animator.duration = 500
            animator.interpolator = LinearInterpolator()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    tipTv.text = readyToRefreshText
                }
            })
            animator.start()
        }
    }

    /**
     * 可以刷新的动画
     */
    private fun canRefresh() {
        if (state != CAN_REFRESH) {
            val animator = ObjectAnimator.ofFloat(animIv, "rotation", 0f, 180f)
            animator.duration = 500
            animator.interpolator = LinearInterpolator()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    tipTv.text = canRefreshText
                }
            })
            animator.start()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isUsed) return super.onTouchEvent(event)
        var isEaten = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                distance = (event.y - startY).toInt()
                //rv滑到顶部并且是下滑并且距离大于滑动最小值才滑动
                if (rvIsToTop() && distance >= 0) {
                    isEaten = true
                    state = if (distance >= headerView.height) {
                        if (state != CAN_REFRESH)//没有
                            canRefresh()
                        CAN_REFRESH
                    } else {
                        if (state != READY_TO_REFRESH)
                            readyToRefresh()
                        READY_TO_REFRESH
                    }
                    scrollTo(0, -distance)
                } else if (rvIsToTop() && distance < 0) {
                    scrollTo(0, 0)
                    //这是下拉又上滑的情况，把上滑分发给RV
                    state = IDLE
                    //分发剩余的的滑动
                    dispatchTouchEvent(
                        MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN,
                            event.x,
                            event.y,
                            event.metaState
                        )
                    )
                    isEaten = false
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (state == CAN_REFRESH) {
                    mScroller.startScroll(0, scrollY, 0, -scrollY - headerView.height)
                    state = REFRESHING
                    refreshingAnim()
                } else if (state == READY_TO_REFRESH) {//预备滑动说明还不能话，退回去
                    mScroller.startScroll(0, scrollY, 0, -scrollY)
                    state = IDLE
                }
                invalidate()//启动回弹
            }
        }
        return isEaten
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isUsed) return super.onInterceptTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (state != IDLE) return true//拦截刷新的时候的骚操作
                startX = ev.x.toInt()
                startY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                //rv滑到顶部，达到最小滑动距离，下滑，竖滑大于横滑才拦截事件
                if (rvIsToTop() && abs(ev.y - startY) > abs(ev.x - startX)
                    && ev.y - startY > 0
                    && abs(ev.y - startY) > minDist
                ) return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun rvIsToTop(): Boolean = !recyclerView.canScrollVertically(-1)//RV: 到顶了~ >_<

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL//只接受RV的垂直滑动
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {}
    override fun onStopNestedScroll(target: View, type: Int) {}

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (rvIsToTop() && isUsed) {//接收到来自RV滑到顶后剩余的滑动，于是就让父布局重新分发一下事件给自己消耗
            requestDisallowInterceptTouchEvent(false)
        }
    }

    fun interface OnRefreshListener {
        fun onRefreshListen()
    }

    fun setOnRefreshListener(l: OnRefreshListener) {
        listener = l
    }
}