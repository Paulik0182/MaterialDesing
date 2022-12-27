package com.example.materialdesing.ui.nasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoDtoRepo
import com.example.materialdesing.utils.mutable

class PhotoDeyViewModel(
    private val photoDtoRepo: PhotoDtoRepo
) : ViewModel() {

    class Factory(private val photoDtoRepo: PhotoDtoRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PhotoDeyViewModel(photoDtoRepo) as T
        }
    }


    val inProgressLiveData: LiveData<Boolean> = MutableLiveData(false)
    val photoDeyLiveData: LiveData<PhotoDto> = MutableLiveData()
    val yesterdayLiveData: LiveData<PhotoDto> = MutableLiveData()
    val twoDaysAgoLiveData: LiveData<PhotoDto> = MutableLiveData()

    init {
        if (photoDeyLiveData.value == null) {
            inProgressLiveData.mutable().postValue(true)
            photoDtoRepo.getPhotoDay {
                inProgressLiveData.mutable().postValue(false)
                photoDeyLiveData.mutable().postValue(it)
            }
        }
        if (yesterdayLiveData.value == null) {
            inProgressLiveData.mutable().postValue(true)
            photoDtoRepo.getPhotoYesterday {
                inProgressLiveData.mutable().postValue(false)
                yesterdayLiveData.mutable().postValue(it)
            }
        }
        if (twoDaysAgoLiveData.value == null) {
            inProgressLiveData.mutable().postValue(true)
            photoDtoRepo.getPhotoTwoDaysAgo {
                inProgressLiveData.mutable().postValue(false)
                twoDaysAgoLiveData.mutable().postValue(it)
            }
        }
    }
}