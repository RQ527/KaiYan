package com.wssg.kaiyan.adapter

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.bean.HomeBean
import com.wssg.kaiyan.bean.VideoDetailBean
import com.wssg.kaiyan.net.HomeService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception
import kotlin.math.log

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
class HomePagingResource() : PagingSource<Int, VideoDetailBean>() {
    var nextParams: String = ""
    override fun getRefreshKey(state: PagingState<Int, VideoDetailBean>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoDetailBean> {
        return try {
            val page = params.key ?: 1
            val homeData = if (nextParams == "")
                RetrofitClient.getService(HomeService::class.java).getHomeData("0", "false", "0")
            else RetrofitClient.getService(HomeService::class.java)
                .getHomeData(
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1],
                    nextParams.split("&")[2].split("=")[1]
                )
            Log.d("RQ", "load: ${homeData.itemList}")
            nextParams = if (homeData.nextPageUrl == null) "" else "?${
                homeData.nextPageUrl!!.substring(
                    52,
                    homeData.nextPageUrl!!.length
                )
            }"
            Log.d("RQ", "load: $nextParams")
            val bannerData = mutableListOf<VideoDetailBean>()
            val realData = mutableListOf<VideoDetailBean>()
            for (data in homeData.itemList!!) {
                when (data.type) {
                    "squareCardCollection" -> {
                        for (d in data.data.itemList) {
                            bannerData.add(swapBannerBean(d.data.content.data))
                        }
                        realData.add(
                            VideoDetailBean(
                                -1, "", "", "", "", "",
                                VideoDetailBean.Consumption(-1, -1, -1),
                                "", "", "", -1, -1,
                                bannerData
                            )
                        )
                    }
                    "followCard" -> {
                        realData.add(swapContentBean(data.data.content))
                    }
                    "videoSmallCard" -> {
                        realData.add(swapHomeDataBean(data.data))
                    }
                }
            }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (nextParams != "") page + 1 else null
            Log.d("RQ", "load: $nextKey")
            LoadResult.Page(realData, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
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
                releaseTime,
                null
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
                releaseTime,
                null
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
                releaseTime,
                null
            )
        }
}
