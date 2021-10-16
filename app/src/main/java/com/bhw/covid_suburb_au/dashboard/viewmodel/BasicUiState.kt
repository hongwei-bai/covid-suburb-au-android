package com.bhw.covid_suburb_au.dashboard.viewmodel

sealed interface BasicUiState {
    data class Success(
        val lastUpdate: String,
        val dataByState: CasesByStateViewObject
    ) : BasicUiState

    object Error : BasicUiState

    object Loading : BasicUiState
}

data class CasesByStateViewObject(
    val nsw: StateItemViewObject,
    val act: StateItemViewObject,
    val vic: StateItemViewObject,
    val wa: StateItemViewObject,
    val sa: StateItemViewObject,
    val nt: StateItemViewObject,
    val qld: StateItemViewObject,
    val tas: StateItemViewObject
)

data class StateItemViewObject(
    val stateCode: String,
    val stateFullName: String,
    val isMyState: Boolean = false,
    val cases: Long,
    val isHighlighted: Boolean
)
