package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wssg.kaiyan.page.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.adapter.HomePagingAdapter
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
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
    val refreshLayout by R.id.srl_homeFrag.view<SwipeRefreshLayout>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_homeFrag)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val pagingAdapter = HomePagingAdapter()
        recyclerView.adapter = pagingAdapter
            .withLoadStateFooter(PagingFooterAdapter {
                pagingAdapter.retry()
            })
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getHomePagingData().collect {
                    pagingAdapter.submitData(it)
                }
            }
        }
        pagingAdapter.setOnClickedListener(object : HomePagingAdapter.OnClickedListener {
            override fun onClicked(detailBean: VideoInfoData, view: View) {
                val bundle =
                    ActivityOptions.makeSceneTransitionAnimation(requireActivity(), view, "video")
                        .toBundle()
                startActivity(
                    Intent(
                        requireContext(),
                        PlayVideoActivity::class.java
                    ).putExtra("videoBean", detailBean), bundle
                )
            }
        })
//        pagingAdapter.addLoadStateListener {
//            when(it.refresh){
//                is LoadState.Loading->{
//                    refreshLayout.isRefreshing = true
//                }
//                is LoadState.Error,is LoadState.NotLoading ->{
//                    refreshLayout.isRefreshing = false
//                }
//            }
//        }
        refreshLayout.setOnRefreshListener {
            if (refreshLayout.isRefreshing) {
                pagingAdapter.refresh()
                refreshLayout.isRefreshing = false
            }
        }
    }
}