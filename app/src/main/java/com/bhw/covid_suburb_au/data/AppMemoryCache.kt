package com.bhw.covid_suburb_au.data

import com.bhw.covid_suburb_au.data.network.model.NewsResponse
import javax.inject.Inject

class AppMemoryCache @Inject constructor() {
    var news: NewsResponse? = null
}