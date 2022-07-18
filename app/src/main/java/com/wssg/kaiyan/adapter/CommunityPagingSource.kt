package com.wssg.kaiyan.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.bean.CommunityData
import com.wssg.kaiyan.bean.VideoInfoData
import com.wssg.kaiyan.net.CommunityService
import com.wssg.kaiyan.net.HomeService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
class CommunityPagingSource : PagingSource<Int, CommunityData>() {
    private var nextParams = ""
    override fun getRefreshKey(state: PagingState<Int, CommunityData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityData> {
        return try {
            val page = params.key ?: 1
            Log.d("RQ", "load: $page")
            val homeData = if (nextParams == "")
                RetrofitClient.getService(CommunityService::class.java).getCommunityData("", "")
            else RetrofitClient.getService(CommunityService::class.java)
                .getCommunityData(
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1]
                )
            nextParams = if (homeData.nextPageUrl == null) "" else homeData.nextPageUrl!!.substring(
                53,
                homeData.nextPageUrl!!.length
            )
            val realData = mutableListOf<CommunityData>()
            for (data in homeData.itemList!!) {
                if (data.type == "communityColumnsCard") {
                    data.data.content.data.run {
                        realData.add(
                            CommunityData(
                                cover.feed,
                                description,
                                owner.avatar,
                                owner.nickname,
                                consumption.collectionCount,
                                if (resourceType == "ugc_picture") urls else null,
                                resourceType,
                                if (resourceType == "ugc_video") playUrl else null
                            )
                        )
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
}