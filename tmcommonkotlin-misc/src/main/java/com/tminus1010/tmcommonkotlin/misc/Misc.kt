package com.tminus1010.tmcommonkotlin.misc

import android.content.res.Resources
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


fun getToday() : String {
    return SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time).toString()
}

fun easySnackbar(
    coordinatorLayout: CoordinatorLayout,
    msg: String,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    Snackbar
        .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
        .also { if ((actionText != null) and (action != null)) it.setAction(actionText, action) }
        .apply { show() }
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}
