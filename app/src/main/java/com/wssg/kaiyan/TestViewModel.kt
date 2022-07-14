package com.wssg.kaiyan

import androidx.lifecycle.MutableLiveData
import com.wssg.lib.base.base.BaseResp
import com.wssg.lib.base.base.BaseViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class TestViewModel(): BaseViewModel() {
    val loginLiveData = MutableLiveData<BaseResp<LoginRes>>()
    val infoLiveData = MutableLiveData<BaseResp<TestBean>>()
    fun login(username:String,password:String)=launch(TestRepo.login(username, password)){
        loginLiveData.value = it
    }
    fun getInfo()=launch(TestRepo.getInfo()){
        infoLiveData.value = it
    }
}