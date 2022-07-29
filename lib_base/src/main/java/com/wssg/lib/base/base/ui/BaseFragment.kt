package com.wssg.lib.base.base.ui

import android.text.TextUtils.replace
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wssg.lib.base.R
import com.wssg.lib.base.widget.BindView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment : Fragment() {
    protected fun <T : View> Int.view() = BindView<T>(this) { this@BaseFragment }
    protected fun <T : Any, V : RecyclerView.ViewHolder, A : PagingDataAdapter<T, V>> bindAdapterToPaging(
        data: Flow<PagingData<T>>,
        pagingAdapter: A
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                data.collect {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }
}