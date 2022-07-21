package com.wssg.kaiyan.page.viewmodel

import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
class SearchActivityViewModel : BaseViewModel() {
    fun getSearchResultPagingData(query: String) =
        KaiYanRepo.getSearchResultPagingData(query)
}