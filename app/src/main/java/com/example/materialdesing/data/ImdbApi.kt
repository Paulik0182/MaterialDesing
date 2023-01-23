package com.example.materialdesing.data

import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.entity.earth.EarthDto
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData

interface ImdbApi {

    @GET("planetary/apod")
    fun loadPhotoByDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PhotoDto>

    // земля
// https://api.nasa.gov/EPIC/api/natural/date/2019-05-30?api_key=DEMO_KEY

    @GET("EPIC/api/natural/date/")
    fun epicPhotoByDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<List<EarthDto>>

    @GET("EPIC/api/natural/date/")
    fun marsPhotoByDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<EarthDtoItem>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}