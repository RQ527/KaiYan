package com.wssg.kaiyan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wssg.kaiyan.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/17
 * @Description:
 */
class PagingFooterAdapter(val retry: () -> Unit) : LoadStateAdapter<PagingFooterAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar
        var textView: TextView

        init {
            progressBar = view.findViewById(R.id.pb_itemFooter_home)
            textView = view.findViewById(R.id.tv_itemFooter_home)
            textView.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.run {
            when (loadState) {
                is LoadState.Error -> {
                    progressBar.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.text = "加载失败，点击重试"
                }
                is LoadState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    textView.text = "正在加载"
                }
                is LoadState.NotLoading -> {
                    progressBar.visibility = View.GONE
                    textView.text = "加载完成"
                    textView.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv_footer, parent, false)
        )
    }
}