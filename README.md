# covid-suburb-australia

[![Build Status](https://hongwei-test1.top:8444/buildStatus/icon?job=covid-app-android)](https://hongwei-test1.top:8444/job/covid-app-android/)

<img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/feature.png" width="512" height="250" />

The app is aiming to help people in Australia keeping safe from COVID-19.

By choosing your currently living suburb and following your most visit suburbs, you may closely being kept updated about new cases every day in these suburbs.

#### Interest points in terms of Modern Android Development(MAD) skills:
- [x] Android Jetpack: Compose
- [x] Jetpack Compose Theming and dark mode ready
- [x] Splash screen (for Android 12/previous versions)
- [x] Android Jetpack: Navigation
- [x] Android Jetpack: Room
- [x] Hilt
- [x] OkHttp + Retrofit
- [x] Kotlin Coroutines

The architecture of the App would use MVVM, with layers:
- View
- View Model
- Data(Repository/Data Source)

#### How data flows to ui?

In this project, I am using a best practice so far recommended from Google samples.

Data layer(repository) implements sequential functions and returns data wrapped by a Resource<T> type.

ViewModel implements sequential functions too and post these wrapped data to ui via LiveData.

Benefits are:

1. [x] Wrapped data are stateful, mapping different ui states easily(loading, success, error etc.)

1. [x] In ViewModel, ui triggers use case alike behaviours easily.

Dashboard

<img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_dash.jpg" width="300" height="600" /> <img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_dash_dark.jpg" width="300" height="600" />

MapView

<img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_map.jpg" width="300" height="600" /> <img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_map_tablet.png" width="800" height="600" />

Settings

<img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_settings.jpg" width="300" height="600" /> <img src="https://raw.githubusercontent.com/hongwei-bai/covid-suburb-au-android/main/screenshots/Screenshot_settings_dark.jpg" width="300" height="600" />



COVID-19 data is coming from NSW government website:

[Site Analytics | Usage By Dataset](https://data.nsw.gov.au/data/site-usage/dataset)

[NSW COVID-19 cases by location and likely source of infection](https://data.nsw.gov.au/data/dataset/nsw-covid-19-cases-by-location-and-likely-source-of-infection)


#### Related projects

backend project repository:
[hongwei-bai/covid-application-service](https://github.com/hongwei-bai/covid-application-service)

Authentication service(backend) repository:
[hongwei-bai/application-service-authentication](https://github.com/hongwei-bai/application-service-authentication)

Stay safe!
