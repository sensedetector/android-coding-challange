package com.shiftkey.codingchallenge.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulerProvider {
    val main: Scheduler
    val computation: Scheduler
}
