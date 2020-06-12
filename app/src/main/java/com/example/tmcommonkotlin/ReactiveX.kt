package com.example.tmcommonkotlin

import android.annotation.SuppressLint
import io.reactivex.Observable

@SuppressLint("CheckResult")
fun <T> Observable<T>.logO(msgPrefix: String? = null): Observable<T> {
    val tempMsgPrefix: String = if (msgPrefix == null) "" else { "$msgPrefix`" }
    return this.doOnNext { logz("$tempMsgPrefix$it") }
}

@SuppressLint("CheckResult")
fun <T> Observable<T>.log(msgPrefix:String? = null, bType:Boolean=false) : Observable<T> {
    val tempMsgPrefix : String = if (msgPrefix==null) "" else { "$msgPrefix`" }
    return this
        .doOnNext {
            if (bType) {
                val typeName = if (it==null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$tempMsgPrefix$typeName`$it")
            } else {
                logz("$tempMsgPrefix$it")
            }
        }
}


val TMAlphabet = "abcdefghijklmnopqrstuvwxyz"
var TMRXCounter = 0
fun <T> Observable<T>.loga(msgPrefix:String? = null, bType:Boolean=false) : Observable<T> {
    val alphabetCharacter = TMAlphabet[TMRXCounter%TMAlphabet.length].toString().repeat(3)
    TMRXCounter++
    val tempMsgPrefix = if (msgPrefix==null) "" else "`$msgPrefix"
    return this.log("$alphabetCharacter$tempMsgPrefix", bType=bType)
}