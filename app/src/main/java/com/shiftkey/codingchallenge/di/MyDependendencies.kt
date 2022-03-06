package com.shiftkey.codingchallenge.di

import com.google.gson.GsonBuilder
import com.shiftkey.codingchallenge.data.ShiftKeyResponseDeserializer
import com.shiftkey.codingchallenge.data.ShiftKeyServiceApi
import com.shiftkey.codingchallenge.data.ShiftRetrofitRepository
import com.shiftkey.codingchallenge.domain.FetchShiftsForAWeekUseCase
import com.shiftkey.codingchallenge.domain.ShiftKeyResponse
import com.shiftkey.codingchallenge.domain.ShiftRepository
import com.shiftkey.codingchallenge.util.RxProductionSchedulerProvider
import com.shiftkey.codingchallenge.util.RxSchedulerProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Temporary class for custom DI service. It can be replace with another DI library in future.
 */
object MyDependendencies {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://staging-app.shiftkey.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(createGsonConverter())
        .also {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            it.client(client)
        }
        .build()

    fun getShiftKeyServiceApi(): ShiftKeyServiceApi = retrofit.create(ShiftKeyServiceApi::class.java)

    fun getShiftRepository(): ShiftRepository = ShiftRetrofitRepository(getShiftKeyServiceApi())

    fun getSchedulerProvider(): RxSchedulerProvider = RxProductionSchedulerProvider()

    fun getFetchShiftsForAWeekUseCase() = FetchShiftsForAWeekUseCase(getShiftRepository())

    private fun createGsonConverter(): Converter.Factory {
        val gsonBuilder = GsonBuilder().registerTypeAdapter(ShiftKeyResponse::class.java, ShiftKeyResponseDeserializer())
        return GsonConverterFactory.create(gsonBuilder.create())
    }
}
