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

// includes the method name
fun logx (msg:String?=null) {
    val msgZ = if (msg==null) "" else "$msg`"
    val level = if(msg==null) 2 else 1
    val throwable = Throwable()
    Log.d(LOG_TAG, "TM`${throwable.stackTrace[level].methodName}`$msgZ")
}

// includes the class and method names
fun logc (msg:String?=null) {
    val msgZ = if (msg==null) "" else "$msg`"
    val level = if(msg==null) 2 else 1
    val throwable = Throwable()
    Log.d(LOG_TAG, "TM`${shortClassName(throwable.stackTrace[level].className)}`${throwable.stackTrace[level].methodName}`$msgZ")
}