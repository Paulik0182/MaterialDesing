package com.example.materialdesing.ui.nasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialdesing.domain.entity.PhotoDayDto
import com.example.materialdesing.domain.repo.PhotoDayDtoRepo
import com.example.materialdesing.utils.mutable

class PhotoDeyViewModel(
    private val photoDayDtoRepo: PhotoDayDtoRepo
) : ViewModel() {

    class Factory(private val photoDayDtoRepo: PhotoDayDtoRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PhotoDeyViewModel(photoDayDtoRepo) as T
        }
    }

    val inProgressLiveData: LiveData<Boolean> = MutableLiveData(false)
    val photoDeyLiveData: LiveData<PhotoDayDto> = MutableLiveData()

    init {
        if (photoDeyLiveData.value == null) {
            inProgressLiveData.mutable().postValue(true)
            photoDayDtoRepo.getPhotoDay {
                inProgressLiveData.mutable().postValue(false)
                photoDeyLiveData.mutable().postValue(it)
            }
        }
    }
}