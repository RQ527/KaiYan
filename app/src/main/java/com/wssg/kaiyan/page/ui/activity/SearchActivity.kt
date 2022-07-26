package com.wssg.kaiyan.page.ui.activity

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.SearchResultData
import com.wssg.kaiyan.model.pagingsource.SearchResultPagingSource
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.adapter.SearchResultRvAdapter
import com.wssg.kaiyan.page.viewmodel.SearchActivityViewModel
import com.wssg.kaiyan.utils.toast
import com.wssg.kaiyan.widget.view.FlowLayout
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity
import com.wssg.lib.base.net.DataState

class SearchActivity : BaseVmActivity<SearchActivityViewModel>() {
    private val recyclerView by R.id.rv_searchActivity.view<RecyclerView>()
    private val searchBt by R.id.bt_searchActivity_search.view<Button>()
    private val backBt by R.id.bt_searchActivity_back.view<Button>()
    private val inputEt by R.id.et_searchActivity_input.view<EditText>()
    private val flowLayout by R.id.fl_searchActivity_hot.view<FlowLayout>()
    private val hotKeyTv by R.id.tv_searchActivity_hot.view<TextView>()

    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            context.startActivity(
                Intent(
                    context,
                    SearchActivity::class.java
                ), bundle
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val adapter = SearchResultRvAdapter()
        recyclerView.adapter = adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
        recyclerView.layoutManager = LinearLayoutManager(this)
        backBt.setOnClickListener { onBackPressed() }
        searchBt.setOnClickListener {
            if (inputEt.text.toString() != "") {
                hotKeyTv.visibility = View.GONE
                flowLayout.visibility = View.GONE
                bindAdapterToPaging(
                    viewModel.getSearchResultPagingData(inputEt.text.toString()),
                    adapter
                )
            }
        }
        adapter.setOnClickedListener { searchResultData, view ->
            if (searchResultData.type == "video") {
                PlayVideoActivity.startActivity(
                    this,
                    searchResultData.videoInfoData!!,
                    ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        view,
                        "video"
                    ).toBundle()
                )
            }
        }
        viewModel.hotKeysLiveData.observe(this) {
            if (it.dataState==DataState.STATE_ERROR) toast("获取热词失败")
            if (it.dataState == DataState.STATE_SUCCESS) {
                flowLayout.addData(it.itemList!!)
            }
        }
        flowLayout.setOnItemClickedListener {
            hotKeyTv.visibility = View.GONE
            flowLayout.visibility = View.GONE
            inputEt.setText(it)
            bindAdapterToPaging(
                viewModel.getSearchResultPagingData(it),
                adapter
            )
        }
        viewModel.getHotKeys()
    }
}