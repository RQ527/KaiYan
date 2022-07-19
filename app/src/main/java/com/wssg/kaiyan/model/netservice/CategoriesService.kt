package com.wssg.kaiyan.model.netservice

import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.lib.base.base.BaseResp
import retrofit2.http.GET

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/19
 * @Description:
 */
interface CategoriesService {
    @GET("/api/v4/categories/all")
    suspend fun getAllCategories():BaseResp<CategoryBean>
}