package com.wssg.kaiyan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wssg.kaiyan.bean.HomeVideoBean
import com.wssg.kaiyan.bean.VideoInfoData
import com.wssg.kaiyan.repo.KaiYanRepo
import com.wssg.kaiyan.bean.VideoRelatedBean
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HomeFragmentViewModel() : BaseViewModel() {
    fun getHomePagingData(): Flow<PagingData<VideoInfoData>> =
        KaiYanRepo.getHomePagingData().cachedIn(viewModelScope)
}