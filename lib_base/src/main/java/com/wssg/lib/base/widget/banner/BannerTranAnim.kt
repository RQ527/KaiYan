package com.wssg.lib.base.widget.banner

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description: //banner切换动画
 */
class BannerTranAnim : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        var pos = position
        if (pos>0) pos = -pos
        page.scaleX = 1 + pos * 0.01f
        page.scaleY = 1 + pos * 0.2f
    }
}