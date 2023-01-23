package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.utils.bpDataFormatter
import com.example.materialdesing.utils.showDebugToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData
import java.util.*

private const val DAY_IN_MS = 24 * 60 * 60 * 1000L

class RetrofitMarsRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi
) : MarsRepo {

    private val today: Long = Calendar.getInstance().timeInMillis

    private val yesterdayMs = today - DAY_IN_MS
    private val twoDaysAgoMs = yesterdayMs - DAY_IN_MS

    override fun getMarsToday(callback: (MarsPhotosServerResponseData?) -> Unit) {
        getMarsByDate(
            Calendar.getInstance(),
            callback
        )
    }

    override fun getMarsYesterday(callback: (MarsPhotosServerResponseData?) -> Unit) {
        getMarsByDate(
            Calendar.getInstance().apply {
                timeInMillis = yesterdayMs
            },
            callback
        )
    }

    override fun getMarsTwoDaysAgo(callback: (MarsPhotosServerResponseData?) -> Unit) {
        getMarsByDate(
            Calendar.getInstance().apply {
                timeInMillis = twoDaysAgoMs
            },
            callback
        )
    }

    private fun getMarsByDate(
        date: Calendar,
        callback: (MarsPhotosServerResponseData?) -> Unit
    ) {
        val dateServerFormat = bpDataFormatter.format(date.time)

        imdbApi.getMarsImageByDate(dateServerFormat, apiKey)
            .enqueue(object : Callback<MarsPhotosServerResponseData> {
                override fun onResponse(
                    call: Call<MarsPhotosServerResponseData>,
                    response: Response<MarsPhotosServerResponseData>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        callback.invoke(body)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
                    context.showDebugToast(t.message.toString()) // todo только для дебага
                    callback(null)
                }
            })
    }
}