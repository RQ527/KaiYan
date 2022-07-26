package com.wssg.kaiyan.page.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wssg.kaiyan.page.ui.fragment.FindFragment
import com.wssg.kaiyan.page.ui.fragment.HomeFragment
import com.wssg.kaiyan.page.ui.fragment.HotFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class KaiYanFragmentPagerAdapter(_fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(_fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> FindFragment()
            2 -> HotFragment()
            else -> error("Fragment Error")
        }


}