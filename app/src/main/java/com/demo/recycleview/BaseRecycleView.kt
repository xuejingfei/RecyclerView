package com.demo.recycleview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-04 22:39
 */
class BaseRecycleVIew(context:Context,attr: AttributeSet?) :RecyclerView(context,attr) {
    var layoutListener :LayoutListener? = null

    constructor(context:Context) : this(context,null)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutListener?.beforeLayout()
        super.onLayout(changed, l, t, r, b)
        layoutListener?.afterLayout()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
    }


    public interface LayoutListener {
        fun beforeLayout()
        fun afterLayout()
    }




}