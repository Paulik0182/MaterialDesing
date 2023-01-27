package com.example.materialdesing.domain.entity.earth


import com.google.gson.annotations.SerializedName

data class CentroidCoordinates(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)