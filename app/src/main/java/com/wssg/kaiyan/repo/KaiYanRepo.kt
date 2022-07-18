package com.wssg.kaiyan.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wssg.kaiyan.adapter.CommunityPagingSource
import com.wssg.kaiyan.adapter.HomePagingSource
import com.wssg.kaiyan.bean.CommunityData
import com.wssg.kaiyan.bean.VideoInfoData
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
    fun getHomePagingData(): Flow<PagingData<VideoInfoData>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
        ),
            pagingSourceFactory = { HomePagingSource() }).flow

    fun getCommunityPagingData(): Flow<PagingData<CommunityData>> =
        Pager(config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2
        ),
            pagingSourceFactory = { CommunityPagingSource() }).flow

}