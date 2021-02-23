package com.lucers.widget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseRecyclerViewAdapter
 *
 * @author Lucers
 */
abstract class BaseRecyclerViewAdapter<Item>(
    open var headerLayoutId: Int = 0,
    open var emptyLayoutId: Int = 0,
    open var footerLayoutId: Int = 0,
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 751451
        const val VIEW_TYPE_EMPTY = 41223
        const val VIEW_TYPE_FOOTER = 611151
    }

    var list: List<Item> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        if (emptyLayoutId != 0) {
            if (list.isNullOrEmpty()) {
                return VIEW_TYPE_EMPTY
            }
        }
        if (headerLayoutId != 0) {
            if (position == 0) {
                return VIEW_TYPE_HEADER
            }
        }
        if (footerLayoutId != 0) {
            if (position == itemCount - 1) {
                return VIEW_TYPE_FOOTER
            }
        }
        return super.getItemViewType(position)
    }

    open var itemClickListener: OnItemClickListener? = null
    open var itemLongClickListener: OnItemLongClickListener? = null
    open var footerClickListener: OnFooterClickListener? = null
    open var emptyClickListener: OnEmptyClickListener? = null
    open var headerClickListener: OnHeaderClickListener? = null
    open var onAdapterViewListener: OnAdapterViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView: View
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_HEADER -> {
                itemView = inflater.inflate(headerLayoutId, parent, false)
                itemView.setOnClickListener(headerClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                itemView = inflater.inflate(emptyLayoutId, parent, false)
                itemView.setOnClickListener(emptyClickListener)
            }
            VIEW_TYPE_FOOTER -> {
                itemView = inflater.inflate(footerLayoutId, parent, false)
                itemView.setOnClickListener(footerClickListener)
            }
            else -> {
                itemView = inflater.inflate(getItemLayoutId(viewType), parent, false)
            }
        }
        return BaseViewHolder(parent.context, itemView)
    }

    abstract fun getItemLayoutId(viewType: Int): Int

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_HEADER -> onBindHeaderViewHolder(holder)
            VIEW_TYPE_EMPTY -> onBindEmptyViewHolder(holder)
            VIEW_TYPE_FOOTER -> onBindFooterViewHolder(holder)
            else -> if (list.isNullOrEmpty().not()) {
                var adapterPosition = holder.adapterPosition
                if (headerLayoutId != 0) {
                    adapterPosition = holder.adapterPosition - 1
                }
                onBindSimpleViewHolder(holder, list[adapterPosition])
                itemClickListener?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.onItemClick(it, adapterPosition)
                    }
                }

                itemLongClickListener?.let { listener ->
                    holder.itemView.setOnLongClickListener {
                        listener.onItemLongClick(it, adapterPosition)
                        false
                    }
                }
            }
        }
    }

    open fun onBindHeaderViewHolder(holder: BaseViewHolder) = Unit

    open fun onBindSimpleViewHolder(holder: BaseViewHolder, item: Item) = Unit

    open fun onBindFooterViewHolder(holder: BaseViewHolder) = Unit

    open fun onBindEmptyViewHolder(holder: BaseViewHolder) = Unit

    override fun getItemCount(): Int {
        when {
            list.isNullOrEmpty() -> {
                return if (emptyLayoutId == 0) {
                    0
                } else {
                    1
                }
            }
            else -> {
                var count = list.size
                if (headerLayoutId != 0) {
                    count += 1
                }
                if (footerLayoutId != 0) {
                    count += 1
                }
                return count
            }
        }
    }

    fun addData(index: Int = this.list.size, list: List<Item>) {
        if (index > this.list.size && list !is MutableList) {
            return
        }
        (this.list as MutableList).addAll(index, list)
        notifyItemRangeInserted(if (headerLayoutId == 0) index else index + 1, list.size)
    }

    fun addData(index: Int = this.list.size, item: Item) {
        if (index > this.list.size && list !is MutableList) {
            return
        }
        (list as MutableList).add(index, item)
        notifyItemInserted(if (headerLayoutId == 0) index else index + 1)
    }

    fun removeData(item: Item) {
        val index = list.indexOf(item)
        if (index >= 0 && list is MutableList) {
            (list as MutableList).remove(item)
            notifyItemRemoved(if (headerLayoutId == 0) index else index + 1)
            notifyItemRangeChanged(if (headerLayoutId == 0) index else index + 1, list.size - index)
        }
    }

    fun removeData(itemList: List<Item>) {
        itemList.forEach {
            this.removeData(it)
        }
    }

    fun removeData(index: Int) {
        if (index > this.list.size) {
            return
        }
        if (index >= 0 && list is MutableList) {
            (list as MutableList).removeAt(index)
            notifyItemRemoved(if (headerLayoutId == 0) index else index + 1)
            notifyItemRangeChanged(if (headerLayoutId == 0) index else index + 1, list.size - index)
        }
    }

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {

        fun onItemLongClick(view: View, position: Int)
    }

    interface OnFooterClickListener : View.OnClickListener

    interface OnEmptyClickListener : View.OnClickListener

    interface OnHeaderClickListener : View.OnClickListener

    interface OnAdapterViewListener {

        fun onAdapterViewClick(view: View, position: Int)
    }
}