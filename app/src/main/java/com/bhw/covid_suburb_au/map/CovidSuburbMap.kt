package com.bhw.covid_suburb_au.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.util.PermissionState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("MissingPermission")
@Composable
fun CovidSuburbMap(fineLocation: PermissionState) {
    val viewModel = hiltViewModel<MapViewModel>()

    val homeLocationUiState = viewModel.homeLocation.observeAsState().value

    val lgaList = viewModel.lgaWithCases.observeAsState().value

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

fun textAsBitmap(text: String, textSize: Float, textColor: Int): Bitmap? {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = textSize
    paint.color = textColor

    val baseline: Float = -paint.ascent()
    val width = (paint.measureText(text) + 0.5f).roundToInt()
    val height = (baseline + paint.descent() + 0.5f).roundToInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    return image
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