package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wssg.kaiyan.model.bean.CategoryRecBean
import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/19
 * @Description:
 */
class CategoryActivityViewModel : BaseViewModel() {
    fun getRecommendData(id:Int)=
        KaiYanRepo.getCategoryRecPagingData(id).cachedIn(viewModelScope)
}