package com.wssg.kaiyan.page.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wssg.kaiyan.widget.view.PortraitTitleView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.page.adapter.VideoRelatedRvAdapter
import com.wssg.kaiyan.page.viewmodel.PlayVideoActivityViewModel
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity
import com.wssg.lib.base.net.DataState
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.*
import xyz.doikki.videoplayer.player.AndroidMediaPlayer
import xyz.doikki.videoplayer.player.VideoView
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:
 */
class PlayVideoActivity : BaseVmActivity<PlayVideoActivityViewModel>(isCancelStatusBar = false) {
    private val videoView
            by R.id.vv_activity_playVideo.view<xyz.doikki.videoplayer.player.VideoView<AndroidMediaPlayer>>()
    private val titleTv by R.id.tv_playVideo_title.view<TextView>()
    private val subTitleTv by R.id.tv_playVideo_subtitle.view<TextView>()
    private val descriptionTv by R.id.tv_playVideo_description.view<TextView>()
    private val collectionTv by R.id.tv_playVideo_agree.view<TextView>()
    private val shareTv by R.id.tv_playVideo_share.view<TextView>()
    private val commentTv by R.id.tv_playVideo_comment.view<TextView>()
    private val recyclerView by R.id.rv_playVideoActivity.view<RecyclerView>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        cancelStatusBar()
        val videoBean = intent.getSerializableExtra("videoBean") as VideoInfoData
        initVideoPlayer(videoBean)
        initView(videoBean)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun initView(videoDetailBean: VideoInfoData) {
        titleTv.text = videoDetailBean.title
        subTitleTv.text = "#${videoDetailBean.kind} ${
            SimpleDateFormat("/ yyyy/MM/dd HH:mm").format(Date(videoDetailBean.releaseDate))
        }"
        descriptionTv.text = videoDetailBean.description
        collectionTv.text = videoDetailBean.consumption.collectionCount.toString()
        shareTv.text = videoDetailBean.consumption.shareCount.toString()
        commentTv.text = videoDetailBean.consumption.replyCount.toString()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = VideoRelatedRvAdapter(null)
        viewModel.videoRelatedData.observe(this) { gottenData ->
            if (gottenData.dataState == DataState.STATE_SUCCESS) {
                val realData = mutableListOf<VideoInfoData>()
                var header = ""
                //Gson转换的数据类无用的字段嵌套太多了。转换一下
                gottenData.itemList!!.forEach {
                    if (it.type == "videoSmallCard")
                        realData.add(it.data.run {
                            val vData = VideoInfoData(
                                id.toInt(),
                                playUrl,
                                cover.feed,
                                title,
                                category,
                                description,
                                VideoInfoData.Consumption(
                                    consumption.collectionCount.toInt(),
                                    consumption.shareCount.toInt(),
                                    consumption.replyCount.toInt()
                                ),
                                author?.name ?: "",
                                author?.description ?:"",
                                author?.icon?:"",
                                duration.toInt(),
                                releaseTime,
                                null
                            )
                            if (header != "") vData.bannerData =
                                mutableListOf(vData.copy(kind = header))//用之前携带banner的功能，分别类别
                            vData
                        })
                    header = if (it.type == "textCard") it.data.text else ""
                }
                adapter.data = realData
                recyclerView.adapter = adapter
            }
        }
        recyclerView.adapter = adapter
        adapter.setOnClickedListener(object : VideoRelatedRvAdapter.OnClickedListener {
            override fun onClicked(videoInfoData: VideoInfoData) {
                startActivity(
                    Intent(
                        this@PlayVideoActivity,
                        PlayVideoActivity::class.java
                    ).putExtra("videoBean", videoInfoData)
                )
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = true
        viewModel.getVideoRelatedData(videoDetailBean.id)
    }

    private fun initVideoPlayer(videoDetailBean: VideoInfoData) {

        val controller = StandardVideoController(this)
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(false)
        val prepareView = PrepareView(this) //准备播放界面
        prepareView.setClickStart()
        val thumb = prepareView.findViewById<ImageView>(xyz.doikki.videocontroller.R.id.thumb) //封面图
        controller.addControlComponent(prepareView)

        controller.addControlComponent(CompleteView(this)) //自动完成播放界面

        controller.addControlComponent(ErrorView(this)) //错误界面

        val gestureControlView = GestureView(this) //滑动控制视图
        controller.addControlComponent(gestureControlView)

        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(true)

        val titleView = TitleView(this) //标题栏
        val portraitTitleView = PortraitTitleView(this)
        controller.addControlComponent(portraitTitleView)
        controller.addControlComponent(titleView)

        val vodControlView = VodControlView(this) //点播控制条
        controller.addControlComponent(vodControlView)

        videoView.setVideoController(controller)
        videoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9)
        videoView.setUrl(videoDetailBean.playUrl)
        titleView.setTitle(videoDetailBean.title)
        portraitTitleView.setTitle(videoDetailBean.title)
        Glide.with(this)
            .load(videoDetailBean.coverUrl)
            .into(thumb)
        videoView.release()
        videoView.start()
    }

    private fun cancelStatusBar() {
        val window = this.window
        val decorView = window.decorView

        // 这是 Android 做了兼容的 Compat 包
        // 注意，使用了下面这个方法后，状态栏不会再有东西占位，
        // 可以给根布局加上 android:fitsSystemWindows=true
        // 不同布局该属性效果不同，请给合适的布局添加
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = ViewCompat.getWindowInsetsController(decorView)
        windowInsetsController?.isAppearanceLightStatusBars = false // 设置状态栏字体颜色为白色
        window.statusBarColor = Color.TRANSPARENT //把状态栏颜色设置成透明
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
    }

    override fun onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed()
        }
    }

}