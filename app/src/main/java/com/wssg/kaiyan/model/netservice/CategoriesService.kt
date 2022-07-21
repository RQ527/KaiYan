package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.CategoryRecBean
import com.wssg.kaiyan.model.bean.CategorySquareBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/19
 * @Description:
 */
interface CategoriesService {
    @GET("/api/v4/categories")
    suspend fun getCategories(): List<CategoryBean>

    @GET("/api/v1/tag/videos")
    suspend fun getCategoryDetailRecommend(
        @Query("id") id: String,
        @Query("start") start: String,
        @Query("num") num: String,
        @Query("strategy") strategy: String
    ): BaseResp<List<CategoryRecBean>>

    @GET("/api/v6/tag/dynamics?id=12")
    suspend fun getCategoryDetailSquare(
        @Query("id") id: String,
        @Query("start") start: String,
        @Query("num") num: String
    ): BaseResp<List<CategorySquareBean>>
}