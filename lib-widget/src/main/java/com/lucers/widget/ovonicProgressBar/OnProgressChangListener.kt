package com.lucers.widget.ovonicProgressBar

/**
 * OnProgressChangListener
 *
 * @author Lucers
 * @date 2019/4/13
 */
interface OnProgressChangListener {

    /**
     * onProgressChange
     *
     * @param lowProgress  lowProgress
     * @param highProgress highProgress
     */
    fun onProgressChange(lowProgress: Int, highProgress: Int)
}