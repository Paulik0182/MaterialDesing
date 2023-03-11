package com.example.materialdesing.data

import android.content.Context
import android.util.Log
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.utils.bpDataFormatter
import com.example.materialdesing.utils.showDebugToast
import com.example.nasaapp.model.data.MarsServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData
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
            .enqueue(object : Callback<MarsPhotosServerResponseData> {
                override fun onResponse(
                    call: Call<MarsPhotosServerResponseData>,
                    response: Response<MarsPhotosServerResponseData>
                ) {
                    val body = response.body()
                    Log.d("@@@", "onResponse() called with: call = $call, response = $response")
                    if (response.isSuccessful && body != null) {
                        callback.invoke(body.photos)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
                    context.showDebugToast(t.message.toString()) // todo только для дебага
                    Log.d("@@@", "onResponse() called with: call = $call, response = $t")
                    callback(null)
                }
            })
    }
}