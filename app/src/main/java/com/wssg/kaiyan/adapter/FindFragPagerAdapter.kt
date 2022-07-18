package com.wssg.kaiyan.adapter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wssg.kaiyan.ui.fragment.InnerFindFragment

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
            this.putString("kind", position.toString())
            val fragment = InnerFindFragment()
            fragment.arguments = this
            fragment
        }
}

