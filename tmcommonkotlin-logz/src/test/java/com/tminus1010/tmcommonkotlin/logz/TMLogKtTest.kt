package com.tminus1010.tmcommonkotlin.logz

import junit.framework.TestCase

class TMLogKtTest : TestCase() {
    fun testLogz() {
        logx("AAA")
        "BBB".logx("bbb")
    }
}