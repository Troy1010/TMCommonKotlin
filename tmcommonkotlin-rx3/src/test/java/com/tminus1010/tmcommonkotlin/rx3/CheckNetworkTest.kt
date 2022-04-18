package com.tminus1010.tmcommonkotlin.rx

import com.tminus1010.tmcommonkotlin.rx3.CheckNetwork
import org.junit.Test

class CheckNetworkTest {

    @Test
    operator fun invoke() {
        // # Given
        // assume connection to internet
        val checkNetwork = CheckNetwork()
        // # When
        val testObserver = checkNetwork.completable.test()
        // # Then
        testObserver.await().assertComplete()
    }
}