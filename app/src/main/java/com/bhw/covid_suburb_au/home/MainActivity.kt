package com.bhw.covid_suburb_au.home

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.ui.theme.CovidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var auPostcodeRepository: AuPostcodeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CovidTheme {
                SystemUiController()

                if (SDK_INT >= Build.VERSION_CODES.S) {
                    MainScreen()
                } else {
                    NavComposeApp()
                }
            }
        }
    }
}

@Composable
fun SystemUiController() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val systemBarColor = MaterialTheme.colors.surface

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = systemBarColor,
            darkIcons = useDarkIcons
        )

        // setStatusBarsColor() and setNavigationBarsColor() also exist
    }
}

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