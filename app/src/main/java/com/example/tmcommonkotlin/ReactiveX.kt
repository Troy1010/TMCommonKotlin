package com.example.tmcommonkotlin

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.Observable

//// very simple version
//fun <T> Observable<T>.logO(msgPrefix: String? = null): Observable<T> {
//    val tempMsgPrefix: String = if (msgPrefix == null) "" else {
//        "$msgPrefix`"
//    }
//    return this.doOnNext { logz("$tempMsgPrefix$it") }
//}

// bType prints the type
fun <T> Observable<T>.log(msgPrefix: String? = null, bType: Boolean = false): Observable<T> {
    val tempMsgPrefix: String = if (msgPrefix == null) "" else {
        "$msgPrefix`"
    }
    return this
        .doOnNext {
            if (bType) {
                val typeName = if (it == null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$tempMsgPrefix$typeName`$it")
            } else {
                logz("$tempMsgPrefix$it")
            }
        }
        .doOnError {
            logz("${tempMsgPrefix}Error`$it")
        }
}

fun <T> Flowable<T>.log(msgPrefix: String? = null, bType: Boolean = false): Flowable<T> {
    val tempMsgPrefix: String = if (msgPrefix == null) "" else {
        "$msgPrefix`"
    }
    return this
        .doOnNext {
            if (bType) {
                val typeName = if (it == null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$tempMsgPrefix$typeName`$it")
            } else {
                logz("$tempMsgPrefix$it")
            }
        }
        .doOnError {
            logz("${tempMsgPrefix}Error`$it")
        }
}


val TMAlphabet = "abcdefghijklmnopqrstuvwxyz"
var TMRXCounter = 0
// gives its own prefix
fun <T> Observable<T>.loga(msgPrefix: String? = null, bType: Boolean = false): Observable<T> {
    val alphabetCharacter = TMAlphabet[TMRXCounter % TMAlphabet.length].toString().repeat(3)
    TMRXCounter++
    val tempMsgPrefix = if (msgPrefix == null) "" else "`$msgPrefix"
    return this.log("$alphabetCharacter$tempMsgPrefix", bType = bType)
}

@SuppressLint("CheckResult")
fun <T> Observable<T>.logSubscribe(msgPrefix: String? = null, bType: Boolean = false) {
    val tempMsgPrefix: String = if (msgPrefix == null) "" else {
        "$msgPrefix`"
    }
    this
        .subscribe({
            if (bType) {
                val typeName = if (it == null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$tempMsgPrefix$typeName`$it")
            } else {
                logz("$tempMsgPrefix$it")
            }
        }, {
            logz("${tempMsgPrefix}Error`$it")
        })
}

@SuppressLint("CheckResult")
fun <T> Flowable<T>.logSubscribe(msgPrefix: String? = null, bType: Boolean = false) {
    val tempMsgPrefix: String = if (msgPrefix == null) "" else {
        "$msgPrefix`"
    }
    this
        .subscribe({
            if (bType) {
                val typeName = if (it == null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$tempMsgPrefix$typeName`$it")
            } else {
                logz("$tempMsgPrefix$it")
            }
        }, {
            logz("${tempMsgPrefix}Error`$it")
        })
}
