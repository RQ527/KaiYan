package com.wssg.kaiyan.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.adapter.HomeRvAdapter
import com.wssg.kaiyan.bean.HomeBean
import com.wssg.kaiyan.bean.VideoDetailBean
import com.wssg.kaiyan.ui.activity.PlayVideoActivity
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import com.wssg.lib.base.net.DataState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HomeFragment : BaseVmFragment<HomeFragmentViewModel>() {
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_homeFrag)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var adapter: HomeRvAdapter
        viewModel.homeLiveData.observe(requireActivity()) {
            if (it.dataState == DataState.STATE_SUCCESS) {
                val bannerData = mutableListOf<VideoDetailBean>()
                val realData = mutableListOf<VideoDetailBean>()
                for (data in it.itemList!!) {
                    when (data.type) {
                        "squareCardCollection" -> {
                            for (d in data.data.itemList) {
                                bannerData.add(swapBannerBean(d.data.content.data))
                            }
                        }
                        "followCard" -> {
                            realData.add(swapContentBean(data.data.content))
                        }
                        "videoSmallCard" -> {
                            realData.add(swapHomeDataBean(data.data))
                        }
                    }
                }
                adapter = HomeRvAdapter(realData, bannerData)
                recyclerView.adapter = adapter
                adapter.setOnClickedListener(object : HomeRvAdapter.OnClickedListener {
                    override fun onClicked(detailBean: VideoDetailBean) {
                        Toast.makeText(requireContext(), "$detailBean", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        viewModel.getHomeData()
    }

    private fun swapContentBean(data: HomeBean.Data.Content): VideoDetailBean =
        data.data.run {
            VideoDetailBean(
                id,
                playUrl,
                cover.feed,
                title,
                category,
                description,
                VideoDetailBean.Consumption(
                    consumption.collectionCount,
                    consumption.shareCount,
                    consumption.replyCount
                ),
                author.name,
                author.description,
                author.icon,
                duration,
                releaseTime
            )
        }

    private fun swapHomeDataBean(data: HomeBean.Data) =
        data.run {
            VideoDetailBean(
                id,
                playUrl,
                cover.feed,
                title,
                category,
                description,
                VideoDetailBean.Consumption(
                    consumption.collectionCount,
                    consumption.shareCount,
                    consumption.replyCount
                ),
                author.name,
                author.description,
                author.icon,
                duration,
                releaseTime
            )
        }

    private fun swapBannerBean(data: HomeBean.Data.Item.Data.Content.Data) =
        data.run {
            VideoDetailBean(
                id,
                playUrl,
                cover.feed,
                title,
                category,
                description,
                VideoDetailBean.Consumption(
                    consumption.collectionCount,
                    consumption.shareCount,
                    consumption.replyCount
                ),
                author.name,
                author.description,
                author.icon,
                duration,
                releaseTime
            )
        }

}