package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.FindFragPagerAdapter
import com.wssg.kaiyan.page.ui.anim.FindVPAnimation
import com.wssg.lib.base.base.ui.BaseFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class FindFragment : BaseFragment() {
    val indexTab by R.id.tl_findFrag_index.view<TabLayout>()
    val viewPager by R.id.vp_findFrag.view<ViewPager2>()
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = FindFragPagerAdapter(requireActivity())
        TabLayoutMediator(indexTab, viewPager) { tab, positon ->
            tab.text =
                when (positon) {
                    0 -> "关注"
                    1 -> "分类"
                    2 -> "推荐"
                    else -> error("tabLayout位置错误")
                }
        }.attach()
        viewPager.setPageTransformer(FindVPAnimation())
        viewPager.offscreenPageLimit = 2
    }
}