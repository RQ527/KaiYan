package com.wssg.kaiyan.utils

import android.widget.Toast
import com.wssg.lib.base.base.BaseApp

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/25
 * @Description:
 */
fun toast(s: CharSequence) {
    Toast.makeText(BaseApp.appContext, s, Toast.LENGTH_SHORT).show()
}