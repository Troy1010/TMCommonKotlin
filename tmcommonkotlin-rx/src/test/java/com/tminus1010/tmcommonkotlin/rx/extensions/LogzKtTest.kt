package com.tminus1010.tmcommonkotlin.rx.extensions

import com.tminus1010.tmcommonkotlin.logz.logx
import com.tminus1010.tmcommonkotlin.logz.logz
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase

class LogzKtTest : TestCase() {

    fun testLogz() {
        logz("GZLO")
        "ZGOL".logx("qwer")
        Observable.just("LOGZ").logz("logz").test()
    }
}