package com.shiftkey.codingchallenge.domain

import io.reactivex.Single
import java.util.Date

class FetchShiftsForAWeekUseCase(
    private val shiftRepository: ShiftRepository
) {
    operator fun invoke(startDate: Date): Single<List<Shift>> {
        return shiftRepository.getShifts(
            DALLAS_ADDRESS,
            ShiftResponseType.WEEK,
            startDate
        )
    }
}

private const val DALLAS_ADDRESS = "Dallas, TX"
