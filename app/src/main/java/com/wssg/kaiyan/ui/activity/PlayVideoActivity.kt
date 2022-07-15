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
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity
import com.wssg.lib.base.net.DataState
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.*
import xyz.doikki.videoplayer.player.AbstractPlayer
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
            by R.id.vv_activity_playVideo.view<xyz.doikki.videoplayer.player.VideoView<AbstractPlayer>>()
    private val titleTv by R.id.tv_playVideo_title.view<TextView>()
    private val subTitleTv by R.id.tv_playVideo_subtitle.view<TextView>()
    private val descriptionTv by R.id.tv_playVideo_description.view<TextView>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        cancelStatusBar()
        val videoId = intent.getStringExtra("videoId")
        initVideoPlayer()
        viewModel.getVideoInfo(videoId!!)
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
    private fun initVideoPlayer() {

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

        viewModel.testLiveData.observe(this) {
            if (it.dataState == DataState.STATE_SUCCESS) {
                videoView.release()
                videoView.setUrl(it.itemList!![1].data.playUrl)
                titleView.setTitle(it.itemList!![1].data.title)
                portraitTitleView.setTitle(it.itemList!![1].data.title)
                Glide.with(this)
                    .load(it.itemList!![1].data.cover.feed)
                    .into(thumb)
                titleTv.text = it.itemList!![1].data.title
                subTitleTv.text = "#${it.itemList!![1].data.category} ${
                    SimpleDateFormat("/ yyyy/MM/dd HH:mm").format(Date(it.itemList!![1].data.releaseTime))
                }"
                descriptionTv.text = it.itemList!![1].data.description
                videoView.start()
            }
        }
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