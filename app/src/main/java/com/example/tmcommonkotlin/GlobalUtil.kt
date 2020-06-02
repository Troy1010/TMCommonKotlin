package com.example.tmcommonkotlin

import android.content.Context
import android.util.Log
import android.widget.Toast

fun logz (msg:String) {
    Log.d("TMLog", "TM`$msg")
}

fun easyToast(context: Context, msg: String, lengthID:Int=Toast.LENGTH_SHORT) {
    Toast.makeText(context, msg, lengthID).show()
}

//fun invokeHiddenMethod(name: String) {
//    val method = sut.javaClass.getDeclaredMethod(name)
//    method.isAccessible = true
//    method.invoke(testSubject)
//}