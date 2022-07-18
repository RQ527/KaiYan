package com.wssg.kaiyan.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.bean.HomeVideoBean
import com.wssg.kaiyan.bean.VideoInfoData
import com.wssg.kaiyan.net.HomeService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
class HomePagingSource() : PagingSource<Int, VideoInfoData>() {
    var nextParams: String = ""
    override fun getRefreshKey(state: PagingState<Int, VideoInfoData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoInfoData> {
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
            nextParams = if (homeData.nextPageUrl == null) "" else "?${
                homeData.nextPageUrl!!.substring(
                    52,
                    homeData.nextPageUrl!!.length
                )
            }"
            val bannerData = mutableListOf<VideoInfoData>()
            val realData = mutableListOf<VideoInfoData>()
            for (data in homeData.itemList!!) {
                when (data.type) {
                    "squareCardCollection" -> {
                        for (d in data.data.itemList) {
                            bannerData.add(swapBannerBean(d.data.content.data))
                        }
                        realData.add(
                            VideoInfoData(
                                -1, "", "", "", "", "",
                                VideoInfoData.Consumption(-1, -1, -1),
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
            LoadResult.Page(realData, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun swapContentBean(data: HomeVideoBean.Data.Content): VideoInfoData =
        data.data.run {
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
        }

    private fun swapHomeDataBean(data: HomeVideoBean.Data) =
        data.run {
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
        }

    private fun swapBannerBean(data: HomeVideoBean.Data.Item.Data.Content.Data) =
        data.run {
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
        }
}
