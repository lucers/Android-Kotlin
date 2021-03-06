package com.lucers.widget.adapter

import android.content.Context
import android.text.TextWatcher
import android.util.SparseArray
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseViewHolder
 *
 * @author Lucers
 */
open class BaseViewHolder(val context: Context, private val convertView: View) :
    RecyclerView.ViewHolder(convertView) {

    private val views: SparseArray<View> = SparseArray()

    @Suppress("UNCHECKED_CAST")
    fun <T : View?> getView(viewId: Int): T {
        var view = views[viewId]
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence?): BaseViewHolder {
        val view = getView<TextView?>(viewId)
        view?.text = text
        return this
    }

    fun setSelected(viewId: Int, selected: Boolean): BaseViewHolder {
        val view = getView<View?>(viewId)
        view?.isSelected = selected
        return this
    }

    fun setEnable(viewId: Int, enable: Boolean): BaseViewHolder {
        val view = getView<View?>(viewId)
        view?.isEnabled = enable
        return this
    }

    fun setTextColor(viewId: Int, color: Int): BaseViewHolder {
        val view = getView<TextView?>(viewId)
        view?.setTextColor(color)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): BaseViewHolder {
        val view = getView<View?>(viewId)
        view?.setBackgroundColor(color)
        return this
    }

    fun setVisibility(viewId: Int, visibility: Int): BaseViewHolder {
        val view = getView<View?>(viewId)
        view?.visibility = visibility
        return this
    }

    fun setClickListener(viewId: Int, clickListener: View.OnClickListener?): BaseViewHolder {
        val view = getView<View?>(viewId)
        view?.setOnClickListener(clickListener)
        return this
    }

    fun setTextWatcher(viewId: Int, textWatcher: TextWatcher): BaseViewHolder {
        val view = getView<EditText?>(viewId)
        view?.addTextChangedListener(textWatcher)
        return this
    }
}