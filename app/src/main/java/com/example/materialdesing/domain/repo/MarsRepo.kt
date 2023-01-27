package com.example.materialdesing.domain.repo

import com.example.nasaapp.model.data.MarsServerResponseData

interface MarsRepo {

    fun getMarsLastWeek(callback: (List<MarsServerResponseData>?) -> Unit)
}