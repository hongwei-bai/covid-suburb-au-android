package com.bhw.covid_suburb_au.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R

@SuppressLint("MissingPermission")
@Composable
fun CovidSuburbMap() {
    Text(
        text = stringResource(id = R.string.coming_soon),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        textAlign = TextAlign.Center
    )
//    val currentLocation = viewModel.location.collectAsState()

//    val coroutineScope = rememberCoroutineScope()
//
//    val mapView = rememberMapViewWithLifecycle()
//
//
//    AndroidView({ mapView }) { mapView ->
//        coroutineScope.launch {
//            val map = mapView.awaitMap()
//
//            map.isMyLocationEnabled = true
//            map.uiSettings.isZoomControlsEnabled = true
//
////            currentLocation.value?.let {
////                val position = com.google.android.libraries.maps.model.LatLng(it.latitude, it.longitude)
////                map.moveCamera(com.google.android.libraries.maps.CameraUpdateFactory.newLatLngZoom(position,  viewModel.getZoomLevel()))
////            }
//
//            map.setOnCameraIdleListener{
//                val cameraPosition = map.cameraPosition
////                val location = Location(cameraPosition.target.latitude, cameraPosition.target.longitude)
////                viewModel.setLocation(location)
////                viewModel.setZoomLevel(cameraPosition.zoom)
//            }
//
////            for (busStop in stops) {
////                busStop.latitude?.let { latitude ->
////                    busStop.longitude?.let { longitude ->
////
////                        val busStopLocation = com.google.android.libraries.maps.model.LatLng(latitude, longitude)
////
////                        val icon = bitmapDescriptorFromVector(mapView.context, R.drawable.ic_stop, R.color.mapMarkerGreen)
////                        val markerOptions = com.google.android.libraries.maps.model.MarkerOptions()
////                            .title(busStop.shortName)
////                            .position(busStopLocation)
////                            .icon(icon)
////
////                        val marker = map.addMarker(markerOptions)
////                        marker.tag = busStop
////                    }
////                }
////            }
//        }
//    }
}