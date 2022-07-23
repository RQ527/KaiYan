package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.model.bean.CategoryBean
import com.wssg.kaiyan.model.bean.RankBean
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
class InnerFragmentViewModel : BaseViewModel() {
    private val _allCategoriesLiveData = MutableLiveData<BaseResp<List<CategoryBean>>>()
    val allCategoriesLiveData: LiveData<BaseResp<List<CategoryBean>>>
        get() = _allCategoriesLiveData

    private val _historicalRankLiveData = MutableLiveData<BaseResp<List<RankBean>>>()
    val historicalRankLiveData: LiveData<BaseResp<List<RankBean>>>
        get() = _historicalRankLiveData

    private val _monthlyRankLiveData = MutableLiveData<BaseResp<List<RankBean>>>()
    val monthlyRankLiveData: LiveData<BaseResp<List<RankBean>>>
        get() = _monthlyRankLiveData

    private val _weeklyRankLiveData = MutableLiveData<BaseResp<List<RankBean>>>()
    val weeklyRankLiveData: LiveData<BaseResp<List<RankBean>>>
        get() = _weeklyRankLiveData

    fun getCommunityData() =
        KaiYanRepo.getCommunityPagingData().cachedIn(viewModelScope)

    fun getFollowData() =
        KaiYanRepo.getFollowPagingData().cachedIn(viewModelScope)


    fun getAllCategories() = launch(KaiYanRepo.getCategories()) {
        _allCategoriesLiveData.value = it
    }

    fun getCategoryRecommendData(id: Int) =
        KaiYanRepo.getCategoryRecPagingData(id).cachedIn(viewModelScope)

    fun getCategorySquareData(id: Int) =
        KaiYanRepo.getCategorySquarePagingData(id).cachedIn(viewModelScope)

    fun getRankList(strategy: String) =
        launch(KaiYanRepo.getRankList(strategy)) {
            when (strategy) {
                "monthly" -> _monthlyRankLiveData.value = it
                "weekly" -> _weeklyRankLiveData.value = it
                "historical" -> _historicalRankLiveData.value = it
            }
        }
}