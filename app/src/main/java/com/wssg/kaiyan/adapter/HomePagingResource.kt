package com.wssg.kaiyan.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wssg.kaiyan.bean.VideoDetailBean
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
/*class HomePagingResource():PagingSource<Int,VideoDetailBean>() {
    override fun getRefreshKey(state: PagingState<Int, VideoDetailBean>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoDetailBean> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            if (page == 1) {
                val topArticles = RetrofitClient.getService(HomeService::class.java).getHomeData().itemList!!
                for (d in topArticles) {
                    val finalHomePaging =
                        FinalHomePaging(
                            "置顶    ${if (d.author.isEmpty()) d.shareUser else d.author}",
                            d.niceDate,
                            d.title,
                            "${d.superChapterName}/${d.chapterName}",
                            d.id.toString(),
                            d.link,
                            id++
                        )
                    articles.add(finalHomePaging)
                }
            }
            val homeArtists =
                WanAndroidNetwork.getHomeArticles((page - 1).toString(), pageSize.toString()).data.datas
            for (d in homeArtists){
                val finalHomePaging = FinalHomePaging(
                    if (d.author.isEmpty()) d.shareUser else d.author,
                    d.niceDate,
                    d.title,
                    "${d.superChapterName}/${d.chapterName}",
                    d.id.toString(),
                    d.link,
                    id++
                )
                articles.add(finalHomePaging)
            }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (articles.isNotEmpty()) page + 1 else null
            LoadResult.Page(articles, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}*/
