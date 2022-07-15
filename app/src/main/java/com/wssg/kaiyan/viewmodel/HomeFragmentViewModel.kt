package com.wssg.kaiyan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wssg.kaiyan.repo.KaiYanRepo
import com.wssg.kaiyan.bean.Banner
import com.wssg.kaiyan.bean.VideoInfo
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HomeFragmentViewModel(): BaseViewModel() {
    private val _bannersLiveData = MutableLiveData<BaseResp<Banner>>()
    val bannersLiveData:LiveData<BaseResp<Banner>>
    get() = _bannersLiveData

    val testLiveData = MutableLiveData<BaseResp<VideoInfo>>()
    fun getBanners() =
        launch(KaiYanRepo.getBanners()){
            _bannersLiveData.value = it
        }
    fun getVideoInfo(id:String) =
        launch(KaiYanRepo.getVideoInfo(id)){
            testLiveData.value = it
        }
}