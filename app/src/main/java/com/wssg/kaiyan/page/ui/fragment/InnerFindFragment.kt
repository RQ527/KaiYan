package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
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
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.page.adapter.*
import com.wssg.kaiyan.page.ui.activity.CategoryActivity
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
import com.wssg.kaiyan.page.viewmodel.InnerFindFragmentViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import com.wssg.lib.base.net.DataState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class InnerFindFragment : BaseVmFragment<InnerFindFragmentViewModel>() {
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
                    bindAdapterToPaging(viewModel.getFollowData(), adapter)
                    adapter.setOnClickedListener(object : FollowRvAdapter.OnClickedListener {
                        override fun onClicked(detailBean: VideoInfoData) {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    PlayVideoActivity::class.java
                                ).putExtra("videoBean", detailBean)
                            )
                        }
                    })
                    LinearLayoutManager(requireContext())
                }
                1 -> {
                    var adapter: CategoriesRvAdapter
                    viewModel.allCategories.observe(requireActivity()) {
                        if (it.dataState == DataState.STATE_SUCCESS) {
                            adapter =
                                CategoriesRvAdapter(it.itemList!!)
                            recyclerView.adapter = adapter
                            adapter.setOnClickedListener(object :
                                CategoriesRvAdapter.OnClickedListener {
                                override fun onClicked(categoryData: CategoryBean) {
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            CategoryActivity::class.java
                                        ).putExtra("categoryBean", categoryData)
                                    )
                                }
                            })
                        }
                    }
                    viewModel.getAllCategories()
                    GridLayoutManager(requireContext(), 2)
                }
                2 -> {
                    val adapter = CommunityRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getCommunityData(), adapter)
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
                3 -> {
                    val categoryBean =
                        requireArguments().getSerializable("categoryBean")!! as CategoryBean
                    val adapter = CategoryRecRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(
                        viewModel.getCategoryRecommendData(categoryBean.tagId),
                        adapter
                    )
                    LinearLayoutManager(requireContext())
                }
                4 -> {
                    val categoryBean =
                        requireArguments().getSerializable("categoryBean")!! as CategoryBean
                    val adapter = CategorySquareRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getCategorySquareData(categoryBean.tagId), adapter)
                    LinearLayoutManager(requireContext())
                }
                else -> error("inner find fragment 位置错误")
            }
    }
}