package com.example.awesomeextensions

import android.view.View


/***
 * TODO
 * double click in custom time
 * long click in custome time
 */

fun View.onDebounceClick(
    intervalInMillis: Long,
    onClick: () -> Unit
) {
    var lastTapTimestamp: Long = 0
    val customListener = View.OnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTapTimestamp > intervalInMillis) {
            lastTapTimestamp = currentTime
            onClick.invoke()
        }
    }
    this.setOnClickListener(customListener)
}