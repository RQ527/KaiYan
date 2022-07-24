package com.wssg.kaiyan.widget.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.wssg.kaiyan.R
import kotlin.math.log

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/24
 * @Description:
 */
class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var data = mutableListOf<String>()

    //外部设置数据
    fun addData(data: List<String>) {
        for (d in data) {
            val textView =
                LayoutInflater.from(context).inflate(R.layout.textview_flow_layout, this, true)
                    .findViewById<TextView>(R.id.tv_flow_layout)
            textView.text = d
            this.data.add(d)
            addView(View(context).apply {
                layoutParams = LayoutParams(40, 1)//用空的view来占位，达到两个TextView间隔一定距离
            })
            this.data.add("")//加个空数据防止通过位置获取数据而错位
        }
    }

    private lateinit var childView: View
    private lateinit var childTextView: TextView

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount
        if (count != 0) {
            var hasPlacedWidth = 0
            var hasPlacedHeight = 0
            val childHeight = getChildAt(0).measuredHeight
            for (i in 0 until count) {
                childView = getChildAt(i)
                val childWidth = childView.measuredWidth
                if (hasPlacedWidth + childWidth > measuredWidth) {
                    hasPlacedWidth = 0
                    hasPlacedHeight += childHeight + 20//上下间隔距离
                }
                childView.layout(
                    hasPlacedWidth,
                    hasPlacedHeight,
                    hasPlacedWidth + childWidth,
                    hasPlacedHeight + childHeight
                )
                if (childView is TextView) {
                    childTextView = getChildAt(i) as TextView
                    childTextView.text = data[i]
                    childTextView.setOnClickListener(onClickListener)//在这设置监听，因为我发现在add的时候监听无效，我猜测是向上转型丢失了属性
                }
                hasPlacedWidth += childWidth
            }
        }
    }

    private val onClickListener = OnClickListener { v ->
        v as TextView
        listener?.clicked(v.text.toString())
    }

    fun interface OnItemClickListener {
        fun clicked(content: String)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickedListener(l: OnItemClickListener) {
        listener = l
    }
}