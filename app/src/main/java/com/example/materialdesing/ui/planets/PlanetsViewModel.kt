package com.example.materialdesing.ui.planets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.utils.mutable
import com.example.nasaapp.model.data.MarsServerResponseData

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


    val earthLastWeekLiveData: LiveData<List<EarthDtoItem>> = MutableLiveData()
    val earthTwoWeeksAgoLiveData: LiveData<List<EarthDtoItem>> = MutableLiveData()
    val earthThreeWeeksAgoLiveData: LiveData<List<EarthDtoItem>> = MutableLiveData()

    val marsLiveData: LiveData<List<MarsServerResponseData>> = MutableLiveData()

    init {
        if (earthLastWeekLiveData.value == null) {
            earthRepo.getEarthLastWeek {
                earthLastWeekLiveData.mutable().postValue(it)
            }
        }
        if (earthTwoWeeksAgoLiveData.value == null) {
            earthRepo.getEarthTwoWeeksAgo {
                earthTwoWeeksAgoLiveData.mutable().postValue(it)
            }
        }

        if (earthThreeWeeksAgoLiveData.value == null) {
            earthRepo.getEarthThreeWeeksAgo {
                earthThreeWeeksAgoLiveData.mutable().postValue(it)
            }
        }

        if (marsLiveData.value == null) {
            marsRepo.getMarsLastWeek {
                marsLiveData.mutable().postValue(it)
            }
        }
    }
}