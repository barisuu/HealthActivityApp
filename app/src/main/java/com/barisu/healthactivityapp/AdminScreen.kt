package com.barisu.healthactivityapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

val myButtonBGColor = Color(0xFFE5E8E6)

@Composable
fun AdminScreen(onSettingsClick: () -> Unit,
                navigateToRecentActivity: () -> Unit,
                navigateToSensorData: () -> Unit,
                navigateToCurrentActivity: () -> Unit) {

    val adminScreenViewModel: AdminScreenViewModel = viewModel()
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
    // Column to hold settings button.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    )
    {
        // Setings button. TODO implement a pop-up box for settings.
        IconButton(onClick = { onSettingsClick() }) {
            Icon(Icons.Default.Settings, contentDescription = null)

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MSAD-HMS",
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
            //adminScreenViewModel.updateActivity(activeSocket,"curAct")
            navigateToCurrentActivity() // Navigating to current activity.
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sensor data screen button.
        ButtonWithIcon(
            buttonText = "Show Sensor Data",
            icon = Icons.Default.Menu
        ) {
            navigateToSensorData() // Navigating to sensor data screen.
        }

    }
        // Box to hold disconnect button.
    Box(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)){

        Row(modifier = Modifier.align(Alignment.BottomEnd))
        {
            // Disconnect button.
            ButtonWithIcon(modifier=Modifier.width(240.dp).height(64.dp),bgColor = Color(0xFFE85354), buttonText = "Disconnect", icon = Icons.Default.Close) {
                adminScreenViewModel.disconnect(context)
            }
        }
    }

    }
}

