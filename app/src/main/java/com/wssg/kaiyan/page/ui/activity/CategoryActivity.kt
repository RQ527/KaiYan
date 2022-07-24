package com.wssg.kaiyan.page.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.page.adapter.CategoryDetailPagerAdapter
import com.wssg.kaiyan.page.adapter.CategoryRecRvAdapter
import com.wssg.kaiyan.page.ui.anim.HotVPAnimation
import com.wssg.kaiyan.page.viewmodel.CategoryActivityViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

class CategoryActivity : BaseVmActivity<CategoryActivityViewModel>() {
    private val viewPager by R.id.vp_categoryActivity.view<ViewPager2>()
    private val tablLayout by R.id.tl_categoryActivity_tab.view<TabLayout>()
    private val toolbar by R.id.tl_categoryActivity_title.view<Toolbar>()
    private val headIv by R.id.iv_categoryActivity_cover.view<ImageView>()

    companion object {
        fun startActivity(context: Context, categoryBean: CategoryBean, bundle: Bundle? = null) {
            context.startActivity(
                Intent(
                    context,
                    CategoryActivity::class.java
                ).putExtra("categoryBean", categoryBean), bundle
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val categoryBean: CategoryBean = intent.getSerializableExtra("categoryBean") as CategoryBean
        supportActionBar?.title = categoryBean.name
        Glide.with(this).load(categoryBean.headerImage).into(headIv)
        viewPager.adapter = CategoryDetailPagerAdapter(this, categoryBean)
        TabLayoutMediator(tablLayout, viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = "推荐"
                1 -> tab.text = "广场"
            }
        }.attach()
        viewPager.offscreenPageLimit = 2
        viewPager.setPageTransformer(HotVPAnimation())
    }
}