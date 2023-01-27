package com.example.materialdesing.domain.repo

import com.example.materialdesing.domain.entity.earth.EarthDtoItem

interface EarthRepo {

    fun getEarthLastWeek(callback: (List<EarthDtoItem>?) -> Unit)

    fun getEarthTwoWeeksAgo(callback: (List<EarthDtoItem>?) -> Unit)

    fun getEarthThreeWeeksAgo(callback: (List<EarthDtoItem>?) -> Unit)
}