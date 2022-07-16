package com.wssg.kaiyan.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
data class HomeDetailBean(
    val title: String,
    val author: String,
    val kind: String,
    val headUrl: String,
    val coverUrl: String,
    val duration: Int,
    val videoDetailBean: VideoDetailBean
) {
}