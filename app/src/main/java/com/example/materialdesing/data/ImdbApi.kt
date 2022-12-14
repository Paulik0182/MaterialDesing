package com.example.materialdesing.data

import com.example.materialdesing.domain.entity.PhotoDayDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {

    @GET("apod?api_key={apiKey}/")
    fun loadPhotoDey(
        @Path("apiKey") apiKey: String
    ): Call<PhotoDayDto>
}