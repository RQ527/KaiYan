package com.wssg.kaiyan.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
data class VideoDetailBean(
    val id: Int,
    val playUrl: String,
    val coverUrl: String,
    val title: String,
    val kind: String,
    val description: String,
    val consumption: Consumption,
    val author: String,
    val authorDescription: String,
    val authorHeader: String,
    val duration:Int,
    val releaseDate:Long,
    val bannerData:List<VideoDetailBean>?
) {
    data class Consumption(
        val collectionCount: Int,
        val shareCount: Int,
        val replyCount: Int,
    )
}