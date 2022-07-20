package com.wssg.kaiyan.model.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.model.bean.CommunityBean
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.netservice.CommunityService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
class CommunityPagingSource : BasePagingSource<CommunityData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityData> {
        return try {
            val page = params.key ?: 1
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