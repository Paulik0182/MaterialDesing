package com.example.materialdesing.domain.entity.earth


import com.google.gson.annotations.SerializedName

data class DscovrJ2000PositionX(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("z")
    val z: Double
)