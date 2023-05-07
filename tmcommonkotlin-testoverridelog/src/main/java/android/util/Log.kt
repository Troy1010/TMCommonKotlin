package android.util

import com.tminus1010.tmcommonkotlin.testoverridelog.TestOverrideLogConfig

object Log {
    const val ASSERT = 7
    const val DEBUG = 3
    const val ERROR = 6
    const val INFO = 4
    const val VERBOSE = 2
    const val WARN = 5

    // TODO: I'm not sure how to best use [tag].
    private fun printMsg(tag: String?, msg: String?, logLevel: Int): Int {
        if (logLevel >= TestOverrideLogConfig.logLevel)
            println(msg)
        return 0 // TODO: I'm not sure what is supposed to be returned.
    }

    // TODO: I'm not sure how to best use [tag].
    private fun printThrowable(tag: String?, tr: Throwable?, logLevel: Int): Int {
        if (logLevel >= TestOverrideLogConfig.logLevel)
            println(tr?.stackTraceToString() ?: "")
        return 0 // TODO: I'm not sure what is supposed to be returned.
    }

    // TODO: I'm not sure how to best use [tag].
    private fun printMsgAndThrowable(tag: String?, msg: String?, tr: Throwable?, logLevel: Int): Int {
        if (logLevel >= TestOverrideLogConfig.logLevel)
            println("$msg ${tr?.stackTraceToString() ?: ""}")
        return 0 // TODO: I'm not sure what is supposed to be returned.
    }

    @JvmStatic
    fun v(tag: String?, msg: String): Int {
        return printMsg(tag, msg, VERBOSE)
    }

    @JvmStatic
    fun v(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, VERBOSE)
    }

    @JvmStatic
    fun d(tag: String?, msg: String): Int {
        return printMsg(tag, msg, DEBUG)
    }

    @JvmStatic
    fun d(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, DEBUG)
    }

    @JvmStatic
    fun i(tag: String?, msg: String): Int {
        return printMsg(tag, msg, INFO)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, INFO)
    }

    @JvmStatic
    fun w(tag: String?, msg: String): Int {
        return printMsg(tag, msg, WARN)
    }

    @JvmStatic
    fun w(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, WARN)
    }

    @JvmStatic
    external fun isLoggable(var0: String?, var1: Int): Boolean // TODO: Idk what this is or how to mock it properly.

    @JvmStatic
    fun w(tag: String?, tr: Throwable?): Int {
        return printThrowable(tag, tr, WARN)
    }

    @JvmStatic
    fun e(tag: String?, msg: String): Int {
        return printMsg(tag, msg, ERROR)
    }

    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, ERROR)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?): Int {
        return printMsg(tag, msg, 100)
    }

    @JvmStatic
    fun wtf(tag: String?, tr: Throwable): Int {
        return printThrowable(tag, tr, 100)
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?, tr: Throwable?): Int {
        return printMsgAndThrowable(tag, msg, tr, 100)
    }

    @JvmStatic
    fun getStackTraceString(tr: Throwable?): String {
        return tr?.stackTraceToString() ?: ""
    }

    @JvmStatic
    fun println(priority: Int, tag: String?, msg: String): Int { // TODO: I'm not sure if this mock is behaving correctly.
        println(msg)
        return 0 // TODO: I'm not sure what to return here
    }
}