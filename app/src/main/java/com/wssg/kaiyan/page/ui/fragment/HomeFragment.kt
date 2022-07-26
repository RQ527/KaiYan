package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.page.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.adapter.HomePagingAdapter
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
import com.wssg.kaiyan.utils.toast
import com.wssg.kaiyan.widget.view.MyRefreshView
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import kotlinx.coroutines.launch

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HomeFragment : BaseVmFragment<HomeFragmentViewModel>() {
    private val refreshLayout by R.id.srl_homeFrag.view<MyRefreshView>()
    private val recyclerView by R.id.rv_homeFrag.view<RecyclerView>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val pagingAdapter = HomePagingAdapter()
        recyclerView.adapter = pagingAdapter
            .withLoadStateFooter(PagingFooterAdapter {
                pagingAdapter.retry()
            })
        lifecycleScope.launch {
            bindAdapterToPaging(viewModel.getHomePagingData(), pagingAdapter)
        }
        pagingAdapter.setOnClickedListener { detailBean, view ->
            PlayVideoActivity.startActivity(
                requireContext(),
                detailBean,
                ActivityOptions.makeSceneTransitionAnimation(requireActivity(), view, "video")
                    .toBundle()
            )
        }
        refreshLayout.keyName = "homeFragRefresh"
        refreshLayout.setOnRefreshListener {
            pagingAdapter.refresh()
        }
        pagingAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) toast("日报加载失败")
        }
    }
}