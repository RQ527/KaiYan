package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.CommunityData
import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class FindFragmentViewModel : BaseViewModel() {
    private val _allCategories = MutableLiveData<BaseResp<CategoryBean>>()
    val allCategories
        get() = _allCategories

    fun getCommunityData() =
        KaiYanRepo.getCommunityPagingData().cachedIn(viewModelScope)

    fun getFollowData() =
        KaiYanRepo.getFollowPagingData().cachedIn(viewModelScope)

    fun getAllCategories() =
        launch(KaiYanRepo.getAllCategories()) {
            _allCategories.value = it
        }
}