package com.wssg.kaiyan.model.bean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/20
 * @Description:
 */
data class CategorySquareBean(
    val adIndex: Int,
    val `data`: Data,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) {
    data class Data(
        val adTrack: Any,
        val content: Content,
        val dataType: String,
        val header: Header
    ) {
        data class Content(
            val adIndex: Int,
            val `data`: Data,
            val id: Int,
            val tag: Any,
            val trackingData: Any,
            val type: String
        ) {
            data class Data(
                val addWatermark: Boolean,
                val author: Author,
                val area: String,
                val checkStatus: String,
                val city: String,
                val collected: Boolean,
                val consumption: Consumption,
                val cover: Cover,
                val createTime: Long,
                val dataType: String,
                val description: String,
                val duration: Int,
                val height: Int,
                val id: Int,
                val ifMock: Boolean,
                val library: String,
                val owner: Owner,
                val playUrl: String,
                val playUrlWatermark: String,
                val privateMessageActionUrl: Any,
                val reallyCollected: Boolean,
                val recentOnceReply: Any,
                val releaseTime: Long,
                val resourceType: String,
                val selectedTime: Any,
                val source: String,
                val status: Any,
                val tags: List<Tag>,
                val title: String,
                val transId: Any,
                val type: String,
                val uid: Int,
                val updateTime: Long,
                val url: String,
                val urls: List<String>,
                val urlsWithWatermark: List<String>,
                val validateResult: String,
                val validateStatus: String,
                val validateTaskId: String,
                val width: Int
            ) {
                data class Author(
                    val adTrack: Any,
                    val approvedNotReadyVideoCount: Int,
                    val description: String,
                    val expert: Boolean,
                    val follow: Follow,
                    val icon: String,
                    val id: Int,
                    val ifPgc: Boolean,
                    val latestReleaseTime: Long,
                    val link: String,
                    val name: String,
                    val recSort: Int,
                    val shield: Shield,
                    val videoNum: Int
                ) {
                    data class Follow(
                        val followed: Boolean,
                        val itemId: Int,
                        val itemType: String
                    )

                    data class Shield(
                        val itemId: Int,
                        val itemType: String,
                        val shielded: Boolean
                    )
                }
                data class Consumption(
                    val collectionCount: Int,
                    val playCount: Int,
                    val realCollectionCount: Int,
                    val replyCount: Int,
                    val shareCount: Int
                )

                data class Cover(
                    val blurred: Any,
                    val detail: String,
                    val feed: String,
                    val homepage: Any,
                    val sharing: Any
                )

                data class Owner(
                    val actionUrl: String,
                    val area: Any,
                    val avatar: String,
                    val bannedDate: Any,
                    val bannedDays: Any,
                    val birthday: Long,
                    val city: String,
                    val country: String,
                    val cover: String,
                    val description: String,
                    val expert: Boolean,
                    val followed: Boolean,
                    val gender: String,
                    val ifPgc: Boolean,
                    val job: String,
                    val library: String,
                    val limitVideoOpen: Boolean,
                    val nickname: String,
                    val registDate: Long,
                    val releaseDate: Long,
                    val tagIds: Any,
                    val uid: Int,
                    val university: String,
                    val uploadStatus: String,
                    val userMedalBeanList: Any,
                    val userType: String,
                    val username: String
                )

                data class Tag(
                    val actionUrl: String,
                    val adTrack: Any,
                    val alias: Any,
                    val bgPicture: String,
                    val childTagIdList: Any,
                    val childTagList: Any,
                    val communityIndex: Int,
                    val desc: String,
                    val haveReward: Boolean,
                    val headerImage: String,
                    val id: Int,
                    val ifNewest: Boolean,
                    val ifShow: Boolean,
                    val level: Int,
                    val name: String,
                    val newestEndTime: Any,
                    val parentId: Int,
                    val recWeight: Double,
                    val relationType: Int,
                    val tagRecType: String,
                    val tagStatus: String,
                    val top: Int,
                    val type: String
                )
            }
        }

        data class Header(
            val actionUrl: String,
            val followType: String,
            val icon: String,
            val iconType: String,
            val id: Int,
            val issuerName: String,
            val labelList: Any,
            val showHateVideo: Boolean,
            val tagId: Int,
            val tagName: Any,
            val time: Long,
            val topShow: Boolean
        )
    }
}
