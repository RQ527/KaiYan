package com.wssg.kaiyan.page.ui.anim

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/23
 * @Description:
 */
class FindVPAnimation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {

        when (position) {
            in -1f..0f -> {
                page.pivotX = page.measuredWidth.toFloat()
                page.pivotY = page.measuredHeight.toFloat() / 2
                page.rotationY = 45 * position
            }
            in 0f..1f -> {
                page.pivotX = 0f
                page.pivotY = page.measuredHeight.toFloat() / 2
                page.rotationY = 45 * position
            }
        }
    }
}