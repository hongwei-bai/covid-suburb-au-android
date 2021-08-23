package com.bhw.covid_suburb_au.view.main

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.view.theme.CovidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mobileCovidRepository: MobileCovidRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CovidTheme {
                SystemUiController()

                NavComposeApp()
            }
        }
    }
}

@Composable
fun SystemUiController() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val systemBarColor = MaterialTheme.colors.primary

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )

        // setStatusBarsColor() and setNavigationBarsColor() also exist
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("main") {
            MainScreen()
        }
    }
}