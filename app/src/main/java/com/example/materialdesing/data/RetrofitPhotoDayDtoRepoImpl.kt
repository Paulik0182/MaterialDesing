package com.example.materialdesing.data

import android.content.Context
import com.example.materialdesing.domain.entity.PhotoDayDto
import com.example.materialdesing.domain.repo.PhotoDayDtoRepo

class RetrofitPhotoDayDtoRepoImpl(
    private val context: Context,
    private val apiKey: String,
    private val imdbApi: ImdbApi,
) : PhotoDayDtoRepo {

    override fun getPhotoDay(callback: (PhotoDayDto?) -> Unit) {
        TODO("Not yet implemented")
    }
}