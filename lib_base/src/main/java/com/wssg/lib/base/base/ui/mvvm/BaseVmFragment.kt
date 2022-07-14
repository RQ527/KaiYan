package com.wssg.lib.base.base.ui.mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wssg.lib.base.widget.BindView
import com.wssg.lib.base.base.ui.BaseFragment
import java.lang.reflect.ParameterizedType

abstract class BaseVmFragment<VM : ViewModel> : BaseFragment() {

    @Suppress("UNCHECKED_CAST")
    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        //获得泛型参数的实际类型
        val vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(vmClass)
    }

    fun <T : View> Int.view() = BindView<T>(this, BindView.GetActivity { requireActivity() })

}