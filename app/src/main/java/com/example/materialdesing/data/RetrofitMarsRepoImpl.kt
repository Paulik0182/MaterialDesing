package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.utils.bpDataFormatter
import com.example.materialdesing.utils.showDebugToast
import com.example.nasaapp.model.data.MarsServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

private const val WEEK_IN_MS = (24 * 60 * 60 * 1000L) * 7

class RetrofitMarsRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi
) : MarsRepo {

    private val today: Long = Calendar.getInstance().timeInMillis - WEEK_IN_MS

    override fun getMarsLastWeek(callback: (List<MarsServerResponseData>?) -> Unit) {
        getMarsByDate(
            Calendar.getInstance().apply {
                timeInMillis = today
            },
            callback
        )
    }

    private fun getMarsByDate(
        date: Calendar,
        callback: (List<MarsServerResponseData>?) -> Unit
    ) {
        val dateServerFormat = bpDataFormatter.format(date.time)

        imdbApi.getMarsImageByDate(dateServerFormat, apiKey)
            .enqueue(object : Callback<List<MarsServerResponseData>> {
                override fun onResponse(
                    call: Call<List<MarsServerResponseData>>,
                    response: Response<List<MarsServerResponseData>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        callback.invoke(body)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<MarsServerResponseData>>, t: Throwable) {
                    context.showDebugToast(t.message.toString()) // todo только для дебага
                    callback(null)
                }
            })
    }
}