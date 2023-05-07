package com.tminus1010.tmcommonkotlin.testoverridelog

import android.util.Log
import com.tminus1010.tmcommonkotlin.test.TestParamsA
import com.tminus1010.tmcommonkotlin.test.toDynamicTests
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class LogTest {
    @TestFactory
    fun testMsgAndThrowable(): List<DynamicTest> {
        return listOf(
            MsgAndThrowableTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenThrowable = Exception("Oh no!"),
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgAndThrowableTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenThrowable = Exception("Oh no!"),
                givenLogLevel = Log.DEBUG,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgAndThrowableTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenThrowable = Exception("Oh no!"),
                givenLogLevel = Log.ERROR,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgAndThrowableTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenThrowable = null,
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgAndThrowableTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenThrowable = Exception("Oh no!"),
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.INFO,
            ),
        ).toDynamicTests { (tag, msg, tr, logLevel, logLevelSetting) ->
            // # When
            TestOverrideLogConfig.logLevel = logLevelSetting
            when (logLevel) {
                Log.INFO -> Log.i(tag, msg, tr)
                Log.DEBUG -> Log.d(tag, msg, tr)
                Log.ERROR -> Log.e(tag, msg, tr)
                Log.VERBOSE -> Log.v(tag, msg, tr)
                Log.WARN -> Log.w(tag, msg, tr)
            }
            // # Then
            // Manually read the println
        }
    }

    data class MsgAndThrowableTestParams(
        val givenTag: String?,
        val givenMsg: String?,
        val givenThrowable: Throwable?,
        val givenLogLevel: Int,
        val givenLogLevelSetting: Int,
    ) : TestParamsA()

    @TestFactory
    fun testMsg(): List<DynamicTest> {
        return listOf(
            MsgTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenLogLevel = Log.DEBUG,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenLogLevel = Log.ERROR,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage".repeat(1000),
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.VERBOSE,
            ),
            MsgTestParams(
                givenTag = "TAG",
                givenMsg = "FakeMessage",
                givenLogLevel = Log.VERBOSE,
                givenLogLevelSetting = Log.INFO,
            ),
        ).toDynamicTests { (tag, msg, logLevel, logLevelSetting) ->
            // # When
            TestOverrideLogConfig.logLevel = logLevelSetting
            when (logLevel) {
                Log.INFO -> Log.i(tag, msg)
                Log.DEBUG -> Log.d(tag, msg)
                Log.ERROR -> Log.e(tag, msg)
                Log.VERBOSE -> Log.v(tag, msg)
                Log.WARN -> Log.w(tag, msg)
            }
            // # Then
            // Manually read the println
        }
    }

    data class MsgTestParams(
        val givenTag: String?,
        val givenMsg: String,
        val givenLogLevel: Int,
        val givenLogLevelSetting: Int,
    ) : TestParamsA()
}