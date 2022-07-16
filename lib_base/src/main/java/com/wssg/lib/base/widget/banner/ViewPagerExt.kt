package com.wssg.lib.base.widget.banner

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description: 模拟viewpager的拖动代替viewpager的自动切换以改变滑动速度
 */
fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // 使用viewpager2.getWidth()获取
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)//拖动距离
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)//拖动
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }//开启假拖动
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }//关闭假拖动
        override fun onAnimationCancel(animation: Animator?) { }
        override fun onAnimationRepeat(animation: Animator?) { }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}