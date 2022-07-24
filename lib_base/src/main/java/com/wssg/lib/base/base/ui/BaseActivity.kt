package com.wssg.lib.base.base.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wssg.lib.base.widget.BindView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 *@author 985892345
 *@email 2767465918@qq.com
 *@data 2021/5/25
 *@description
 */
abstract class BaseActivity(
    /**
     * 是否锁定竖屏
     */
    private val isPortraitScreen: Boolean = true,

    /**
     * 是否沉浸式状态栏
     *
     * 注意，沉浸式后，状态栏不会再有东西占位，界面会默认上移，
     * 可以给根布局加上 android:fitsSystemWindows=true，
     * 不同布局该属性效果不同，请给合适的布局添加
     */
    private val isCancelStatusBar: Boolean = true,
    /**
     * 是否给状态栏字体设置成黑色，否则白色
     */
    private val statusBarTextColorBlack: Boolean = true,
) : AppCompatActivity() {

    @CallSuper
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isPortraitScreen) { // 锁定竖屏
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (isCancelStatusBar) { // 沉浸式状态栏
            cancelStatusBar()
        }
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
        windowInsetsController?.isAppearanceLightStatusBars = statusBarTextColorBlack // 设置状态栏字体颜色为黑色
        window.statusBarColor = Color.TRANSPARENT //把状态栏颜色设置成透明
    }

    fun <T : View> Int.view() = BindView<T>(this, BindView.GetActivity { this@BaseActivity })

    /**
     * 将从paging获取的数据提交至adapter
     */
    protected fun <T : Any, V : RecyclerView.ViewHolder, A : PagingDataAdapter<T, V>> bindAdapterToPaging(
        data: Flow<PagingData<T>>,
        pagingAdapter: A
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                data.collect {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

}
