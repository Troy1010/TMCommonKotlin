package com.example.tmcommonkotlin

import android.util.Log


val fnName
    get() = Throwable().stackTrace[1].methodName

fun getFnName(level:Int=1) {
    Throwable().stackTrace[level].methodName
}

const val LOG_TAG = "TMLog"

fun logz (msg:String) {
    Log.d(LOG_TAG, "TM`$msg")
}

fun logx (msg:String) {
    Log.d(LOG_TAG, "TM`${Throwable().stackTrace[1].methodName}`$msg")
}

fun logc (msg:String) {
    val throwable = Throwable()
    Log.d(LOG_TAG, "TM`${shortClassName(throwable.stackTrace[1].className)}`${throwable.stackTrace[1].methodName}`$msg")
}