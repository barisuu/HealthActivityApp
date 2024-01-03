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
    val socketConnection = SocketConnection()

    val isLoggedIn = remember { mutableStateOf(false) }
    // TODO If isLoggedIn is true startDestination is main_screen
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(onLoginSuccess = { isLoggedIn.value = true },
                navigateToMain = {navController.navigate("admin_screen")},
                navigateToUser = {navController.navigate("user_screen")},
                socketConnection)
        }
        composable("admin_screen") {
            AdminScreen(
                onSettingsClick = { /* Handle Settings button click */ },

                navigateToRecentActivity = {navController.navigate("recent_activity")},
                navigateToSensorData = {navController.navigate("sensor_data")},
                navigateToCurrentActivity = {navController.navigate("current_activity")},
                socketConnection
            )
        }
        composable("user_screen"){
            UserScreen(onSettingsClick = { /*TODO*/ },
                navigateToChangePassword = {navController.navigate("change_password")},
            )
        }
        composable("change_password"){
            ChangePasswordScreen(
                changePasswordViewModel = viewModel(),
                navigateToMainMenu = {navController.navigate("user_screen")}
            )
        }
        composable("recent_activity"){
            RecentActivityScreen {
                navController.navigate("admin_screen")
            }
        }
        composable("sensor_data"){
            SensorDataScreen {
                navController.navigate("admin_screen")
            }
        }
        composable("current_activity"){
            CurrentActivityScreen( {
                navController.navigate("admin_screen")
            },
                socketConnection)
        }
    }
}