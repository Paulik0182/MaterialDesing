package com.example.materialdesing.data

import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData

interface ImdbApi {

    @GET("planetary/apod")
    fun loadPhotoByDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PhotoDto>

    // земля
// https://api.nasa.gov/EPIC/api/natural/date/2022-01-22?api_key=3YPr0zvE0A1uw2nauTAX3W89WkXfKTS4vOvDTbB8
// https://epic.gsfc.nasa.gov/archive/natural/' + year + '/' + month + '/' + day + '/jpg/' + 'IMAGE_NAME+ '.jpg';
    @GET("EPIC/api/natural/date/{date}")
    fun epicPhotoByDay(
        @Path("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<List<EarthDtoItem>>

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