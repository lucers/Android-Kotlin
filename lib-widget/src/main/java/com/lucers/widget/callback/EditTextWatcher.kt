package com.lucers.widget.callback

import android.text.TextWatcher

abstract class EditTextWatcher : TextWatcher {

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
    }
}