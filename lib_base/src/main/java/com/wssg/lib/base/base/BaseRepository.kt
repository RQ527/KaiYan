package com.wssg.lib.base.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.wssg.lib.base.net.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description: 抽离Repository公共部分
 */
abstract class BaseRepository {

    protected fun <T : Any> executeResp(
        block: suspend () -> BaseResp<T>
    ): Flow<BaseResp<T>> {
        return flow {
            var resp = BaseResp<T>()
            resp.dataState = DataState.STATE_LOADING
            emit(resp)
            try {
                resp = block.invoke()
                if (resp.itemList == null) {
                    resp.dataState = DataState.STATE_FAILED
                } else {
                    resp.dataState = DataState.STATE_SUCCESS
                }
                emit(resp)
            } catch (e: Exception) {
                resp.dataState = DataState.STATE_ERROR
                resp.error = e
                emit(resp)
            } finally {
                resp.dataState = DataState.STATE_COMPLETED
                emit(resp)
            }
        }
    }

    fun <T : Any> getPagingData(pagingSourceFactory: ()->PagingSource<Int, T>): Flow<PagingData<T>>{
        return Pager(config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10
        ),
            pagingSourceFactory = { pagingSourceFactory() }).flow
    }
}