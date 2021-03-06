package com.wssg.kaiyan.page.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wssg.kaiyan.R
import com.wssg.kaiyan.model.bean.CategoryBean

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/15
 * @Description:
 */
class CategoriesRvAdapter(private var data: List<CategoryBean>) :
    RecyclerView.Adapter<CategoriesRvAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverIv:ImageView
        var categoryTv:TextView
        init {
            categoryTv = view.findViewById(R.id.tv_rvItemCategories_category)
            coverIv = view.findViewById(R.id.iv_rvItemCategories_category)
            coverIv.setOnClickListener {
                listener?.onClicked(data[absoluteAdapterPosition],coverIv)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_categories_rv,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            categoryTv.text = data[position].name
            Glide.with(itemView).load(data[position].bgPicture).into(coverIv)
        }
    }

    override fun getItemCount(): Int = data.size

    fun interface OnClickedListener {
        fun onClicked(categoryData: CategoryBean,view: View)
    }

    private var listener: OnClickedListener? = null
    fun setOnClickedListener(listener: OnClickedListener) {
        this.listener = listener
    }
}