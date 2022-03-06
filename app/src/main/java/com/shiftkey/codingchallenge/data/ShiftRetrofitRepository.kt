package com.shiftkey.codingchallenge.data

import com.shiftkey.codingchallenge.domain.Shift
import com.shiftkey.codingchallenge.domain.ShiftRepository
import com.shiftkey.codingchallenge.domain.ShiftResponseType
import io.reactivex.Single
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShiftRetrofitRepository(
    private val shiftKeyServiceApi: ShiftKeyServiceApi
): ShiftRepository {

    override fun getShifts(address: String, type: ShiftResponseType, start: Date, end: Date?): Single<List<Shift>> {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return shiftKeyServiceApi.getAvailableShifts(
            address,
            type.parameter,
            df.format(start),
            end?.let { df.format(it) }
        ).map { it.shifts }
    }
}
