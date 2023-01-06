package com.example.materialdesing.data

import com.example.materialdesing.domain.entity.PhotoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImdbApi {

    @GET("planetary/apod")
    fun loadPhotoByDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PhotoDto>
}