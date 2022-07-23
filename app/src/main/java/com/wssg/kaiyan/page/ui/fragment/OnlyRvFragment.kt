package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.RankBean
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.page.Constant.CATEGORY_ACTIVITY_RECOMMEND
import com.wssg.kaiyan.page.Constant.CATEGORY_ACTIVITY_SQUARE
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_CATEGORY
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_FOLLOW
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_RECOMMEND
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_ALL
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_MONTHLY
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_WEEKLY
import com.wssg.kaiyan.page.adapter.*
import com.wssg.kaiyan.page.ui.activity.CategoryActivity
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
import com.wssg.kaiyan.page.viewmodel.InnerFragmentViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import com.wssg.lib.base.net.DataState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class OnlyRvFragment : BaseVmFragment<InnerFragmentViewModel>() {
    private var type: String = ""
    private val recyclerView by R.id.rv_innerFindFrag.view<RecyclerView>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        type = arguments?.getString("type")!!
        return inflater.inflate(R.layout.fragment_inner_find, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager =
            when (type) {
                FIND_FRAGMENT_FOLLOW -> {
                    val adapter = FollowRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getFollowData(), adapter)
                    adapter.setOnClickedListener(object : FollowRvAdapter.OnClickedListener {
                        override fun onClicked(detailBean: VideoInfoData, view: View) {
                            val bundle =
                                ActivityOptions.makeSceneTransitionAnimation(
                                    requireActivity(),
                                    view,
                                    "video"
                                )
                                    .toBundle()
                            startActivity(
                                Intent(
                                    requireContext(),
                                    PlayVideoActivity::class.java
                                ).putExtra("videoBean", detailBean), bundle
                            )
                        }
                    })
                    LinearLayoutManager(requireContext())
                }
                FIND_FRAGMENT_CATEGORY -> {
                    var adapter: CategoriesRvAdapter
                    viewModel.allCategoriesLiveData.observe(requireActivity()) {
                        if (it.dataState == DataState.STATE_SUCCESS) {
                            adapter =
                                CategoriesRvAdapter(it.itemList!!)
                            recyclerView.adapter = adapter
                            adapter.setOnClickedListener(object :
                                CategoriesRvAdapter.OnClickedListener {
                                override fun onClicked(categoryData: CategoryBean, view: View) {
                                    val bundle =
                                        ActivityOptions.makeSceneTransitionAnimation(
                                            requireActivity(),
                                            view,
                                            "category"
                                        )
                                            .toBundle()
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            CategoryActivity::class.java
                                        ).putExtra("categoryBean", categoryData), bundle
                                    )
                                }
                            })
                        }
                    }
                    viewModel.getAllCategories()
                    GridLayoutManager(requireContext(), 2)
                }
                FIND_FRAGMENT_RECOMMEND -> {
                    val adapter = CommunityRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getCommunityData(), adapter)
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
                CATEGORY_ACTIVITY_RECOMMEND -> {
                    val categoryBean =
                        requireArguments().getSerializable("categoryBean")!! as CategoryBean
                    val adapter = CategoryRecRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    adapter.setOnClickedListener(object : CategoryRecRvAdapter.OnClickedListener {
                        override fun onClicked(detailBean: VideoInfoData, view: View) {
                            val bundle =
                                ActivityOptions.makeSceneTransitionAnimation(
                                    requireActivity(),
                                    view,
                                    "video"
                                )
                                    .toBundle()
                            startActivity(
                                Intent(
                                    requireContext(),
                                    PlayVideoActivity::class.java
                                ).putExtra("videoBean", detailBean), bundle
                            )
                        }
                    })
                    bindAdapterToPaging(
                        viewModel.getCategoryRecommendData(categoryBean.tagId),
                        adapter
                    )
                    LinearLayoutManager(requireContext())
                }
                CATEGORY_ACTIVITY_SQUARE -> {
                    val categoryBean =
                        requireArguments().getSerializable("categoryBean")!! as CategoryBean
                    val adapter = CategorySquareRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(
                        viewModel.getCategorySquareData(categoryBean.tagId),
                        adapter
                    )
                    LinearLayoutManager(requireContext())
                }
                HOT_FRAGMENT_MONTHLY, HOT_FRAGMENT_WEEKLY, HOT_FRAGMENT_ALL -> {
                    val adapter = HotRankRvAdapter()
                    recyclerView.adapter = adapter
                    adapter.setOnClickedListener(object : HotRankRvAdapter.OnClickedListener {
                        override fun onClicked(videoInfoData: VideoInfoData, view: View) {
                            val bundle =
                                ActivityOptions.makeSceneTransitionAnimation(
                                    requireActivity(),
                                    view,
                                    "video"
                                )
                                    .toBundle()
                            startActivity(
                                Intent(
                                    requireContext(),
                                    PlayVideoActivity::class.java
                                ).putExtra("videoBean", videoInfoData), bundle
                            )
                        }
                    })
                    viewModel.run {
                        when (type) {
                            HOT_FRAGMENT_MONTHLY -> {
                                monthlyRankLiveData.observe(requireActivity()) {
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                                getRankList("monthly")
                            }
                            HOT_FRAGMENT_WEEKLY -> {
                                weeklyRankLiveData.observe(requireActivity()) {
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                                getRankList("weekly")
                            }
                            HOT_FRAGMENT_ALL -> {
                                historicalRankLiveData.observe(requireActivity()) {
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                                getRankList("historical")
                            }
                            else -> ""
                        }
                    }
                    LinearLayoutManager(requireContext())
                }
                else -> error("inner find fragment 位置错误")
            }
    }

    private fun swapBean(response: List<RankBean>): List<VideoInfoData> {
        val realData = mutableListOf<VideoInfoData>()
        response.forEach {
            it.data.run {
                realData.add(
                    VideoInfoData(
                        id,
                        playUrl,
                        cover.feed,
                        title,
                        category,
                        description,
                        VideoInfoData.Consumption(
                            consumption.collectionCount,
                            consumption.shareCount,
                            consumption.replyCount
                        ),
                        author.name,
                        author.description,
                        author.icon,
                        duration,
                        releaseTime,
                        null
                    )
                )
            }
        }
        return realData
    }

}