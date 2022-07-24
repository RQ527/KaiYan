package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.SearchResultBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
interface SearchService {
    @GET("/api/v3/search")
    suspend fun searchByKey(
        @Query("query") query: String,
        @Query("start") start: String,
        @Query("num") num: String
    ): BaseResp<List<SearchResultBean>>

    @GET("/api/v3/queries/hot")
    suspend fun getHotKeys(): List<String>
}