package com.bhw.covid_suburb_au.map

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.util.PermissionState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch


@SuppressLint("MissingPermission")
@Composable
fun CovidSuburbMap(fineLocation: PermissionState) {
    val viewModel = hiltViewModel<MapViewModel>()

    val homeLocationUiState = viewModel.homeLocation.observeAsState().value

    val hasLocationPermission by fineLocation.hasPermission.collectAsState()

    if (!hasLocationPermission) {
        fineLocation.launchPermissionRequest()
    }

    val coroutineScope = rememberCoroutineScope()

    val mapView = rememberMapViewWithLifecycle()

    AndroidView({ mapView }) { mapView ->
        coroutineScope.launch {
            val map = mapView.awaitMap()

            map.isMyLocationEnabled = hasLocationPermission
            map.uiSettings.isZoomControlsEnabled = true

            Log.d("bbbb", "homeLocationUiState: $homeLocationUiState")
            val currentLocation = if (homeLocationUiState is MapLocationUiState.Location) {
                LatLng(
                    homeLocationUiState.latLng.latitude,
                    homeLocationUiState.latLng.longitude
                )
            } else if (hasLocationPermission) {
//                val myLocation: Location = map.myLocation
//                LatLng(myLocation.latitude, myLocation.longitude)
                LatLng(-33.8678, 151.2073)
            } else {
                LatLng(-33.8678, 151.2073)
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f))

//            map.setOnCameraIdleListener {
//                val cameraPosition = map.cameraPosition
//                val location = Location(cameraPosition.target.latitude, cameraPosition.target.longitude)
//                viewModel.setLocation(location)
//                viewModel.setZoomLevel(cameraPosition.zoom)
//            }
        }
    }
}