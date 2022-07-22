package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.VideoRelatedBean
import com.wssg.kaiyan.model.bean.VideoMaskBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.POST
import retrofit2.http.Query
import java.lang.reflect.Type

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

    @POST("/api/v2/replies/video?lastId=&videoId=2896&num=&type=video")
    suspend fun getVideoMask(
        @Query("videoId") videoId: String,
        @Query("lastId") lastId: String,
        @Query("num") num: String,
        @Query("type") type: String = "video"
    ): BaseResp<List<VideoMaskBean>>
}