package com.wssg.kaiyan.net

import com.wssg.kaiyan.bean.VideoInfo
import com.wssg.kaiyan.bean.VideoMask
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
    @POST("api/v4/video/related")
    suspend fun getVideoInfo(@Query("id") id: String): BaseResp<VideoInfo>

    @POST("api/v2/replies/video")
    suspend fun getVideoMask(@Query("videoId") videoId: String): BaseResp<VideoMask>
}