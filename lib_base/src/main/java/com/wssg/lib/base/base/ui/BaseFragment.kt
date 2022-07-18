package com.wssg.lib.base.base.ui

import android.text.TextUtils.replace
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.wssg.lib.base.widget.BindView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment : Fragment() {
    protected fun <T : View> Int.view() = BindView<T>(this) { this@BaseFragment }
}