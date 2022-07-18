package com.wssg.kaiyan.net

import com.wssg.kaiyan.bean.CommunityBean
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
interface CommunityService {
    @GET("/api/v7/community/tab/rec")
    suspend fun getCommunityData(
        @Query("startScore") page: String,
        @Query("pageCount") isTag: String,
    ): BaseResp<CommunityBean>
}