package com.tminus1010.tmcommonkotlin.rx

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseTest {
    init {
        // Without this, unit tests will complain about AndroidSchedulers.mainThread()
        // Maybe only one of these is necessary? Maybe there is a better way..?
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }
}
