package android.util

object Log {
    @JvmStatic
    fun d(tag: String?, msg: String?): Int {
        println(msg)
        return 0
    }

    @JvmStatic
    fun i(tag: String?, msg: String?): Int {
        println(msg)
        return 0
    }

    @JvmStatic
    fun w(tag: String?, msg: String?): Int {
        println(msg)
        return 0
    }

    @JvmStatic
    fun e(tag: String?, msg: String?): Int {
        println(msg)
        return 0
    }
}