package com.tminus1010.tmcommonkotlin.logz

import android.util.Log

fun shortClassName(className:String):String {
    val periodMatches = Regex("""\..""").findAll(className)
    if (periodMatches.count()==0)
        return className
    val periodPos = periodMatches.last().range.last
    return className.substring(periodPos)
}

const val TAG = "TMLog"

//
fun TMLog (msg:String?, bClass:Boolean=false, bMethod:Boolean=false) {
    var msgZ = msg ?: ""
    val level = if(msg==null) 2 else 1
    val throwable = Throwable()
    if (bClass)
        msgZ = "${shortClassName(throwable.stackTrace[level].className)}`"+msgZ
    if (bMethod)
        msgZ = "${shortClassName(throwable.stackTrace[level].className)}`"+msgZ
    Log.d(TAG, "TM`$msgZ")
}

fun logz (msg:String) {
    TMLog(msg)
}

// TODO("does not always work")
// includes the method name
fun logx (msg:String?=null) {
    TMLog(msg, bMethod = true)
}

// TODO("does not always work")
// includes the class and method names
fun logc (msg:String?=null) {
    TMLog(msg, bClass = true, bMethod = true)
}