package com.wssg.kaiyan.bean

import java.io.Serializable

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */

data class VideoRelatedBean(
    val adIndex: Long,
    val `data`: Data,
    val id: Long,
    val tag: Any,
    val trackingData: Any,
    val type: String
):Serializable {
    data class Data(
        val actionUrl: String,
        val ad: Boolean,
        val adTrack: Any,
        val author: Author,
        val brandWebsiteInfo: Any,
        val campaign: Any,
        val category: String,
        val collected: Boolean,
        val consumption: Consumption,
        val cover: Cover,
        val dataType: String,
        val date: Long,
        val description: String,
        val descriptionEditor: String,
        val descriptionPgc: String,
        val duration: Long,
        val favoriteAdTrack: Any,
        val follow: Any,
        val id: Long,
        val idx: Long,
        val ifLimitVideo: Boolean,
        val label: Any,
        val labelList: List<Any>,
        val lastViewTime: Any,
        val library: String,
        val playInfo: List<PlayInfo>,
        val playUrl: String,
        val played: Boolean,
        val playlists: Any,
        val promotion: Any,
        val provider: Provider,
        val reallyCollected: Boolean,
        val recallSource: Any,
        val recall_source: Any,
        val releaseTime: Long,
        val remark: Any,
        val resourceType: String,
        val searchWeight: Long,
        val shareAdTrack: Any,
        val slogan: String,
        val src: Any,
        val subTitle: Any,
        val subtitles: List<Any>,
        val tags: List<Tag>,
        val text: String,
        val thumbPlayUrl: String,
        val title: String,
        val titlePgc: String,
        val type: String,
        val videoPosterBean: Any,
        val waterMarks: Any,
        val webAdTrack: Any,
        val webUrl: WebUrl
    ):Serializable {
        data class Author(
            val adTrack: Any,
            val approvedNotReadyVideoCount: Long,
            val description: String,
            val expert: Boolean,
            val follow: Follow,
            val icon: String,
            val id: Long,
            val ifPgc: Boolean,
            val latestReleaseTime: Long,
            val link: String,
            val name: String,
            val recSort: Long,
            val shield: Shield,
            val videoNum: Long
        ):Serializable {
            data class Follow(
                val followed: Boolean,
                val itemId: Long,
                val itemType: String
            )

            data class Shield(
                val itemId: Long,
                val itemType: String,
                val shielded: Boolean
            ):Serializable
        }

        data class Consumption(
            val collectionCount: Long,
            val realCollectionCount: Long,
            val replyCount: Long,
            val shareCount: Long
        ):Serializable

        data class Cover(
            val blurred: String,
            val detail: String,
            val feed: String,
            val homepage: String,
            val sharing: Any
        ):Serializable

        data class PlayInfo(
            val height: Long,
            val name: String,
            val type: String,
            val url: String,
            val urlList: List<Url>,
            val width: Long
        ) :Serializable{
            data class Url(
                val name: String,
                val size: Long,
                val url: String
            ):Serializable
        }

        data class Provider(
            val alias: String,
            val icon: String,
            val name: String
        ):Serializable

        data class Tag(
            val actionUrl: String,
            val adTrack: Any,
            val bgPicture: String,
            val childTagIdList: Any,
            val childTagList: Any,
            val communityIndex: Long,
            val desc: String,
            val haveReward: Boolean,
            val headerImage: String,
            val id: Long,
            val ifNewest: Boolean,
            val name: String,
            val newestEndTime: Any,
            val tagRecType: String
        ):Serializable

        data class WebUrl(
            val forWeibo: String,
            val raw: String
        ):Serializable
    }
}