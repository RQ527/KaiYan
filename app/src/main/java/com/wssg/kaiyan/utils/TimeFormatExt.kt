package com.wssg.kaiyan.utils

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
fun Int.durationToStr(): String {
    val seconds = this % 60
    val minute = this / 60
    return if (seconds<10) {
        if (minute<10) "0$minute:0$seconds"
        else "$minute:0$seconds"
    }else{
        if (minute<0) "0$minute:$seconds"
        else "$minute:$seconds"
    }
}