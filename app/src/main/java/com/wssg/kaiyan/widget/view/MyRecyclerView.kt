package com.wssg.kaiyan.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/23
 * @Description:   始终开启RV的嵌套滑动解决RV的item的点击事件导致RV的嵌套滑动失效的问题
 */
class MyRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val res = super.dispatchTouchEvent(ev)
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)//始终开启嵌套滑动
        return res
    }
}