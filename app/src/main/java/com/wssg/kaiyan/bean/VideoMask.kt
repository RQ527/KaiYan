package com.wssg.kaiyan.bean

import java.io.Serializable

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
data class VideoMask(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: Any,
    val total: Int
):Serializable {
    data class Item(
        val adIndex: Int,
        val `data`: Data,
        val id: Int,
        val tag: Any,
        val trackingData: Any,
        val type: String
    ) :Serializable{
        data class Data(
            val actionUrl: Any,
            val adTrack: Any,
            val cover: Any,
            val createTime: Long,
            val dataType: String,
            val font: String,
            val hot: Boolean,
            val id: Long,
            val imageUrl: String,
            val likeCount: Int,
            val liked: Boolean,
            val message: String,
            val parentReply: Any,
            val parentReplyId: Int,
            val recommendLevel: String,
            val replyStatus: String,
            val rootReplyId: Long,
            val sequence: Int,
            val showConversationButton: Boolean,
            val showParentReply: Boolean,
            val sid: String,
            val text: String,
            val type: String,
            val ugcVideoId: Any,
            val ugcVideoUrl: Any,
            val user: User,
            val userBlocked: Boolean,
            val userType: Any,
            val videoId: Int,
            val videoTitle: String
        ):Serializable {
            data class User(
                val actionUrl: String,
                val area: Any,
                val avatar: String,
                val birthday: Any,
                val city: Any,
                val country: Any,
                val cover: String,
                val description: String,
                val expert: Boolean,
                val followed: Boolean,
                val gender: Any,
                val ifPgc: Boolean,
                val job: Any,
                val library: String,
                val limitVideoOpen: Boolean,
                val nickname: String,
                val registDate: Long,
                val releaseDate: Long,
                val uid: Int,
                val university: Any,
                val userType: String
            ):Serializable
        }
    }
}