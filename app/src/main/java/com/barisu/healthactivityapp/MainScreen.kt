package com.barisu.healthactivityapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val myButtonBGColor = Color(0xFFE5E8E6)

@Composable
fun MainScreen(onSettingsClick: () -> Unit,
               navigateToChangePassword: () -> Unit,
               navigateToRecentActivity: () -> Unit,
               navigateToSensorData: () -> Unit,
               navigateToCurrentActivity: () -> Unit,
               activeSocket: SocketConnection){

    // Column to hold settings button.
    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start)
    {
        // Setings button. TODO implement a pop-up box for settings.
        IconButton(onClick = {onSettingsClick()}) {
            Icon(Icons.Default.Settings,contentDescription = null)

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "MSAD-HMS",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Recent activity screen button.
        ButtonWithIcon(
            buttonText = "Show Recent Activity Graph",
            icon = Icons.Default.Menu
        ) {
            navigateToRecentActivity() // Navigating to recent activity screen.
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Current activity screen button.
        ButtonWithIcon(
            buttonText = "Show Current Activity",
            icon = Icons.Default.Menu
        ) {
            navigateToCurrentActivity() // Navigating to current activity.
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sensor data screen button.
        ButtonWithIcon(
            buttonText = "Show Sensor Data",
            icon = Icons.Default.Menu
        ){
            navigateToSensorData() // Navigating to sensor data screen.
        }

        Spacer(modifier = Modifier.height(32.dp))

        ButtonWithIcon(
            buttonText = "Change Password",
            icon = Icons.Default.Menu
        ) {
            navigateToChangePassword() // Navigating to change password screen.

        }

    }
}

