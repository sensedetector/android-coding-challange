package com.shiftkey.codingchallenge.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.RxThreadFactory
import io.reactivex.schedulers.Schedulers

class RxProductionSchedulerProvider: RxSchedulerProvider {
    override val main: Scheduler = AndroidSchedulers.mainThread()
    override val computation: Scheduler = Schedulers.computation()
}
