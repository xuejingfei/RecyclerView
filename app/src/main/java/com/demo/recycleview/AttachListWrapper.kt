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
class AttachListWrapper<T> : ArrayList<T>() {

    override fun add(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf", "一级缓存mAttachedScrap + add$element")
        }
        return super.add(element)
    }

    override fun remove(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf","一级缓存mAttachedScrap + remove$element")
        }
        return super.add(element)
    }

    override fun clear() {
        Log.d("xjf","一级缓存mAttachedScrap + clear")
        super.clear()
    }

}