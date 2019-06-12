package com.demo.recycleview

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-04 23:20
 */
class CachedListWrapper<T> : ArrayList<T>() {

    override fun add(index: Int, element: T) {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf", "二级缓存mCachedViews + add$element")
        }
        super.add(index, element)
    }

    override fun removeAt(index: Int): T {
        if(this[index] is RecyclerView.ViewHolder) {
            Log.d("xjf","二级缓存mCachedViews + removeAt" + this[index])

        }
        return super.removeAt(index)
    }

    override fun clear() {
        Log.d("xjf","mCachedViews + clear")
        super.clear()
    }

}