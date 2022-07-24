package com.wssg.kaiyan.page.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wssg.kaiyan.page.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.KaiYanFragmentPagerAdapter
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

class MainActivity : BaseVmActivity<HomeFragmentViewModel>() {
    private val viewPager by R.id.vp_activityMain.view<ViewPager2>()
    private val bottomView by R.id.bnv_home.view<BottomNavigationView>()
    private val searchBt by R.id.bt_toolbar_search.view<Button>()
    private val textView by R.id.tv_toolbar.view<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomView.itemIconTintList = null
        viewPager.adapter = KaiYanFragmentPagerAdapter(this)
        bottomView.setOnItemSelectedListener {
            textView.text = when (it.itemId) {
                R.id.home -> {
                    viewPager.currentItem = 0
                    "日报"
                }
                R.id.find -> {
                    viewPager.currentItem = 1
                    "发现"
                }
                R.id.hot -> {
                    viewPager.currentItem = 2
                    "热门"
                }
                R.id.mine -> {
                    viewPager.currentItem = 3
                    "我的"
                }
                else -> error("MainActivity位置匹配错误")
            }
            return@setOnItemSelectedListener true
        }
        viewPager.isUserInputEnabled = false
        searchBt.setOnClickListener {
            val bundle =
                ActivityOptions.makeSceneTransitionAnimation(this, searchBt, "search")
                    .toBundle()
            SearchActivity.startActivity(this, bundle)
        }

    }
}