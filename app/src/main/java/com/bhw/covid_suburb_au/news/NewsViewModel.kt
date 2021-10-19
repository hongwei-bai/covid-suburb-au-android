package com.bhw.covid_suburb_au.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.data.MobileCovidRepository
import com.bhw.covid_suburb_au.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    val title = MutableLiveData<String?>()

    val news = MutableLiveData<String?>()

    val link = MutableLiveData<String?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val resource = mobileCovidRepository.getNews()
            when (resource) {
                is Resource.Success -> {
                    title.postValue(resource.data.title)
                    news.postValue(resource.data.news)
                    link.postValue(resource.data.link)
                }
                else -> {
                    title.postValue(null)
                    news.postValue(null)
                    link.postValue(null)
                }
            }
        }
    }
}