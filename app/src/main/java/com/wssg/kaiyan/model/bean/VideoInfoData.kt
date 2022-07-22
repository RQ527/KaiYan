package com.wssg.kaiyan.model.bean

import java.io.Serializable

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/16
 * @Description:
 */
data class VideoInfoData(
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
    var bannerData:List<VideoInfoData>?
):Serializable {
    data class Consumption(
        val collectionCount: Int,
        val shareCount: Int,
        val replyCount: Int,
    ):Serializable
}