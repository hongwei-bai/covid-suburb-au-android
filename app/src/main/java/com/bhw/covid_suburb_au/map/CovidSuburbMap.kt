package com.bhw.covid_suburb_au.map

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.AppConfigurations.Map.sydneyLocation
import com.bhw.covid_suburb_au.util.PermissionState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.ranges.contains


@SuppressLint("MissingPermission")
@Composable
fun CovidSuburbMap(fineLocation: PermissionState) {
    val viewModel = hiltViewModel<MapViewModel>()

    val homeLocationUiState = viewModel.homeLocation.observeAsState().value

    val currentLocationUiState = viewModel.currentLocation.observeAsState().value

    val lgaList = viewModel.lgaWithCases.observeAsState().value

    val hasLocationPermission by fineLocation.hasPermission.collectAsState()
    if (!hasLocationPermission) {
        fineLocation.launchPermissionRequest()
    }

    val coroutineScope = rememberCoroutineScope()
    val mapView1 = rememberMapViewWithLifecycle()

    AndroidView({ mapView1 }) { mapView ->
        coroutineScope.launch {
            val map = mapView.awaitMap()

            map.isMyLocationEnabled = hasLocationPermission
            map.uiSettings.isZoomControlsEnabled = true

            val currentLocation = if (homeLocationUiState is MapLocationUiState.Location) {
                LatLng(
                    homeLocationUiState.latLng.latitude,
                    homeLocationUiState.latLng.longitude
                )
            } else {
                sydneyLocation
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f))

            lgaList?.forEach { lga ->
                val lgaLocation = LatLng(lga.location.latitude, lga.location.longitude)
                val icon = bitmapDescriptorFromVector(lga.newCases)
                val markerOptions = MarkerOptions()
                    .icon(icon)
                    .position(lgaLocation)
                    .title("${lga.postcode} ${lga.name}")
                val marker = map.addMarker(markerOptions)
                marker.tag = lga.postcode
            }
        }
    }
}

fun bitmapDescriptorFromVector(number: Long): BitmapDescriptor? {
    val text = "+$number"
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    textPaint.textSize = 35f
    textPaint.color = Color.BLUE
    textPaint.textAlign = Paint.Align.CENTER

    val baseline: Float = -textPaint.ascent() + 10f
    val width = (textPaint.measureText(text) + 0.5f) + 20f
    val height = (baseline + textPaint.descent() + 0.5f) + 10f

    return Bitmap.createBitmap(width.roundToInt(), height.roundToInt(), Bitmap.Config.ARGB_8888)?.let { bitmap ->
        val background = Paint()
        background.color = when (number) {
            in 1..3 -> Color.GREEN
            in 4..10 -> Color.YELLOW
            in 11..30 -> Color.RED
            else -> Color.RED
        }

        val canvas = Canvas(bitmap)
        canvas.drawRoundRect(0f, 0f, width, height, 16f, 16f, background)
        canvas.drawText(text, width / 2, baseline, textPaint)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}