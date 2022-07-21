package com.wssg.kaiyan.page.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.page.ui.fragment.InnerFindFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class CategoryDetailPagerAdapter (_fragmentActivity: FragmentActivity,val categoryBean: CategoryBean) :
    FragmentStateAdapter(_fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        Bundle().run {
            this.putString("kind", (position+3).toString())
            this.putSerializable("categoryBean", categoryBean)
            val fragment = InnerFindFragment()
            fragment.arguments = this
            fragment
        }
}