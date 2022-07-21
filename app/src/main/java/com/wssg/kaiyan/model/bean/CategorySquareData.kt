package com.wssg.kaiyan.model.bean

import java.io.Serializable

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
data class CategorySquareData(
    val coverUrl: String,
    val description: String,
    val headerUrl: String,
    val nickname: String,
    val kind: String,
    val releaseDate: Long,
    val consumption: Consumption,
    val picUrls: List<String>?,
    val playUrl: String?
) {
    data class Consumption(
        val collectionCount: Int,
        val shareCount: Int,
        val replyCount: Int,
    )
}