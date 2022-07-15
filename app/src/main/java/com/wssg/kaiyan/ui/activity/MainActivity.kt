package com.wssg.kaiyan.ui.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wssg.kaiyan.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.adapter.FragmentPagerAdapter
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

class MainActivity : BaseVmActivity<HomeFragmentViewModel>() {
    private val viewPager by R.id.vp_activityMain.view<ViewPager2>()
    val bottomView by R.id.bnv_home.view<BottomNavigationView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomView.itemIconTintList = null
        viewPager.adapter = FragmentPagerAdapter(this)
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
    }
}