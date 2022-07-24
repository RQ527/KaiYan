package com.wssg.kaiyan.page.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.wssg.kaiyan.R
import com.wssg.kaiyan.page.adapter.PhotoViewPagerAdapter
import com.wssg.kaiyan.widget.view.PortraitTitleView
import com.wssg.lib.base.base.ui.BaseActivity
import com.wssg.lib.base.base.ui.mvvm.BaseVmActivity
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.*
import xyz.doikki.videoplayer.player.AndroidMediaPlayer
import xyz.doikki.videoplayer.player.VideoView
import java.io.Serializable

class PhotoAndVideoActivity : BaseActivity(statusBarTextColorBlack = false) {
    companion object {
        fun startActivity(context: Context, source: Source, bundle: Bundle? = null) {
            context.startActivity(
                Intent(
                    context,
                    PhotoAndVideoActivity::class.java
                ).putExtra("source", source), bundle
            )
        }

        data class Source(var type: String, var picUrls: List<String>?, var playUrl: String?) :
            Serializable
    }

    private val viewPager by R.id.vp_photoVideoActivity.view<ViewPager2>()
    private val videoView by R.id.vv_photoVideoActivity.view<VideoView<AndroidMediaPlayer>>()
    private val textView by R.id.tv_photoVideoActivity.view<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_and_video)
        initVideoView()
        val source = intent.getSerializableExtra("source")!! as Source
        when (source.type) {
            "ugc_picture" -> {
                viewPager.adapter =
                    PhotoViewPagerAdapter(source.picUrls!!).apply { setOnClickedListener { onBackPressed() } }
                viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("SetTextI18n")
                    override fun onPageSelected(position: Int) {
                        textView.text = "${position + 1}/${source.picUrls!!.size}"
                    }
                })

            }
            "ugc_video" -> {
                textView.visibility = View.GONE
                viewPager.visibility = View.GONE
                videoView.visibility = View.VISIBLE
                videoView.setUrl(source.playUrl)
                videoView.release()
                videoView.start()
            }
        }
    }

    private fun initVideoView() {
        val controller = StandardVideoController(this)
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(false)

        controller.addControlComponent(CompleteView(this)) //自动完成播放界面

        controller.addControlComponent(ErrorView(this)) //错误界面

        val gestureControlView = GestureView(this) //滑动控制视图
        controller.addControlComponent(gestureControlView)

        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(true)


        val vodControlView = VodControlView(this) //点播控制条
        controller.addControlComponent(vodControlView)

        videoView.setVideoController(controller)
        videoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9)
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