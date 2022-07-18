package com.wssg.kaiyan.model.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/18
 * @Description:
 */
data class CommunityData(
    val coverUrl:String,
    val title:String,
    val headerUrl:String,
    val nickname:String,
    val agreeCount:Int,
    val picUrls:List<String>?,
    val kind:String,
    val playUrl:String?
)