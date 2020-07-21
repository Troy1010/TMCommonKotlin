package com.example.tmcommonkotlin.logging

import android.util.Log
import com.example.tmcommonkotlin.extras.shortClassName


val fnName
    get() = Throwable().stackTrace[1].methodName

fun getFnName(level:Int=1) {
    Throwable().stackTrace[level].methodName
}

const val LOG_TAG = "TMLog"

//
fun TMLog (msg:String?, bClass:Boolean=false, bMethod:Boolean=false) {
    var msgZ = msg ?: ""
    val level = if(msg==null) 2 else 1
    val throwable = Throwable()
    if (bClass)
        msgZ = "${shortClassName(throwable.stackTrace[level].className)}`"+msgZ
    if (bMethod)
        msgZ = "${shortClassName(throwable.stackTrace[level].className)}`"+msgZ
    Log.d(LOG_TAG, "TM`$msgZ")
}

fun logz (msg:String) {
    TMLog(msg)
}

// includes the method name
fun logx (msg:String?=null) {
    TMLog(msg, bMethod = true)
}

// includes the class and method names
fun logc (msg:String?=null) {
    TMLog(msg, bClass = true, bMethod = true)
}

// includes the class names
fun logv (msg:String?=null) {
    TMLog(msg, bClass = true)
}