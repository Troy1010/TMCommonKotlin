package com.tminus1010.tmcommonkotlin.androidx

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.tminus1010.tmcommonkotlin.androidx.extensions.easyGetLayoutParams
import com.tminus1010.tmcommonkotlin.view.NativeText
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Must use [Activity] or you will see: java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity.
 */
class ShowAlertDialog constructor(private val activity: Activity) {
    /**
     * EditText
     */
    suspend operator fun invoke(body: NativeText, initialText: CharSequence? = null, onSubmitText: ((CharSequence?) -> Unit)? = null, onNo: (() -> Unit)? = null) = suspendCoroutine<Unit> { downstream ->
        launchOnMainThread {
            val editText = EditText(activity)
            editText.easyGetLayoutParams()
            editText.setText(initialText)
            AlertDialog.Builder(activity)
                .setMessage(body.toCharSequence(activity))
                .setView(editText)
                .setPositiveButton("Submit") { _, _ -> onSubmitText?.invoke(editText.text) }
                .setNegativeButton("Cancel") { _, _ -> onNo?.invoke() }
                .setOnDismissListener { downstream.resume(Unit) }
                .show()
        }
    }


    /**
     * Yes/no
     */
    suspend operator fun invoke(body: NativeText, onYes: (() -> Unit)? = null, onNo: (() -> Unit)? = null) = suspendCoroutine<Unit> { downstream ->
        launchOnMainThread {
            AlertDialog.Builder(activity)
                .setMessage(body.toCharSequence(activity))
                .setPositiveButton("Yes") { _, _ -> onYes?.invoke() }
                .setNegativeButton("No") { _, _ -> onNo?.invoke() }
                .setOnDismissListener { downstream.resume(Unit) }
                .show()
        }
    }


    /**
     * Cancel/Continue Anyway/Yes
     */
    suspend operator fun invoke(body: NativeText, onCancel: (() -> Unit)? = null, onContinue: (() -> Unit)? = null, onYes: (() -> Unit)? = null) = suspendCoroutine<Unit> { downstream ->
        launchOnMainThread {
            AlertDialog.Builder(activity)
                .setMessage(body.toCharSequence(activity))
                .setNeutralButton("Cancel") { _, _ -> onCancel?.invoke() }
                .setNegativeButton("Continue Anyway") { _, _ -> onContinue?.invoke() }
                .setPositiveButton("Yes") { _, _ -> onYes?.invoke() }
                .setOnDismissListener { downstream.resume(Unit) }
                .show()
        }
    }


    /**
     * Okay
     */
    suspend operator fun invoke(body: String) = invoke(NativeText.Simple(body))
    suspend operator fun invoke(body: NativeText) = suspendCoroutine<Unit> { downstream ->
        launchOnMainThread {
            AlertDialog.Builder(activity)
                .setMessage(body.toCharSequence(activity))
                .setPositiveButton("Okay") { _, _ -> }
                .setOnDismissListener { downstream.resume(Unit) }
                .show()
        }
    }
}