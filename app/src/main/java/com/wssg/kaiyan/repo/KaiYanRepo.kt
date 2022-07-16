package com.wssg.kaiyan.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wssg.kaiyan.adapter.HomePagingResource
import com.wssg.kaiyan.bean.VideoDetailBean
import com.wssg.kaiyan.net.HomeService
import com.wssg.kaiyan.net.PlayVideoService
import com.wssg.lib.base.net.RetrofitClient
import com.wssg.lib.base.base.BaseRepository
import kotlinx.coroutines.flow.Flow

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
object KaiYanRepo : BaseRepository() {
    fun getHomeData() =
        executeResp { RetrofitClient.getService(HomeService::class.java).getHomeData("0","false","0") }

    fun getVideoInfo(id: String) =
        executeResp { RetrofitClient.getService(PlayVideoService::class.java).getVideoInfo(id) }

    fun getHomePagingData(): Flow<PagingData<VideoDetailBean>> {
        return Pager(config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 1
        ),
            pagingSourceFactory = { HomePagingResource() }).flow
    }
}