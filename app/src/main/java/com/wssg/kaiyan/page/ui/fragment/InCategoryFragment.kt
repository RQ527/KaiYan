package com.wssg.kaiyan.page.ui.fragment

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.CategorySquareData
import com.wssg.kaiyan.page.Constant
import com.wssg.kaiyan.page.adapter.CategoryRecRvAdapter
import com.wssg.kaiyan.page.adapter.CategorySquareRvAdapter
import com.wssg.kaiyan.page.adapter.PagingFooterAdapter
import com.wssg.kaiyan.page.ui.activity.PhotoAndVideoActivity
import com.wssg.kaiyan.page.ui.activity.PlayVideoActivity
import com.wssg.kaiyan.page.viewmodel.InCategoryViewModel
import com.wssg.kaiyan.utils.toast
import com.wssg.kaiyan.widget.view.MyRecyclerView
import com.wssg.kaiyan.widget.view.MyRefreshView
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/26
 * @Description:
 */
class InCategoryFragment : BaseVmFragment<InCategoryViewModel>() {
    private var type: String = ""
    private val recyclerView by R.id.myRv_inCategoryFrag.view<MyRecyclerView>()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        type = arguments?.getString("type")!!
        return inflater.inflate(R.layout.fragment_incategory, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (type) {
            Constant.CATEGORY_ACTIVITY_RECOMMEND -> {
                val categoryBean =
                    requireArguments().getSerializable("categoryBean")!! as CategoryBean
                val adapter = CategoryRecRvAdapter()
                recyclerView.adapter =
                    adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
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
                bindAdapterToPaging(
                    viewModel.getCategoryRecommendData(categoryBean.tagId),
                    adapter
                )
                adapter.addLoadStateListener {
                    if (it.refresh is LoadState.Error) toast("分类推荐加载失败")
                }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            Constant.CATEGORY_ACTIVITY_SQUARE -> {
                val categoryBean =
                    requireArguments().getSerializable("categoryBean")!! as CategoryBean
                val adapter = CategorySquareRvAdapter()
                recyclerView.adapter =
                    adapter.withLoadStateFooter(PagingFooterAdapter { adapter.retry() })
                bindAdapterToPaging(
                    viewModel.getCategorySquareData(categoryBean.tagId),
                    adapter
                )
                adapter.addLoadStateListener {
                    if (it.refresh is LoadState.Error) toast("分类广场加载失败")
                }
                adapter.setOnClickedListener { categorySquareData: CategorySquareData, view: View ->
                    val source = PhotoAndVideoActivity.Companion.Source(
                        categorySquareData.kind,
                        null,
                        null
                    )
                    if (categorySquareData.kind == "ugc_video") source.playUrl =
                        categorySquareData.playUrl
                    else source.picUrls = categorySquareData.picUrls
                    PhotoAndVideoActivity.startActivity(
                        requireContext(), source,
                        ActivityOptions.makeSceneTransitionAnimation(
                            requireActivity(),
                            view,
                            "picture"
                        )
                            .toBundle()
                    )
                }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

}