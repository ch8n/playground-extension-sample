package com.example.awesomeextensions

import android.os.SystemClock
import android.util.Log
import android.view.View
import java.lang.Math.abs
import java.util.*


/***
 * TODO
 * double click in custom time
 * long click in custome time
 */

inline fun View.onDebounceClick(debounceTime: Long = 1200L, crossinline action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

inline fun View.onDoubleClick(interval: Long = 1500L, crossinline action: () -> Unit) {
    var count = 0
    var firstClickRecord: Long = 0
    var secondClickRecord: Long = 0
    this.setOnClickListener {
        count += 1
        when (count) {
            1 -> firstClickRecord = Calendar.getInstance().timeInMillis
            2 -> {
                secondClickRecord = Calendar.getInstance().timeInMillis
                if (abs(firstClickRecord - secondClickRecord) < interval) {
                    action()
                }
                count = 0
            }
        }
    }
}
