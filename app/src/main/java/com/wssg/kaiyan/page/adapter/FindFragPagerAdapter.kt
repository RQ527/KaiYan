package com.wssg.kaiyan.page.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wssg.kaiyan.page.Constant
import com.wssg.kaiyan.page.ui.fragment.ReuseFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class FindFragPagerAdapter(_fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(_fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        Bundle().run {
            this.putString(
                "type", when (position) {
                    0 -> Constant.FIND_FRAGMENT_FOLLOW
                    1 -> Constant.FIND_FRAGMENT_CATEGORY
                    2 -> Constant.FIND_FRAGMENT_RECOMMEND
                    else -> error("FindFragPagerAdapter位置错误")
                }
            )
            val fragment = ReuseFragment()
            fragment.arguments = this
            fragment
        }
}

