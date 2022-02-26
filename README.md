# covid-suburb-australia

[![CircleCI](https://img.shields.io/circleci/build/github/hongwei-bai/covid-suburb-au-android?logo=CircleCI&style=plastic&token=97%3Ae2%3A9f%3Ae4%3Ad6%3Aa4%3Aa3%3Ab1%3A02%3Aa4%3Aab%3Aad%3Aa5%3Ac4%3A21%3A73)](https://circleci.com/gh/hongwei-bai/covid-suburb-au-android/tree/main)
[![Unit Tests](https://img.shields.io/badge/unit%20tests-covered-83B81A?style=for-the-bedge&logo=Stitcher)](https://github.com/hongwei-bai/covid-suburb-au-android/tree/main/app/src/test/java/com/bhw/covid_suburb_au/util)
[![Instrumented Tests](https://img.shields.io/badge/instrumented%20tests-not%20applicable-EE0000?style=for-the-bedge&logo=Stitcher)](./README.md)
[![UI Tests](https://img.shields.io/badge/ui%20tests-not%20applicable-EE0000?style=for-the-bedge&logo=Stitcher)](./README.md)
[![GitHub repo size](https://img.shields.io/github/repo-size/hongwei-bai/covid-suburb-au-android?logo=GitHub)](./README.md)
[![GitHub last commit](https://img.shields.io/github/last-commit/hongwei-bai/covid-suburb-au-android?logo=Github)](./README.md)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/hongwei-bai/covid-suburb-au-android?logo=Github)](./README.md)
[![GitHub Repo stars](https://img.shields.io/github/stars/hongwei-bai/covid-suburb-au-android?logo=Github&style=plastic)](./README.md)
[![GitHub forks](https://img.shields.io/github/forks/hongwei-bai/covid-suburb-au-android?logo=GitHub&style=plastic)](./README.md)
[![YouTube Video Views](https://img.shields.io/youtube/views/apAOzJ2zh6s?style=social)](https://www.youtube.com/watch?v=apAOzJ2zh6s)


[![GitHub top language](https://img.shields.io/github/languages/top/hongwei-bai/covid-suburb-au-android?logo=Kotlin)](./README.md)
[![Coroutines](https://img.shields.io/badge/kotlin%20coroutines-in%20use-83B81A?style=for-the-bedge&logo=Kotlin)](./README.md)
[![Jetpack Compose](https://img.shields.io/badge/jetpack%20compose-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Dynamic Theming](https://img.shields.io/badge/dynamic%20theming-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Navigation](https://img.shields.io/badge/compose%20navigation-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Navigation Animation](https://img.shields.io/badge/navigation%20animation-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Compose Accompanist](https://img.shields.io/badge/compose%20accompanist-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Accompanist Placeholder](https://img.shields.io/badge/accompanist%20placeholder-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Room](https://img.shields.io/badge/jetpack%20room-in%20use-83B81A?style=for-the-bedge&logo=Jetpack%20Compose)](./README.md)
[![Hilt](https://img.shields.io/badge/hilt-in%20use-83B81A?style=for-the-bedge&logo=Google)](./README.md)
[![OkHttp Retrofit](https://img.shields.io/badge/okhttp%2Bretrofit-in%20use-83B81A?style=for-the-bedge&logo=Square)](./README.md)
[![MVVM](https://img.shields.io/badge/architecture-mvvm-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)
[![Design Patterns](https://img.shields.io/badge/design%20patterns-in%20use-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)
[![SOLID](https://img.shields.io/badge/solid%20principles-in%20use-83B81A?style=for-the-bedge&logo=Laravel)](./README.md)

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
