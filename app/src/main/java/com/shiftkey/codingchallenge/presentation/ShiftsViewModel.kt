package com.shiftkey.codingchallenge.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.shiftkey.codingchallenge.di.MyDependendencies
import com.shiftkey.codingchallenge.domain.Shift
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.Date

class ShiftsViewModel: ViewModel() {

    // dependencies:
    private val fetchShiftsForAWeekUseCase = MyDependendencies.getFetchShiftsForAWeekUseCase()
    private val rxSchedulerProvider = MyDependendencies.getSchedulerProvider()

    private val mutableShifts: MutableLiveData<List<Shift>> = MutableLiveData<List<Shift>>().also { it.value = emptyList() }
    val shifts: LiveData<List<Shift>> = mutableShifts

    private val mutableSelectedShiftIndex = MutableLiveData<Int>()
    val selectedShift = Transformations.map(mutableSelectedShiftIndex) {
        requireNotNull(shifts.value)[it]
    }

    private var nextBatchStartDate = Date()
    private var fetching = false

    private val compositeDisposable = CompositeDisposable()

    fun fetchShifts() {
        // bail early if already fetching
        if (fetching) return

        fetching = true
        fetchShiftsForAWeekUseCase.invoke(nextBatchStartDate)
            .subscribeOn(rxSchedulerProvider.computation)
            .observeOn(rxSchedulerProvider.main)
            .doFinally { fetching = false }
            .subscribeBy (
                onSuccess = { shifts ->
                    incrementStartDate()
                    mutableShifts.value = mutableShifts.value?.toMutableList()?.apply { addAll(shifts) }
                },
                onError = {
                    Log.e("SHIFT", it.toString())
                }
            )
            .save()
    }

    fun selectShift(shiftId: Int) {
        shifts.value?.let {
            val index = it.indexOfFirst { shift -> shift.shiftId == shiftId }
            mutableSelectedShiftIndex.value = index
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    private fun incrementStartDate() {
        nextBatchStartDate = Date(nextBatchStartDate.time + WEEK_MILLIS)
    }

    private fun Disposable.save() = compositeDisposable.add(this)
}

private const val WEEK_MILLIS = 7 * 24 * 60 * 60 * 1000
