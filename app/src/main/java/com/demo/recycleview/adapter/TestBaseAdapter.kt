package com.demo.recycleview.adapter

import android.widget.TextView
import com.demo.recycleview.R
import com.demo.recycleview.adapter.viewholer.BaseViewHolder

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-06-02 22:09
 */
class TestBaseAdapter(datas:ArrayList<String>): BaseAdapter<String>(datas) {
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_view

    }

    override fun convert(viewHolder: BaseViewHolder, data: String, pos: Int) {
        val tvTitle = viewHolder.getView<TextView>(R.id.tv_title)
        tvTitle.text = data
    }
}