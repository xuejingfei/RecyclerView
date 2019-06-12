package com.demo.recycleview

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import java.lang.reflect.Field

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-09 18:05
 */
class RecyclerPoolSparseArrayWrapper<T : Any>:SparseArray<T>() {
    override fun put(key: Int, value: T) {
        Log.d("xjf", "recyclerPool + put$key")
        val mScrapHeapField  = value.javaClass.getDeclaredField("mScrapHeap") as Field
        mScrapHeapField.isAccessible = true
        mScrapHeapField.set(value,RecyclerPoolScrapListWrapper<RecyclerView.ViewHolder> ())
        super.put(key, value)
    }


    override fun removeAt(index: Int) {
        Log.d("xjf", "四级缓存recyclerPool + remove${get(index)}")
        super.removeAt(index)
    }

    override fun clear() {
        Log.d("xjf", "四级缓存recyclerPool + clear")
        super.clear()
    }
}