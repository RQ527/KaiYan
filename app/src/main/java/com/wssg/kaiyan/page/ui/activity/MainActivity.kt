package com.wssg.kaiyan.page.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomView.itemIconTintList = null
        viewPager.adapter = KaiYanFragmentPagerAdapter(this)
        bottomView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.find -> viewPager.currentItem = 1
                R.id.hot -> viewPager.currentItem = 2
                R.id.mine -> viewPager.currentItem = 3
            }
            return@setOnItemSelectedListener true
        }
        viewPager.isUserInputEnabled = false
        searchBt.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
    }
}