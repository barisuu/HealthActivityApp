package com.barisu.healthactivityapp

import android.Manifest.permission
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidplot.util.PixelUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PixelUtils.init(this)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this,
                arrayOf(permission.POST_NOTIFICATIONS),
                0)
        }
        setContent {
            println("Trying to start service.")
            Intent(applicationContext, SocketForegroundService::class.java).also {
                it.action = SocketForegroundService.Actions.START.toString()
                startService(it)
            }
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
                navigateToMain = {navController.navigate("admin_screen")},
                navigateToUser = {navController.navigate("user_screen")})
        }
        composable("admin_screen") {
            AdminScreen(
                onSettingsClick = { /* Handle Settings button click */ },

                navigateToRecentActivity = {navController.navigate("recent_activity")},
                navigateToSensorData = {navController.navigate("sensor_data")},
                navigateToCurrentActivity = {navController.navigate("current_activity")}
            )
        }
        composable("user_screen"){
            UserScreen(onSettingsClick = { /*TODO*/ },
                navigateToChangePassword = {navController.navigate("change_password")},
                navigateToChangeContactInfo = {navController.navigate("change_contact_info")}
            )
        }
        composable("change_password"){
            ChangePasswordScreen(
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
            CurrentActivityScreen( { navController.navigate("admin_screen") })
        }
        composable("change_contact_info"){
            ChangeContactInfoScreen (navigateToMainMenu = {navController.navigate("user_screen")})
        }
    }
}