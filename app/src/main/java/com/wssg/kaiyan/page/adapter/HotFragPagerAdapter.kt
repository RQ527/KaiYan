package com.wssg.kaiyan.page.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wssg.kaiyan.page.Constant
import com.wssg.kaiyan.page.ui.fragment.OnlyRvFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/22
 * @Description:
 */
class HotFragPagerAdapter(_fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(_fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        Bundle().run {
            this.putString(
                "type", when (position) {
                    0 -> Constant.HOT_FRAGMENT_MONTHLY
                    1 -> Constant.HOT_FRAGMENT_WEEKLY
                    2 -> Constant.HOT_FRAGMENT_ALL
                    else -> error("HotFragPagerAdapter位置错误")
                }
            )
            val fragment = OnlyRvFragment()
            fragment.arguments = this
            fragment
        }
}