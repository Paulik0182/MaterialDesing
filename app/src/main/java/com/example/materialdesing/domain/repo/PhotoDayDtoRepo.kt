package com.example.materialdesing.domain.repo

import com.example.materialdesing.domain.entity.PhotoDayDto

interface PhotoDayDtoRepo {

    fun getPhotoDay(callback: (PhotoDayDto?) -> Unit)
}