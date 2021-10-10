package com.bhw.covid_suburb_au.dashboard.viewobject

sealed interface DashboardViewState

data class DashboardSuccessState(
    val lastUpdate: String,
    val dataByState: CasesByStateViewObject
) : DashboardViewState

object DashboardErrorState : DashboardViewState

object DashboardLoadingState : DashboardViewState

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
    val cases: Int,
    val isHighlighted: Boolean
)
