package com.example.tmcommonkotlin

import android.content.Context
import android.util.Log
import android.widget.Toast

fun logz (msg:String) {
    Log.d("TMLog", "TM`$msg")
}

fun easyToast(context:Context, s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}