package com.wssg.kaiyan.utils

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/25
 * @Description:
 */
/**
 * dp转px
 * @return
 */
fun Int.dpToPx(): Int {
    return (Resources.getSystem().displayMetrics.density * this + 0.5f).roundToInt()
}

/**
 * sp转px
 * @return
 */
fun Int.spToPx(): Int {
    return (Resources.getSystem().displayMetrics.scaledDensity * this + 0.5f).roundToInt()
}

/**
 * px转dp
 * @return
 */
fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()
}

/**
 * px转sp
 * @return
 */
fun Int.pxToSp(): Int {
    return (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).roundToInt()
}