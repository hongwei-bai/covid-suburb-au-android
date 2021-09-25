package com.bhw.covid_suburb_au.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.viewmodel.SplashViewModel


@Composable
fun SplashScreen(navController: NavController) {
    val splashViewModel = hiltViewModel<SplashViewModel>()
    splashViewModel.preload {
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
            launchSingleTop = true
        }
    }

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_clock))
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                speed = 1.0f,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.requiredSizeIn(maxWidth = 240.dp, maxHeight = 240.dp).scale(0.75f)
            )
            Text(
                text = stringResource(id = R.string.splash_loading_text),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}