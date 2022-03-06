package com.shiftkey.codingchallenge.domain

import io.reactivex.Single
import java.util.Date

interface ShiftRepository {
    fun getShifts(address: String, type: ShiftResponseType, start: Date, end: Date? = null): Single<List<Shift>>
}
