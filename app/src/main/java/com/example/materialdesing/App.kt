package com.example.materialdesing

import android.app.Application
import com.example.materialdesing.data.ImdbApi
import com.example.materialdesing.data.RetrofitPhotoDtoRepoImpl
import com.example.materialdesing.domain.repo.PhotoDtoRepo
import com.example.materialdesing.utils.bpDataFormatter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * API_KEY - должен быть в gradle properties (для удобства здесь)
 */
// https://api.nasa.gov/planetary/apod?api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8
// https://api.nasa.gov/planetary/apod?date=2015-09-07&api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8
private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8"
private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class App : Application() {

    private val today: Long = Calendar.getInstance().timeInMillis

    private val yesterday = today - DAY_IN_MS
    private val twoDaysAgo = yesterday - DAY_IN_MS

    private val yesterdayString = bpDataFormatter.format(yesterday).toString()
    private val twoDaysAgoString = bpDataFormatter.format(twoDaysAgo).toString()

    val photoDtoRepo: PhotoDtoRepo by lazy {
        RetrofitPhotoDtoRepoImpl(this, API_KEY, imdbApi, yesterdayString, twoDaysAgoString)
    }

    override fun onCreate() {
        super.onCreate()
        // динамические цвета - принудительное включение (это работает то 31 API)
//        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    private val imdbApi: ImdbApi by lazy { retrofit.create(ImdbApi::class.java) }
}