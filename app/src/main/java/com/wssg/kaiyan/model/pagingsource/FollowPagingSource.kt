package com.wssg.kaiyan.model.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.netservice.CommunityService
import com.wssg.kaiyan.model.netservice.FollowService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
class FollowPagingSource : BasePagingSource<VideoInfoData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoInfoData> {
        return try {
            val page = params.key ?: 1
            val homeData = if (nextParams == "")
                RetrofitClient.getService(FollowService::class.java).getFollowData("", "", "")
            else RetrofitClient.getService(FollowService::class.java)
                .getFollowData(
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1],
                    nextParams.split("&")[2].split("=")[1]
                )
            nextParams = if (homeData.nextPageUrl == null) "" else homeData.nextPageUrl!!.substring(
                56,
                homeData.nextPageUrl!!.length
            )
            val realData = mutableListOf<VideoInfoData>()
            for (data in homeData.itemList!!) {
                if (data.type == "autoPlayFollowCard") {
                    data.data.content.data.run {
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
            }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (nextParams != "") page + 1 else null
            LoadResult.Page(realData, prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("RQ", "load: Exception$e")
            LoadResult.Error(e)
        }
    }
}