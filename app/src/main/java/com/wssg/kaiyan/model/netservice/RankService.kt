package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.RankBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/22
 * @Description:
 */
interface RankService {
    @GET("/api/v4/rankList/videos")
    suspend fun getRankList(@Query("strategy") strategy: String): BaseResp<List<RankBean>>
}