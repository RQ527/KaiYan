package com.wssg.kaiyan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.bean.CommunityData
import com.wssg.kaiyan.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class FindFragmentViewModel : BaseViewModel() {
    private val _communityLiveData = MutableLiveData<CommunityData>()
    val communityLiveData
        get() = _communityLiveData

    fun getCommunityData() =
        KaiYanRepo.getCommunityPagingData().cachedIn(viewModelScope)
}