package com.wssg.kaiyan.page.ui.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.VideoInfoData
import com.wssg.kaiyan.page.adapter.VideoRelatedRvAdapter
import com.wssg.kaiyan.page.viewmodel.PlayVideoActivityViewModel
import com.wssg.kaiyan.widget.view.PortraitTitleView
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
class PlayVideoActivity :
    BaseVmActivity<PlayVideoActivityViewModel>(statusBarTextColorBlack = false) {
    private val videoView
            by R.id.vv_activity_playVideo.view<VideoView<AndroidMediaPlayer>>()

    private val recyclerView by R.id.rv_playVideoActivity.view<RecyclerView>()
    private val collapsingToolbarLayout by R.id.ctl_activity_playVideo.view<CollapsingToolbarLayout>()
    private val toolbar by R.id.tl_activity_playVideo_title.view<Toolbar>()
    private val appBarLayout by R.id.abl_activity_playVideo.view<AppBarLayout>()
    private val nestedScrollVIew by R.id.nsv_activity_playVideo_scrollView.view<NestedScrollView>()

    companion object {
        fun startActivity(context: Context, videoInfoData: VideoInfoData, bundle: Bundle? = null) {
            context.startActivity(
                Intent(
                    context,
                    PlayVideoActivity::class.java
                ).putExtra("videoBean", videoInfoData), bundle
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        val videoBean = intent.getSerializableExtra("videoBean") as VideoInfoData
        initVideoPlayer(videoBean)
        initView(videoBean)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun initView(videoDetailBean: VideoInfoData) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = VideoRelatedRvAdapter(null, videoDetailBean)
        viewModel.videoRelatedData.observe(this) { gottenData ->
            if (gottenData.dataState == DataState.STATE_SUCCESS) {
                val realData = mutableListOf<VideoInfoData>()
                var header = ""
                //Gson???????????????????????????????????????????????????????????????
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
                                author?.description ?: "",
                                author?.icon ?: "",
                                duration.toInt(),
                                releaseTime,
                                null
                            )
                            if (header != "") vData.bannerData =
                                mutableListOf(vData.copy(kind = header))//???????????????banner????????????????????????
                            vData
                        })
                    header = if (it.type == "textCard") it.data.text else ""
                }
                adapter.data = realData
                recyclerView.adapter = adapter
            }
        }
        recyclerView.adapter = adapter
        adapter.setOnClickedListener { videoInfoData, view ->
            startActivity(
                Intent(
                    this@PlayVideoActivity,
                    PlayVideoActivity::class.java
                ).putExtra("videoBean", videoInfoData),
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    view,
                    "video"
                ).toBundle()
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = true
        viewModel.getVideoRelatedData(videoDetailBean.id)
        //????????????????????????????????????
        toolbar.setOnClickListener {
            appBarLayout.setExpanded(true)
            videoView.start()
        }
        val i0 = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        val i1 = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        val appBarChildAt: View = appBarLayout.getChildAt(0)
        val appBarParams = appBarChildAt.layoutParams as AppBarLayout.LayoutParams
        //???????????????????????????????????????????????????
        videoView.addOnStateChangeListener(object : VideoView.OnStateChangeListener {
            override fun onPlayerStateChanged(playerState: Int) {

            }

            override fun onPlayStateChanged(playState: Int) {
                if (playState == VideoView.STATE_PLAYING) appBarParams.scrollFlags = 0 //??????????????????????????????
                else if (playState == VideoView.STATE_PAUSED) appBarParams.scrollFlags = i0 or i1 // ??????????????????
                appBarChildAt.layoutParams = appBarParams
            }
        })
    }

    private fun initVideoPlayer(videoDetailBean: VideoInfoData) {

        val controller = StandardVideoController(this)
        //??????????????????????????????/????????????
        controller.setEnableOrientation(false)
        val prepareView = PrepareView(this) //??????????????????
        prepareView.setClickStart()
        val thumb = prepareView.findViewById<ImageView>(xyz.doikki.videocontroller.R.id.thumb) //?????????
        controller.addControlComponent(prepareView)

        controller.addControlComponent(CompleteView(this)) //????????????????????????

        controller.addControlComponent(ErrorView(this)) //????????????

        val gestureControlView = GestureView(this) //??????????????????
        controller.addControlComponent(gestureControlView)

        //?????????????????????????????????????????????????????????
        controller.setCanChangePosition(true)

        val titleView = TitleView(this) //?????????
        val portraitTitleView = PortraitTitleView(this)
        controller.addControlComponent(portraitTitleView)
        controller.addControlComponent(titleView)

        val vodControlView = VodControlView(this) //???????????????
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