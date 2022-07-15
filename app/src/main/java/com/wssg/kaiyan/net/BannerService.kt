package com.wssg.kaiyan.net

import com.wssg.kaiyan.bean.Banner
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
interface BannerService {
    @GET("/api/v5/index/tab/allRec")
    suspend fun getBanners():BaseResp<Banner>

}