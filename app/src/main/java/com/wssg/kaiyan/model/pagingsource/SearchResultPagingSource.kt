package com.wssg.kaiyan.model.pagingsource

import android.util.Log
import com.wssg.kaiyan.model.bean.SearchResultData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.netservice.SearchService
import com.wssg.lib.base.net.RetrofitClient

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class SearchResultPagingSource(private val query: String) : BasePagingSource<SearchResultData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultData> {
        return try {
            val page = params.key ?: 1
            val homeData = if (nextParams == "")
                RetrofitClient.getService(SearchService::class.java)
                    .searchByKey(query, "", "")
            else RetrofitClient.getService(SearchService::class.java)
                .searchByKey(
                    query,
                    nextParams.split("&")[0].split("=")[1],
                    nextParams.split("&")[1].split("=")[1],
                )
            nextParams = if (homeData.nextPageUrl == null) "" else homeData.nextPageUrl!!.substring(
                42,
                homeData.nextPageUrl!!.length
            )
            val realData = mutableListOf<SearchResultData>()
            for (data in homeData.itemList!!) {
                data.type.run {
                        when (this) {
                            "briefCard" -> {
                                realData.add(data.data.run {
                                    SearchResultData(
                                        id,
                                        follow.itemType,
                                        icon,
                                        title,
                                        description,
                                        null
                                    )
                                })
                            }
                            "followCard" -> {
                                realData.add(data.data.content.data.run {
                                    SearchResultData(
                                        id.toLong(),
                                        "video",
                                        null,
                                        null,
                                        null,
                                        VideoInfoData(
                                            id, playUrl, cover.feed, title, category, description,
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
                                })
                            }
                            else -> {}
                        }
                }
            }
            println()
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (nextParams != "") page + 1 else null
            LoadResult.Page(realData, prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("RQ", "load: $e")
            LoadResult.Error(e)
        }
    }
}