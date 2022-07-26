package com.wssg.lib.base.utils

import android.content.Context
import android.content.SharedPreferences
import com.wssg.lib.base.base.BaseApp

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
fun saveValue(key: String, value: String) {
    val sp: SharedPreferences = BaseApp.appContext
        .getSharedPreferences("kaiyan", Context.MODE_PRIVATE)
    val edit = sp.edit()
    edit.putString(key, value)
    edit.apply()
}

fun achieveValue(key: String): String? {
    val sp: SharedPreferences = BaseApp.appContext
        .getSharedPreferences("kaiyan", Context.MODE_PRIVATE)
    return sp.getString(key, "")
}
