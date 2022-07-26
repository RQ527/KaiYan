package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.*
import com.wssg.kaiyan.page.Constant.CATEGORY_ACTIVITY_RECOMMEND
import com.wssg.kaiyan.page.Constant.CATEGORY_ACTIVITY_SQUARE
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_CATEGORY
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_FOLLOW
import com.wssg.kaiyan.page.Constant.FIND_FRAGMENT_RECOMMEND
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_HISTORICAL
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_MONTHLY
import com.wssg.kaiyan.page.Constant.HOT_FRAGMENT_WEEKLY
import com.wssg.kaiyan.page.adapter.*
import com.wssg.kaiyan.page.ui.activity.CategoryActivity
import com.wssg.kaiyan.page.ui.activity.PhotoAndVideoActivity
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
import com.wssg.kaiyan.page.viewmodel.InnerFragmentViewModel
import com.wssg.kaiyan.utils.toast
import com.wssg.kaiyan.widget.view.MyRefreshView
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import com.wssg.lib.base.net.DataState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class ReuseFragment : BaseVmFragment<InnerFragmentViewModel>() {
    private var type: String = ""
    private val recyclerView by R.id.rv_innerFrag.view<RecyclerView>()
    private val refreshLayout by R.id.srl_innerFrag.view<MyRefreshView>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        type = arguments?.getString("type")!!
        return inflater.inflate(R.layout.fragment_reuse, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.keyName = type
        recyclerView.layoutManager =
            when (type) {
                FIND_FRAGMENT_FOLLOW -> {
                    val adapter = FollowRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getFollowData(), adapter)
                    refreshLayout.setOnRefreshListener {
                        adapter.refresh()
                    }
                    adapter.addLoadStateListener {
                        if (it.refresh is LoadState.Error) toast("关注加载失败")
                    }
                    adapter.setOnClickedListener { detailBean, view ->
                        PlayVideoActivity.startActivity(
                            requireContext(),
                            detailBean,
                            ActivityOptions.makeSceneTransitionAnimation(
                                requireActivity(),
                                view,
                                "video"
                            )
                                .toBundle()
                        )
                    }
                    LinearLayoutManager(requireContext())
                }
                FIND_FRAGMENT_CATEGORY -> {
                    var adapter: CategoriesRvAdapter
                    viewModel.allCategoriesLiveData.observe(requireActivity()) {
                        if (it.dataState==DataState.STATE_ERROR){
                            toast("获取分类数据失败")
                        }
                        if (it.dataState == DataState.STATE_SUCCESS) {
                            adapter =
                                CategoriesRvAdapter(it.itemList!!)
                            recyclerView.adapter = adapter
                            adapter.setOnClickedListener { categoryData, view ->
                                CategoryActivity.startActivity(
                                    requireContext(),
                                    categoryData,
                                    ActivityOptions.makeSceneTransitionAnimation(
                                        requireActivity(), view, "category"
                                    ).toBundle()
                                )
                            }
                        }
                    }
                    refreshLayout.setOnRefreshListener{
                        viewModel.getAllCategories()
                    }
                    viewModel.getAllCategories()
                    GridLayoutManager(requireContext(), 2)
                }
                FIND_FRAGMENT_RECOMMEND -> {
                    val adapter = CommunityRvAdapter()
                    recyclerView.adapter =
                        adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                    bindAdapterToPaging(viewModel.getCommunityData(), adapter)
                    refreshLayout.setOnRefreshListener {
                        adapter.refresh()
                    }
                    adapter.addLoadStateListener {
                        if (it.refresh is LoadState.Error) toast("发现的推荐加载失败")
                    }
                    adapter.setOnClickedListener { communityData, view ->
                        val source = PhotoAndVideoActivity.Companion.Source("", null, null)
                        if (communityData.kind == "ugc_video") {
                            source.playUrl = communityData.playUrl!!
                        } else source.picUrls = communityData.picUrls
                        source.type = communityData.kind
                        PhotoAndVideoActivity.startActivity(
                            requireContext(), source, ActivityOptions.makeSceneTransitionAnimation(
                                requireActivity(),
                                view,
                                "video"
                            ).toBundle()
                        )
                    }
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
                HOT_FRAGMENT_MONTHLY, HOT_FRAGMENT_WEEKLY, HOT_FRAGMENT_HISTORICAL -> {
                    val adapter = HotRankRvAdapter()
                    recyclerView.adapter = adapter
                    adapter.setOnClickedListener { videoInfoData, view ->
                        PlayVideoActivity.startActivity(
                            requireContext(),
                            videoInfoData,
                            ActivityOptions.makeSceneTransitionAnimation(
                                requireActivity(),
                                view,
                                "video"
                            )
                                .toBundle()
                        )
                    }
                    viewModel.run {
                        when (type) {
                            HOT_FRAGMENT_MONTHLY -> {
                                monthlyRankLiveData.observe(requireActivity()) {
                                    if (it.dataState==DataState.STATE_ERROR) toast("获取月排行失败")
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                            }
                            HOT_FRAGMENT_WEEKLY -> {
                                weeklyRankLiveData.observe(requireActivity()) {
                                    if (it.dataState==DataState.STATE_ERROR) toast("获取周排行失败")
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                            }
                            HOT_FRAGMENT_HISTORICAL -> {
                                historicalRankLiveData.observe(requireActivity()) {
                                    if (it.dataState==DataState.STATE_ERROR) toast("获取总排行失败")
                                    if (it.dataState == DataState.STATE_SUCCESS) {
                                        adapter.submitList(swapBean(it.itemList!!))
                                    }
                                }
                            }
                            else -> ""
                        }
                        refreshLayout.setOnRefreshListener{
                            getRankList(type.substring(14,type.length))
                        }
                        getRankList(type.substring(14,type.length))
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