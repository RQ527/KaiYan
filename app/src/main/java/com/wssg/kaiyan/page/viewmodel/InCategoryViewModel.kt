package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/26
 * @Description:
 */
class InCategoryViewModel:BaseViewModel() {
    fun getCategoryRecommendData(id: Int) =
        KaiYanRepo.getCategoryRecPagingData(id).cachedIn(viewModelScope)

    fun getCategorySquareData(id: Int) =
        KaiYanRepo.getCategorySquarePagingData(id).cachedIn(viewModelScope)

}