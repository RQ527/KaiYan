package com.wssg.kaiyan.page.ui.activity

import android.os.Bundle
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.viewmodel.CategoryActivityViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

class CategoryActivity : BaseVmActivity<CategoryActivityViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }
}