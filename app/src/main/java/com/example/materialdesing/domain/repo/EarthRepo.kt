package com.example.materialdesing.domain.repo

import com.example.materialdesing.domain.entity.earth.EarthDtoItem

interface EarthRepo {

    fun getEarthToday(callback: (List<EarthDtoItem>?) -> Unit)

    fun getEarthYesterday(callback: (List<EarthDtoItem>?) -> Unit)

    fun getEarthTwoDaysAgo(callback: (List<EarthDtoItem>?) -> Unit)
}