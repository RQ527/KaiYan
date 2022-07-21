package com.wssg.kaiyan.model.pagingsource

import android.util.Log
import com.wssg.kaiyan.model.bean.CategorySquareData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.netservice.CategoriesService
import com.wssg.lib.base.net.RetrofitClient
import java.lang.Exception

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/20
 * @Description:
 */
class CategorySquarePagingSource(val id: Int) : BasePagingSource<CategorySquareData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategorySquareData> {
        return try {
            val page = params.key ?: 1
            val homeData = if (nextParams == "")
                RetrofitClient.getService(CategoriesService::class.java)
                    .getCategoryDetailSquare(id.toString(), "", "")
            else RetrofitClient.getService(CategoriesService::class.java)
                .getCategoryDetailSquare(
                    id.toString(),
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1],
                )
            nextParams = if (homeData.nextPageUrl == null) "" else homeData.nextPageUrl!!.substring(
                48,
                homeData.nextPageUrl!!.length
            )
            val realData = mutableListOf<CategorySquareData>()
            for (data in homeData.itemList!!) {
                data.data.content.data.run {
                    realData.add(
                        CategorySquareData(
                            cover.feed,
                            description,
                            owner?.avatar ?: author.icon,
                            owner?.nickname ?: author.name,
                            resourceType,
                            releaseTime,
                            CategorySquareData.Consumption(
                                consumption.collectionCount,
                                consumption.shareCount,
                                consumption.replyCount
                            ),
                            if (resourceType!="ugc_video") urls else null,
                            if (resourceType=="ugc_video") playUrl else null
                        )
                    )
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