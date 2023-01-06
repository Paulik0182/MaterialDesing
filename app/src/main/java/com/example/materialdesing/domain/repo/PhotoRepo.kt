package com.example.materialdesing.domain.repo

import com.example.materialdesing.domain.entity.PhotoDto

interface PhotoRepo {

    fun getPhotoToday(callback: (PhotoDto?) -> Unit)

    fun getPhotoYesterday(callback: (PhotoDto?) -> Unit)

    fun getPhotoTwoDaysAgo(callback: (PhotoDto?) -> Unit)
}