package com.tminus1010.tmcommonkotlin.misc

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


fun getToday() : String {
    return SimpleDateFormat("yyyy-MM-dd").format(java.util.Calendar.getInstance().getTime()).toString()
}

fun easySnackbar(
    coordinatorLayout: CoordinatorLayout,
    msg: String,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    var sb = Snackbar
        .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
    if ((actionText != null) and (action != null)) {
        sb.setAction(actionText, action)
    }
    sb.show()
}

fun generateUniqueID(): String {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase()
}

val fnName
    get() = Throwable().stackTrace[1].methodName

fun getFnName(level:Int=1) {
    Throwable().stackTrace[level].methodName
}
