package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.VideoRelatedBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
interface PlayVideoService {
    @POST("/api/v4/video/related")
    suspend fun getVideoInfo(@Query("id") id: String): BaseResp<List<VideoRelatedBean>>

}