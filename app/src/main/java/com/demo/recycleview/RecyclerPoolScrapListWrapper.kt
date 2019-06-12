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
class RecyclerPoolScrapListWrapper<T> : ArrayList<T>() {

    override fun add(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf", "四级缓存recyclerPoolScrap + adds$element")
        }
        return super.add(element)
    }

    override fun remove(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf","四级缓存recyclerPoolScrap + remove$element")
        }
        return super.add(element)
    }

    override fun get(index: Int): T {
        return super.get(index)
    }

    override fun clear() {
        Log.d("xjf","四级缓存recyclerPoolScrap + clear")
        super.clear()
    }

}