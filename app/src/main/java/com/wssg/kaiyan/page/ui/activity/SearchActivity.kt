package com.wssg.kaiyan.page.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.SearchResultData
import com.wssg.kaiyan.model.pagingsource.SearchResultPagingSource
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.adapter.SearchResultRvAdapter
import com.wssg.kaiyan.page.viewmodel.SearchActivityViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

class SearchActivity : BaseVmActivity<SearchActivityViewModel>() {
    val recyclerView by R.id.rv_searchActivity.view<RecyclerView>()
    val searchBt by R.id.bt_searchActivity_search.view<Button>()
    val backBt by R.id.bt_searchActivity_back.view<Button>()
    val inputEt by R.id.et_searchActivity_input.view<EditText>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val adapter = SearchResultRvAdapter()
        recyclerView.adapter = adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
        recyclerView.layoutManager = LinearLayoutManager(this)
        backBt.setOnClickListener { onBackPressed() }
        searchBt.setOnClickListener {
            if (inputEt.text.toString() != "") {
                bindAdapterToPaging(
                    viewModel.getSearchResultPagingData(inputEt.text.toString()),
                    adapter
                )
            }
        }
        adapter.setOnClickedListener(object : SearchResultRvAdapter.OnClickedListener {
            override fun onClicked(searchResultData: SearchResultData) {
                if (searchResultData.type == "video") {
                    startActivity(
                        Intent(
                            this@SearchActivity,
                            PlayVideoActivity::class.java
                        ).putExtra("videoBean", searchResultData.videoInfoData)
                    )
                }
            }
        })
    }
}