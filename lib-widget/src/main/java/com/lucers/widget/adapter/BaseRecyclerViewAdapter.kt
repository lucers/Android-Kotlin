package com.lucers.widget.adapter

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
abstract class BaseRecyclerViewAdapter<Item, Holder : RecyclerView.ViewHolder>(
    var emptyLayoutId: Int = 0,
    var bottomLayoutId: Int = 0
) : RecyclerView.Adapter<Holder>(), View.OnClickListener, OnLongClickListener {

    companion object {
        const val VIEW_TYPE_EMPTY = 41223
        const val VIEW_TYPE_BOTTOM = 250053
    }

    var list: MutableList<Item> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: OnItemClickListener? = null
    var itemLongClickListener: OnItemLongClickListener? = null
    var bottomClickListener: OnBottomClickListener? = null
    var emptyClickListener: OnEmptyClickListener? = null

    override fun getItemViewType(position: Int): Int {
        if (emptyLayoutId != 0) {
            if (list.isNullOrEmpty()) {
                return VIEW_TYPE_EMPTY
            }
        }
        if (bottomLayoutId != 0) {
            if (position == list.size) {
                return VIEW_TYPE_BOTTOM
            }
        }
        return super.getItemViewType(position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView: View
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_EMPTY -> {
                itemView = inflater.inflate(emptyLayoutId, parent, false)
                itemView.setOnClickListener(emptyClickListener)
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

        return object : RecyclerView.ViewHolder(itemView) {} as Holder
    }

    abstract fun getItemLayoutId(viewType: Int): Int

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_EMPTY -> onBindEmptyViewHolder(holder)
            VIEW_TYPE_BOTTOM -> onBindBottomViewHolder(holder)
            else -> if (list.isNullOrEmpty().not()) {
                onBindSimpleViewHolder(holder, list[position])
                holder.itemView.tag = position
            }
        }
    }

    open fun onBindSimpleViewHolder(holder: Holder, item: Item) = Unit

    open fun onBindBottomViewHolder(holder: Holder) = Unit

    open fun onBindEmptyViewHolder(holder: Holder) = Unit

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
                    list.size
                } else {
                    list.size + 1
                }
            }
        }
    }

    override fun onClick(view: View) {
        itemClickListener?.onItemClick(view, view.tag as Int)
    }

    override fun onLongClick(view: View): Boolean {
        itemLongClickListener?.onItemLongClick(view, view.tag as Int)
        return false
    }

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {

        fun onItemLongClick(view: View, position: Int)
    }

    interface OnBottomClickListener : View.OnClickListener

    interface OnEmptyClickListener : View.OnClickListener
}