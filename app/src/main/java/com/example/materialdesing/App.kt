package com.example.materialdesing

import android.app.Application
import com.example.materialdesing.data.ImdbApi
import com.example.materialdesing.data.RetrofitEarthRepoImpl
import com.example.materialdesing.data.RetrofitMarsRepoImpl
import com.example.materialdesing.data.RetrofitPhotoRepoImpl
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.domain.repo.PhotoRepo
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * API_KEY - должен быть в gradle properties (для удобства здесь)
 */
// https://api.nasa.gov/planetary/apod?api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8
// https://api.nasa.gov/planetary/apod?date=2015-09-07&api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8

// земля
// https://api.nasa.gov/EPIC/api/natural/date/2019-05-30?api_key=DEMO_KEY

private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8"

class App : Application() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    private val imdbApi: ImdbApi by lazy { retrofit.create(ImdbApi::class.java) }

    val photoRepo: PhotoRepo by lazy {
        RetrofitPhotoRepoImpl(this, API_KEY, imdbApi)
    }

    val earthRepo: EarthRepo by lazy {
        RetrofitEarthRepoImpl(this, API_KEY, imdbApi)
    }

    val marsRepo: MarsRepo by lazy {
        RetrofitMarsRepoImpl(this, API_KEY, imdbApi)
    }

    override fun onCreate() {
        super.onCreate()
        // динамические цвета - принудительное включение (это работает то 31 API)
//        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}