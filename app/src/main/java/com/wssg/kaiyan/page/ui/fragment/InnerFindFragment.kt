package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.CommunityRvAdapter
import com.wssg.kaiyan.page.adapter.FollowRvAdapter
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.viewmodel.FindFragmentViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class InnerFindFragment : BaseVmFragment<FindFragmentViewModel>() {
    private var position: Int = 0
    private val recyclerView by R.id.rv_innerFindFrag.view<RecyclerView>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        position = arguments?.getString("kind")!!.toInt()
        return inflater.inflate(R.layout.fragment_inner_find, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager =
        when (position) {
            0 -> {
                val adapter = FollowRvAdapter()
                recyclerView.adapter =
                    adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                Log.d("RQ", "onViewCreated: $position")
                bindAdapterToPaging(viewModel.getFollowData(),adapter)
                LinearLayoutManager(requireContext())
            }
            1 -> {
                GridLayoutManager(requireContext(),2)
            }
            2 -> {
                val adapter = CommunityRvAdapter()
                recyclerView.adapter =
                    adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                bindAdapterToPaging(viewModel.getCommunityData(), adapter)
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
            else -> error("inner find fragment 位置错误")
        }
    }
}