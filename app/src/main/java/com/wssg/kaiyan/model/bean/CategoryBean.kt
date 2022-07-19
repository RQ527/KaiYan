package com.wssg.kaiyan.model.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/19
 * @Description:
 */
data class CategoryBean(
    val adIndex: Int,
    val `data`: Data,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) {
    data class Data(
        val actionUrl: String,
        val adTrack: Any,
        val dataType: String,
        val description: Any,
        val id: Int,
        val image: String,
        val shade: Boolean,
        val title: String
    )
}
