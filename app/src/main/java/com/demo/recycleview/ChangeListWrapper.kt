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
class ChangeListWrapper<T> : ArrayList<T>() {

    override fun add(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf", "一级缓存mChangeScrap + add$element")
        }
        return super.add(element)
    }

    override fun remove(element: T): Boolean {
        if(element is RecyclerView.ViewHolder) {
            Log.d("xjf","一级缓存mChangeScrap + remove$element")
        }
        return super.add(element)
    }


    override fun get(index: Int): T {
        Log.d("xjf","一级缓存mChangeScrap + get$index")
        return super.get(index)
    }


    override fun clear() {
        Log.d("xjf","一级缓存mChangeScrap + clear")
        super.clear()
    }

}