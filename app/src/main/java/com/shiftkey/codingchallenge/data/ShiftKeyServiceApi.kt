package com.shiftkey.codingchallenge.data

import com.shiftkey.codingchallenge.domain.ShiftKeyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ShiftKeyServiceApi {
    @GET("api/v2/available_shifts")
    fun getAvailableShifts(
        @Query("address") address: String,
        @Query("type") type: String,
        @Query("start") start: String,
        @Query("end") end: String?,
    ): Single<ShiftKeyResponse>
}
