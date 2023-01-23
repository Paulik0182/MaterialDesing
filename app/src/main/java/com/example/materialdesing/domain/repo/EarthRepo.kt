package com.example.materialdesing.domain.repo

import com.example.materialdesing.domain.entity.earth.EarthDto

interface EarthRepo {

    fun getEarthToday(callback: (List<EarthDto>?) -> Unit)

    fun getEarthYesterday(callback: (List<EarthDto>?) -> Unit)

    fun getEarthTwoDaysAgo(callback: (List<EarthDto>?) -> Unit)
}