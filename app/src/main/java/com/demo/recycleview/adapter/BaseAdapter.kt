package com.demo.recycleview.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.demo.recycleview.adapter.viewholer.BaseViewHolder

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-02 21:20
 */
abstract class BaseAdapter<T>(data:ArrayList<T>):RecyclerView.Adapter<BaseViewHolder> () {
    var index = 1
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    private val mDatas = data

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        return BaseViewHolder.get(p0,getLayoutId(p1), index ++)
    }

    override fun getItemCount(): Int {
       return mDatas.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, pos: Int) {
        Log.d("xjf", "onBindViewHolder${viewHolder.hashCode()}" + "位置：" +  mDatas[pos].toString())
        convert(viewHolder,mDatas[pos],pos)
    }

    public abstract fun getLayoutId(viewType :Int) :Int

    public abstract fun convert(viewHolder: BaseViewHolder, data:T,pos :Int)
}