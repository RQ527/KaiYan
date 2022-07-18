package com.wssg.kaiyan.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wssg.kaiyan.model.pagingsource.CommunityPagingSource
import com.wssg.kaiyan.model.pagingsource.HomePagingSource
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.pagingsource.FollowPagingSource
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
    fun getHomePagingData() = getPagingData { HomePagingSource() }

    fun getCommunityPagingData() = getPagingData { CommunityPagingSource() }

    fun getFollowPagingData() = getPagingData { FollowPagingSource() }
}