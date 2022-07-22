package com.wssg.kaiyan.page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.model.bean.VideoRelatedBean
import com.wssg.kaiyan.model.repo.KaiYanRepo
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/19
 * @Description:
 */
class PlayVideoActivityViewModel : BaseViewModel() {
    private val _videoRelatedData = MutableLiveData<BaseResp<List<VideoRelatedBean>>>()
    val videoRelatedData: LiveData<BaseResp<List<VideoRelatedBean>>>
        get() = _videoRelatedData

    fun getVideoRelatedData(id: Int) =
        launch(KaiYanRepo.getVideoInfoData(id)) {
            _videoRelatedData.value = it
        }
}