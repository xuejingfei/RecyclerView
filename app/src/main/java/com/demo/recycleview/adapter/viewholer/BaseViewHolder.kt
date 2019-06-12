package com.demo.recycleview.adapter.viewholer

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-02 21:22
 */
class BaseViewHolder(view :View,tag:Int):RecyclerView.ViewHolder(view) {
    var mTag = tag
    var mViews:SparseArray<View> = SparseArray()
    var mConvertView:View = view

    fun <T> getView(id:Int):T where T:View{
        var v = mViews[id]
        if(v == null) {
            v = mConvertView.findViewById(id)
            mViews.put(id,v)
            return v as T

        }
        return v as T
    }

    override fun hashCode(): Int {
        return mTag
    }


    override fun toString(): String {
        return "hashcode---->" + mTag + "position--->" + adapterPosition
    }

    companion object{
        fun get(parent: ViewGroup,layoutId:Int,tag:Int):BaseViewHolder {
            val mConvertView = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
            val viewHolder = BaseViewHolder(mConvertView,tag)
            Log.d("xjf", "onCreateViewHolder${viewHolder.hashCode()}")
            return viewHolder
        }
    }


}