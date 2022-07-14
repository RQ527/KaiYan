package com.wssg.kaiyan

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
interface ApiService {
    @POST("/user/login")
    suspend fun login(@Query("username") name: String, @Query("password") password: String): BaseResp<LoginRes>

    @POST("/user/lg/userinfo/json")
    suspend fun getInfo(): BaseResp<TestBean>
}