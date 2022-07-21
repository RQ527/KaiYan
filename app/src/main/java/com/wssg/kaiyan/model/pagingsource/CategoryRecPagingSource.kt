package com.wssg.kaiyan.model.pagingsource

import android.util.Log
import com.wssg.kaiyan.model.bean.CategoryRecBean
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.netservice.CategoriesService
import com.wssg.kaiyan.model.netservice.CommunityService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception
import kotlin.math.log

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/20
 * @Description:
 */
class CategoryRecPagingSource(val id: Int) : BasePagingSource<VideoInfoData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoInfoData> {
        return try {
            val page = params.key ?: 1
            val homeData = if (nextParams == "")
                RetrofitClient.getService(CategoriesService::class.java)
                    .getCategoryDetailRecommend(id.toString(), "", "", "")
            else RetrofitClient.getService(CategoriesService::class.java)
                .getCategoryDetailRecommend(
                    id.toString(),
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1],
                    nextParams.split("&")[2].split("=")[1]
                )
            nextParams = if (homeData.nextPageUrl == null) "" else homeData.nextPageUrl!!.substring(
                46,
                homeData.nextPageUrl!!.length
            )
            val realData = mutableListOf<VideoInfoData>()
            for (data in homeData.itemList!!) {
                if (data.data.content.type == "video") {
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
                                author?.name ?: "",
                                author?.description ?:"",
                                author?.icon?:"",
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
            Log.d("RQ", "load: $e")
            LoadResult.Error(e)
        }
    }
}