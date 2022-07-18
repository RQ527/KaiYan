package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.HomeVideoBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
interface HomeService {
    @GET("/api/v5/index/tab/allRec")
    suspend fun getHomeData(
        @Query("page") page: String,
        @Query("isTag") isTag: String,
        @Query("adIndex") adIndex: String
    ): BaseResp<HomeVideoBean>

}