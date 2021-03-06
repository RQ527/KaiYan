package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class SearchActivityViewModel : BaseViewModel() {
    private val _hotKeysLiveData = MutableLiveData<BaseResp<List<String>>>()
    val hotKeysLiveData: LiveData<BaseResp<List<String>>>
        get() = _hotKeysLiveData

    fun getSearchResultPagingData(query: String) =
        KaiYanRepo.getSearchResultPagingData(query)

    fun getHotKeys() = launch(KaiYanRepo.getHotKeys()) {
        _hotKeysLiveData.value = it
    }
}