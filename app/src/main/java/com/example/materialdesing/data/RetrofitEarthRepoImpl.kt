package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.utils.bpDataFormatter
import com.example.materialdesing.utils.showDebugToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class RetrofitEarthRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi
) : EarthRepo {

    private val today: Long = Calendar.getInstance().timeInMillis - (DAY_IN_MS * 365)

    private val yesterdayMs = today - (DAY_IN_MS * 365)
    private val twoDaysAgoMs = yesterdayMs - (DAY_IN_MS * 365)

    override fun getEarthToday(callback: (List<EarthDtoItem>?) -> Unit) {
        getEarthByDate(
            Calendar.getInstance().apply {
                timeInMillis = today
            },
            callback
        )
    }

    override fun getEarthYesterday(callback: (List<EarthDtoItem>?) -> Unit) {
        getEarthByDate(
            Calendar.getInstance().apply {
                timeInMillis = yesterdayMs
            },
            callback
        )
    }

    override fun getEarthTwoDaysAgo(callback: (List<EarthDtoItem>?) -> Unit) {
        getEarthByDate(
            Calendar.getInstance().apply {
                timeInMillis = twoDaysAgoMs
            },
            callback
        )
    }

    private fun getEarthByDate(
        date: Calendar,
        callback: (List<EarthDtoItem>?) -> Unit
    ) {
        val dateServerFormat = bpDataFormatter.format(date.time)

        imdbApi.epicPhotoByDay(dateServerFormat, apiKey)
            .enqueue(object : Callback<List<EarthDtoItem>> {
                override fun onResponse(
                    call: Call<List<EarthDtoItem>>,
                    response: Response<List<EarthDtoItem>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        callback.invoke(body)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<EarthDtoItem>>, t: Throwable) {
                    context.showDebugToast(t.message.toString()) // todo только для дебага
                    callback(null)
                }
            })
    }
}