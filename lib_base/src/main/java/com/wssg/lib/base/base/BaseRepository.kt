package com.wssg.lib.base.base

import com.wssg.lib.base.net.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
open class BaseRepository {

    fun <T : Any> executeResp(
        block: suspend () -> BaseResp<T>
    ): Flow<BaseResp<T>> {
        return flow {
            var resp = BaseResp<T>()
            resp.dataState = DataState.STATE_LOADING
            emit(resp)
            try {
                resp = block.invoke()
                if (resp.errorCode != 0) {
                    resp.dataState = DataState.STATE_FAILED
                } else if (resp.data == null) {
                    resp.dataState = DataState.STATE_EMPTY
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
}