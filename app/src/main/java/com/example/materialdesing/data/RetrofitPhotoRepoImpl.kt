package com.example.materialdesing.data

import android.annotation.SuppressLint
import android.content.Context
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoRepo
import com.example.materialdesing.utils.showDebugToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class RetrofitPhotoRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi
) : PhotoRepo {

    @SuppressLint("SimpleDateFormat")
    private var apiDateFormatter = SimpleDateFormat("yyyy-MM-dd")

    private val today: Long = Calendar.getInstance().timeInMillis

    private val yesterdayMs = today - DAY_IN_MS
    private val twoDaysAgoMs = yesterdayMs - DAY_IN_MS

    override fun getPhotoToday(callback: (PhotoDto?) -> Unit) {
        getPhotoByDate(
            Calendar.getInstance(),
            callback
        )
    }

    override fun getPhotoYesterday(callback: (PhotoDto?) -> Unit) {
        getPhotoByDate(
            Calendar.getInstance().apply {
                timeInMillis = yesterdayMs
            },
            callback
        )
    }

    override fun getPhotoTwoDaysAgo(callback: (PhotoDto?) -> Unit) {
        getPhotoByDate(
            Calendar.getInstance().apply {
                timeInMillis = twoDaysAgoMs
            },
            callback
        )
    }

    private fun getPhotoByDate(
        date: Calendar,
        callback: (PhotoDto?) -> Unit
    ) {
        val dateServerFormat = apiDateFormatter.format(date.time)
        imdbApi.loadPhotoByDay(dateServerFormat, apiKey).enqueue(object : Callback<PhotoDto> {
            override fun onResponse(call: Call<PhotoDto>, response: Response<PhotoDto>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.invoke(body)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PhotoDto>, t: Throwable) {
                context.showDebugToast(t.message.toString()) // todo только для дебага
                callback(null)
            }
        })
    }
}