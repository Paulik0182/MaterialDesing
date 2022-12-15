package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.entity.PhotoDayDto
import com.example.materialdesing.domain.repo.PhotoDayDtoRepo
import com.example.materialdesing.utils.toastMake
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitPhotoDayDtoRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi,
) : PhotoDayDtoRepo {

    override fun getPhotoDay(callback: (PhotoDayDto?) -> Unit) {
        imdbApi.loadPhotoDey(apiKey).enqueue(object : Callback<PhotoDayDto> {
            override fun onResponse(call: Call<PhotoDayDto>, response: Response<PhotoDayDto>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.invoke(body)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PhotoDayDto>, t: Throwable) {
                context.toastMake(t.message.toString())
            }
        })
    }
}