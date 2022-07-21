package com.wssg.kaiyan.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.netservice.CategoriesService
import com.wssg.kaiyan.model.pagingsource.*
import com.wssg.lib.base.base.BaseRepository
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.net.RetrofitClient
import kotlinx.coroutines.flow.Flow

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
object KaiYanRepo : BaseRepository() {
    fun getHomePagingData() = getPagingData { HomePagingSource() }

    fun getCommunityPagingData() = getPagingData { CommunityPagingSource() }

    fun getFollowPagingData() = getPagingData { FollowPagingSource() }

    fun getCategories() =
        executeResp {
            val data = RetrofitClient.getService(CategoriesService::class.java).getCategories()
            BaseResp(itemList = data)//没办法，接口返回的外层字段突然改了，导致封装的外层也得改，只能包装一下了
        }

    fun getCategoryRecPagingData(id: Int) =
        getPagingData { CategoryRecPagingSource(id) }

    fun getCategorySquarePagingData(id: Int) =
        getPagingData { CategorySquarePagingSource(id) }
}