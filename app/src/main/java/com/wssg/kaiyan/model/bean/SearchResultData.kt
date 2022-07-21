package com.wssg.kaiyan.model.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/21
 * @Description:
 */
data class SearchResultData(
    val id:Long,
    val type: String,
    val imageUrl:String?,
    val title:String?,
    val authorDescription: String?,
    val videoInfoData: VideoInfoData?
    )