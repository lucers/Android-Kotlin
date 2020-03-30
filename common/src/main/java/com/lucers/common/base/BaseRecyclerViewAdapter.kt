package com.lucers.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseRecyclerViewAdapter
 *
 * @author Lucers
 */
abstract class BaseRecyclerViewAdapter<T>(
    private var emptyLayoutId: Int = 0,
    private var bottomLayoutId: Int = 0
) : RecyclerView.Adapter<BaseViewHolder>(), View.OnClickListener, OnLongClickListener {

    companion object {
        const val VIEW_TYPE_EMPTY = 41223
        const val VIEW_TYPE_BOTTOM = 250053
    }

    private var list: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: OnItemClickListener? = null
    var itemLongClickListener: OnItemLongClickListener? = null
    var bottomClickListener: OnBottomClickListener? = null

    override fun getItemViewType(position: Int): Int {
        if (emptyLayoutId != 0) {
            if (list.isNullOrEmpty()) {
                return VIEW_TYPE_EMPTY
            }
        }
        if (bottomLayoutId != 0) {
            if (position == list?.size) {
                return VIEW_TYPE_BOTTOM
            }
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView: View
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_EMPTY -> {
                itemView = inflater.inflate(emptyLayoutId, parent, false)
            }
            VIEW_TYPE_BOTTOM -> {
                itemView = inflater.inflate(bottomLayoutId, parent, false)
                itemView.setOnClickListener(bottomClickListener)
            }
            else -> {
                itemView = inflater.inflate(getItemLayoutId(viewType), parent, false)
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }
        }
        return BaseViewHolder(parent.context, itemView)
    }

    abstract fun getItemLayoutId(viewType: Int): Int

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_EMPTY -> onBindEmptyViewHolder(holder)
            VIEW_TYPE_BOTTOM -> onBindBottomViewHolder(holder)
            else -> if (list.isNullOrEmpty().not()) {
                onBindSimpleViewHolder(holder, list!![position])
                holder.itemView.tag = position
            }
        }
    }

    open fun onBindSimpleViewHolder(holder: BaseViewHolder, item: T) = Unit

    open fun onBindBottomViewHolder(holder: BaseViewHolder) = Unit

    open fun onBindEmptyViewHolder(holder: BaseViewHolder) = Unit

    override fun getItemCount(): Int {
        return when {
            list.isNullOrEmpty() -> {
                if (emptyLayoutId == 0) {
                    0
                } else {
                    1
                }
            }
            else -> {
                if (bottomLayoutId == 0) {
                    list!!.size
                } else {
                    list!!.size + 1
                }
            }
        }
    }

    override fun onClick(view: View) {
        if (itemClickListener != null) {
            itemClickListener!!.onItemClick(view, view.tag as Int)
        }
    }

    override fun onLongClick(view: View): Boolean {
        if (itemLongClickListener != null) {
            itemLongClickListener!!.onItemLongClick(view, view.tag as Int)
        }
        return false
    }

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {

        fun onItemLongClick(view: View, position: Int)
    }

    interface OnBottomClickListener : View.OnClickListener
}