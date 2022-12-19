package com.example.materialdesing

import android.app.Application
import com.example.materialdesing.data.ImdbApi
import com.example.materialdesing.data.RetrofitPhotoDayDtoRepoImpl
import com.example.materialdesing.domain.repo.PhotoDayDtoRepo
import com.google.android.material.color.DynamicColors
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * API_KEY - должен быть в gradle properties (для удобства здесь)
 */
// https://api.nasa.gov/planetary/apod?api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8
private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8"

class App : Application() {

    val photoDayDtoRepo: PhotoDayDtoRepo by lazy {
        RetrofitPhotoDayDtoRepoImpl(this, API_KEY, imdbApi)
    }

    override fun onCreate() {
        super.onCreate()
        // динамические цвета - принудительное включение (это работает то 31 API)
        DynamicColors.applyToActivitiesIfAvailable(this)
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