package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class InnerFindFragmentViewModel : BaseViewModel() {
    private val _allCategories = MutableLiveData<BaseResp<List<CategoryBean>>>()
    val allCategories: LiveData<BaseResp<List<CategoryBean>>>
        get() = _allCategories

    fun getCommunityData() =
        KaiYanRepo.getCommunityPagingData().cachedIn(viewModelScope)

    fun getFollowData() =
        KaiYanRepo.getFollowPagingData().cachedIn(viewModelScope)


    fun getAllCategories() = launch(KaiYanRepo.getCategories()) {
        _allCategories.value = it
    }

    fun getCategoryRecommendData(id: Int) =
        KaiYanRepo.getCategoryRecPagingData(id).cachedIn(viewModelScope)

    fun getCategorySquareData(id: Int) =
        KaiYanRepo.getCategorySquarePagingData(id).cachedIn(viewModelScope)
}