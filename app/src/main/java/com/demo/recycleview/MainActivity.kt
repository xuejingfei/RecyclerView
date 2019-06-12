package com.demo.recycleview

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.View
import com.demo.recycleview.adapter.TestBaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field



@Suppress("INACCESSIBLE_TYPE")
class MainActivity : AppCompatActivity() {
    private val mData =  ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        hideBottomUIMenu()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (index in 1..30) {
            mData.add(index.toString())
        }
        val adapter = TestBaseAdapter(mData)
        rv_content.adapter = adapter
//        rv_content.setItemViewCachxeSize(5)
//        rv_content.itemAnimator = DefaultItemAnimator()
        adapter.notifyDataSetChanged()

        rv_content.layoutListener = object : BaseRecycleVIew.LayoutListener{

            override fun beforeLayout() {
                val recycleField =
                    Class.forName("android.support.v7.widget.RecyclerView").getDeclaredField("mRecycler") as Field
                recycleField.isAccessible = true
                val recyclerClz = recycleField.get(rv_content) as RecyclerView.Recycler

                //hook 一级缓存  mAttachScrap
                val attachScrapField = recyclerClz.javaClass.getDeclaredField("mAttachedScrap") as Field
                attachScrapField.isAccessible = true
                attachScrapField.set(recyclerClz, AttachListWrapper<RecyclerView.ViewHolder>())

                //hook 一级缓存 mChangeScrap
                val changeScrapField = recyclerClz.javaClass.getDeclaredField("mChangedScrap") as Field
                changeScrapField.isAccessible = true
                changeScrapField.set(recyclerClz, ChangeListWrapper<RecyclerView.ViewHolder>())


                //hook 二级缓存
                val mCachedViewsField = recyclerClz.javaClass.getDeclaredField("mCachedViews") as Field
                mCachedViewsField.isAccessible = true
                mCachedViewsField.set(recyclerClz, CachedListWrapper<RecyclerView.ViewHolder>())

                //hook 三级缓存  自定义



                //hook 四级缓存
                val recyclerPoolField =recyclerClz.javaClass.getDeclaredField("mRecyclerPool") as Field
                recyclerPoolField.isAccessible = true
                val recyclePool = recyclerPoolField.get(recyclerClz) as RecyclerView.RecycledViewPool

                val mScrapField = recyclePool.javaClass.getDeclaredField("mScrap") as Field
                mScrapField.isAccessible = true
                mScrapField.set(recyclePool,RecyclerPoolSparseArrayWrapper<Any>())

            }

            override fun afterLayout() {
            }
        }

//        rv_content.recycledViewPool.setMaxRecycledViews(0,8)
//        var index = 0
//        while (index < 8) {
//            val viewHolder = adapter.createViewHolder(rv_content, 0)
//            rv_content.recycledViewPool.putRecycledView(viewHolder)
//            index++
//        }

        addItem.setOnClickListener {
            mData.add(0, "1")
            adapter.notifyItemInserted(2)
        }

        deleteItem.setOnClickListener {
            adapter.notifyItemRemoved(2)
        }

        changeItem.setOnClickListener {
            mData[3] = "111000"
            adapter.notifyItemChanged(3)
        }

        rv_content.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                getCacheField(recyclerClz, "mCachedViews")
//                getCacheField(recyclerClz, "mChangedScrap")
//                getCacheField(recyclerClz, "mAttachedScrap")
//                getRecyclePool(recyclerClz)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

        })
    }

    /**
     * 获取RecyclerView.Recycler的变量的值
     */
    fun getCacheField(clazz: RecyclerView.Recycler, fieldName :String) {
        val field = clazz.javaClass.getDeclaredField(fieldName) as Field
        field.isAccessible = true
        if(field.get(clazz) ==null) {
            return
        }
        val viewHolderList = field.get(clazz) as ArrayList<RecyclerView.ViewHolder>
        Log.d("xjf", "$fieldName--->")
        for(viewHolder:RecyclerView.ViewHolder in viewHolderList) {
            if(viewHolder.adapterPosition == -1) {
                Log.d("xjf",fieldName + "--->" +viewHolder.hashCode())
            }else {
                Log.d("xjf",fieldName + "--->" +viewHolder.hashCode() + "value:" + mData[viewHolder.adapterPosition])
            }
        }
    }

    fun getRecyclePool(clazz:RecyclerView.Recycler) {
        try{
            val recyclerPoolField =clazz.javaClass.getDeclaredField("mRecyclerPool")
            recyclerPoolField.isAccessible = true
            val recyclePool = recyclerPoolField.get(clazz) as RecyclerView.RecycledViewPool

            val mScrapField = recyclePool.javaClass.getDeclaredField("mScrap")
            mScrapField.isAccessible = true
            val mScrap = mScrapField.get(recyclePool) as SparseArray<*>

            val mScrapDataClass = Class.forName("android.support.v7.widget.RecyclerView\$RecycledViewPool\$ScrapData")
            //获得这个类的变量
            val mScrapHeapField = mScrapDataClass.getDeclaredField("mScrapHeap")
            val mMaxScrapField = mScrapDataClass.getDeclaredField("mMaxScrap")
            mScrapHeapField.isAccessible = true
            mMaxScrapField.isAccessible = true
            var x:Int = 0
            while (x <mScrap.size()) {
                val item = mScrapHeapField.get(mScrap[x]) as ArrayList<RecyclerView.ViewHolder>
                x++
                var y = 0
                while (y < item.size) {
                    if(y == 0) {
                        Log.d("xjf","recyclerPool------>")
                    }
                    Log.d("xjf","recyclerPool" + item[y].hashCode())
                    y++
                }
            }

        }catch (e:Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 隐藏虚拟按键，并且全屏
     */
    private  fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }
}

