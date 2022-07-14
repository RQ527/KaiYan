package com.wssg.kaiyan

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
data class TestBean(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
) {
    data class Data(
        val coinInfo: CoinInfo,
        val collectArticleInfo: CollectArticleInfo,
        val userInfo: UserInfo
    ) {
        data class CoinInfo(
            val coinCount: Int,
            val level: Int,
            val nickname: String,
            val rank: String,
            val userId: Int,
            val username: String
        )

        data class CollectArticleInfo(
            val count: Int
        )

        data class UserInfo(
            val admin: Boolean,
            val chapterTops: List<Any>,
            val coinCount: Int,
            val collectIds: List<Int>,
            val email: String,
            val icon: String,
            val id: Int,
            val nickname: String,
            val password: String,
            val publicName: String,
            val token: String,
            val type: Int,
            val username: String
        )
    }
}