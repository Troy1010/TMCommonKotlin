package com.tminus1010.tmcommonkotlin.logz

import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase

class TMLogKtTest : TestCase() {
    fun testLogz() {
        logz("AAA")
        "qwer".logx("BBB")
        Observable.just("LOGZ").logx("CCC").subscribe()

        logz(Exception("It's error time"))
        Exception("It's error time").logx("EEE")
    }
}