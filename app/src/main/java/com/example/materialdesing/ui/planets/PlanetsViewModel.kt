package com.example.materialdesing.ui.planets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialdesing.domain.entity.earth.EarthDto
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.utils.mutable
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData

class PlanetsViewModel(
    private val earthRepo: EarthRepo,
    private val marsRepo: MarsRepo
) : ViewModel() {

    class Factory(private val earthRepo: EarthRepo, private val marsRepo: MarsRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlanetsViewModel(earthRepo, marsRepo) as T
        }
    }


    val photoDeyLiveData: LiveData<List<EarthDto>> = MutableLiveData()
    val yesterdayLiveData: LiveData<List<EarthDto>> = MutableLiveData()
    val twoDaysAgoLiveData: LiveData<List<EarthDto>> = MutableLiveData()

    val marsLiveData: LiveData<MarsPhotosServerResponseData> = MutableLiveData()

    init {
        if (photoDeyLiveData.value == null) {
            earthRepo.getEarthToday {
                photoDeyLiveData.mutable().postValue(it)
            }
        }
        if (yesterdayLiveData.value == null) {
            earthRepo.getEarthYesterday {
                yesterdayLiveData.mutable().postValue(it)
            }
        }
        if (marsLiveData.value == null) {
            marsRepo.getMarsToday {
                marsLiveData.mutable().postValue(it)
            }
        }
    }
}