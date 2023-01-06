package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoRepo
import com.example.materialdesing.utils.toastMake
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitPhotoRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi,
    private val yesterdayString: String,
    private val twoDaysAgoString: String,
) : PhotoRepo {

    override fun getPhotoToday(callback: (PhotoDto?) -> Unit) {
        imdbApi.loadPhotoDey(apiKey).enqueue(object : Callback<PhotoDto> {
            override fun onResponse(call: Call<PhotoDto>, response: Response<PhotoDto>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.invoke(body)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PhotoDto>, t: Throwable) {
                context.toastMake(t.message.toString())
            }
        })
    }

    override fun getPhotoYesterday(callback: (PhotoDto?) -> Unit) {
        imdbApi.loadPhotoYesterday(yesterdayString, apiKey).enqueue(object : Callback<PhotoDto> {
            override fun onResponse(call: Call<PhotoDto>, response: Response<PhotoDto>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.invoke(body)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PhotoDto>, t: Throwable) {
                context.toastMake(t.message.toString())
            }
        })
    }

    override fun getPhotoTwoDaysAgo(callback: (PhotoDto?) -> Unit) {
        imdbApi.loadPhotoTwoDaysAgo(twoDaysAgoString, apiKey).enqueue(object : Callback<PhotoDto> {
            override fun onResponse(call: Call<PhotoDto>, response: Response<PhotoDto>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.invoke(body)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PhotoDto>, t: Throwable) {
                context.toastMake(t.message.toString())
            }
        })
    }
}