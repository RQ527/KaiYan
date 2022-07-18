package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.FollowBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
interface FollowService {
    @GET("/api/v6/community/tab/follow")
    suspend fun getFollowData(
        @Query("start") start: String,
        @Query("num") num: String,
        @Query("newest") newest: String
    ):BaseResp<FollowBean>
}