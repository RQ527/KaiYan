package com.wssg.kaiyan.page.ui.anim

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/23
 * @Description:
 */
class HotVPAnimation: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {

        when (position) {
            in -1f..0f -> {
                page.rotationY = -30 * position
            }
            in 0f..1f -> {
                page.rotationY = -30 * position
            }
        }

    }
}