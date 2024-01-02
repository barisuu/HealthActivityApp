package com.barisu.healthactivityapp

import SocketForegroundService
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidplot.util.PixelUtils

class MainActivity : ComponentActivity() {
    val socketConnection = SocketConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val healthApp = application as HealthApp
        PixelUtils.init(this)
        if (!healthApp.socketForegroundService.isServiceRunning()) {
            val serviceIntent = Intent(this, SocketForegroundService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp(){
    val navController = rememberNavController()

    val isLoggedIn = remember { mutableStateOf(false) }
    // TODO If isLoggedIn is true startDestination is main_screen
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(onLoginSuccess = { isLoggedIn.value = true },
                navigateToMain = {navController.navigate("main_screen")})
        }
        composable("main_screen") {
            MainScreen(
                onSettingsClick = { /* Handle Settings button click */ },
                navigateToChangePassword = {navController.navigate("change_password")},
                navigateToRecentActivity = {navController.navigate("recent_activity")},
                navigateToSensorData = {navController.navigate("sensor_data")},
                navigateToCurrentActivity = {navController.navigate("current_activity")}
            )
        }
        composable("change_password"){
            ChangePasswordScreen(
                changePasswordViewModel = viewModel(),
                navigateToMainMenu = {navController.navigate("main_screen")}
            )
        }
        composable("recent_activity"){
            RecentActivityScreen {
                navController.navigate("main_screen")
            }
        }
        composable("sensor_data"){
            SensorDataScreen {
                navController.navigate("main_screen")
            }
        }
        composable("current_activity"){
            CurrentActivityScreen( {
                navController.navigate("main_screen")
            })
        }
    }
}