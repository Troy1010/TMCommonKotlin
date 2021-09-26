package com.tminus1010.tmcommonkotlin.rx

import org.junit.Test

class CheckNetworkUCTest {

    @Test
    operator fun invoke() {
        // # Given
        // assume connection to internet
        val checkNetworkUC = CheckNetworkUC()
        // # When
        val testObserver = checkNetworkUC().test()
        // # Then
        testObserver.await().assertComplete()
    }
}