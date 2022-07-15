package com.wssg.kaiyan.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.wssg.kaiyan.R
import xyz.doikki.videoplayer.controller.ControlWrapper
import xyz.doikki.videoplayer.controller.IControlComponent
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.util.PlayerUtils

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:
 */
class PortraitTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IControlComponent {

    private var mControlWrapper: ControlWrapper? = null
    private var mTitle: TextView? = null

    init {
        visibility = GONE
        LayoutInflater.from(context).inflate(R.layout.video_portait_title, this, true)
        val back = findViewById<ImageView>(R.id.btn_video_portrait_title)
        back.setOnClickListener {
            val activity = PlayerUtils.scanForActivity(context)
            if (activity != null && !mControlWrapper!!.isFullScreen) {
                activity.finish()
            }
        }
        mTitle = findViewById(R.id.tv_video_portrait_title)
    }

    fun setTitle(title: String?) {
        mTitle!!.text = title
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View {
        return this
    }

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation?) {
        //只在竖屏屏时才有效
        if (mControlWrapper!!.isFullScreen) return
        if (isVisible) {
            if (visibility == GONE) {
                visibility = VISIBLE
                anim?.let { startAnimation(it) }
            }
        } else {
            if (visibility == VISIBLE) {
                visibility = GONE
                anim?.let { startAnimation(it) }
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE, VideoView.STATE_START_ABORT, VideoView.STATE_PREPARING, VideoView.STATE_PREPARED, VideoView.STATE_ERROR, VideoView.STATE_PLAYBACK_COMPLETED -> visibility =
                GONE
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        when(playerState){//屏幕发生变化改变可见性
            VideoView.PLAYER_NORMAL->{
                visibility = VISIBLE
            }
            VideoView.PLAYER_FULL_SCREEN ->{
                visibility = GONE
            }
        }
    }

    override fun setProgress(duration: Int, position: Int) {}

    override fun onLockStateChanged(isLocked: Boolean) {

    }

}