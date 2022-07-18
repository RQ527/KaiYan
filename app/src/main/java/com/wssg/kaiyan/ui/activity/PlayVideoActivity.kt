package com.wssg.kaiyan.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.wssg.kaiyan.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.widget.PortraitTitleView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.bean.VideoInfoData
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity
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
class PlayVideoActivity : BaseVmActivity<HomeFragmentViewModel>(isCancelStatusBar = false) {
    private val videoView
            by R.id.vv_activity_playVideo.view<xyz.doikki.videoplayer.player.VideoView<AndroidMediaPlayer>>()
    private val titleTv by R.id.tv_playVideo_title.view<TextView>()
    private val subTitleTv by R.id.tv_playVideo_subtitle.view<TextView>()
    private val descriptionTv by R.id.tv_playVideo_description.view<TextView>()
    private val collectionTv by R.id.tv_playVideo_agree.view<TextView>()
    private val shareTv by R.id.tv_playVideo_share.view<TextView>()
    private val commentTv by R.id.tv_playVideo_comment.view<TextView>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        cancelStatusBar()
        val videoBean = intent.getSerializableExtra("videoBean") as VideoInfoData
        initVideoPlayer(videoBean)
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

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
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
        titleTv.text = videoDetailBean.title
        subTitleTv.text = "#${videoDetailBean.kind} ${
            SimpleDateFormat("/ yyyy/MM/dd HH:mm").format(Date(videoDetailBean.releaseDate))
        }"
        descriptionTv.text = videoDetailBean.description
        collectionTv.text = videoDetailBean.consumption.collectionCount.toString()
        shareTv.text = videoDetailBean.consumption.shareCount.toString()
        commentTv.text = videoDetailBean.consumption.replyCount.toString()
        videoView.release()
        videoView.start()
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