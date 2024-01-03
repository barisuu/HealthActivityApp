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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserScreen(onSettingsClick: () -> Unit,
               navigateToChangePassword: () -> Unit,
               navigateToAnomaly: () -> Unit,
               activeSocket: SocketConnection) {


    val userViewModel: UserScreenViewModel = viewModel()

    val anomaly by userViewModel.anomaly.observeAsState(false)

    if(anomaly){
        navigateToAnomaly()
    }

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

        ButtonWithIcon(
            buttonText = "Change Contact Info",
            icon = Icons.Default.Menu
        ) {
            //TODO Navigating to change contact info screen.
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