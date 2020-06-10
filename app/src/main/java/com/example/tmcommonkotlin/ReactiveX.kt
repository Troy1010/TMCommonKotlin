package com.example.tmcommonkotlin

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
fun <T> Observable<T>.logO(msgPrefix: String?=null) : Observable<T> {
    val msgPrefixZ : String = if (msgPrefix==null) "" else { "$msgPrefix`" }
    this
        .subscribeOn(Schedulers.io())
        .subscribe({ logz("$msgPrefixZ$it") }, {logz("logO`Error`$msgPrefixZ$it")})
    return this
}

@SuppressLint("CheckResult")
fun <T> Observable<T>.log(msgPrefix:String? = null, bType:Boolean=false) : Observable<T> {
    val msgPrefixZ : String = if (msgPrefix==null) "" else { "$msgPrefix`" }
    this
        .subscribeOn(Schedulers.io())
        .subscribe({
            if (bType) {
                val typeName = if (it==null) {
                    "null"
                } else {
                    (it as Any)::class.java.simpleName
                }
                logz("$msgPrefixZ$typeName`$it")
            } else {
                logz("$msgPrefixZ$it")
            }
        }, {logz("log`Error`$msgPrefixZ$it")})
    return this
}


val TMAlphabet = "abcdefghijklmnopqrstuvwxyz"
var TMRXCounter = 0
fun <T> Observable<T>.loga(bType:Boolean=false) : Observable<T> {
    val alphabetCharacter = TMAlphabet[TMRXCounter%TMAlphabet.length].toString().repeat(3)
    TMRXCounter++
    return this.log(alphabetCharacter, bType=bType)
}
fun <T> Observable<T>.logb() : Observable<T> {
    val alphabetCharacter = TMAlphabet[TMRXCounter%TMAlphabet.length].toString().repeat(3)
    TMRXCounter++
    return this.log(alphabetCharacter, bType=true)
}