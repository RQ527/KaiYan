package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.HotFragPagerAdapter
import com.wssg.kaiyan.page.adapter.HotRankRvAdapter
import com.wssg.kaiyan.page.ui.anim.FindVPAnimation
import com.wssg.kaiyan.page.ui.anim.HotVPAnimation
import com.wssg.lib.base.base.ui.BaseFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HotFragment : BaseFragment() {
    private val tabLayout by R.id.tl_hotFrag_index.view<TabLayout>()
    private val viewPager by R.id.vp_hotFrag.view<ViewPager2>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_hot, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = HotFragPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "月排行"
                1 -> "周排行"
                2 -> "总排行"
                else -> error("HotFragment的Tab位置匹配错误")
            }
        }.attach()
        viewPager.offscreenPageLimit = 2
        viewPager.setPageTransformer(HotVPAnimation())
    }
}