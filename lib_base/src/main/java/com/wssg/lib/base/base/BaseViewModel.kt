package com.wssg.lib.base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
open class BaseViewModel() : ViewModel() {

    fun<T> launch(
        result: Flow<BaseResp<T>>,
        handle:(value: BaseResp<T>)->Unit
    ){
        viewModelScope.launch{
            result.collect {
                handle(it)
            }
        }
    }
}