package com.example.materialdesing.domain.repo

import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData

interface MarsRepo {

    fun getMarsToday(callback: (MarsPhotosServerResponseData?) -> Unit)

    fun getMarsYesterday(callback: (MarsPhotosServerResponseData?) -> Unit)

    fun getMarsTwoDaysAgo(callback: (MarsPhotosServerResponseData?) -> Unit)
}