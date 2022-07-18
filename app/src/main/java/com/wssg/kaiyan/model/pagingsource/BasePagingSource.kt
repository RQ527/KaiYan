package com.wssg.kaiyan.model.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description: 提取pagingSource的公共部分
 */
abstract class BasePagingSource<T : Any>:PagingSource<Int,T>() {
    protected var nextParams = ""
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

}