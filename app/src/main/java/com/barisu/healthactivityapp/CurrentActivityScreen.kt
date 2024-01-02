package com.barisu.healthactivityapp

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CurrentActivityScreen(
    navigateToMainMenu: () -> Unit,
    activeSocket: SocketConnection
){

    var currentActivity = "Temp Activity"
    var currentCertainty = 0

Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        color = Color(0xFF006B5F),
        shape = RoundedCornerShape(20)

    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(380.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Color(0xFFCCE7E0))) {
                Text(text = "Detected Activity:")
                // TODO Implementing connection to get these values
                Text(text = "$currentActivity   $currentCertainty%")
            }
        }

    }

    Button(onClick = { navigateToMainMenu() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))) {
        Text(text = "Back to Main Page", maxLines = 1)
    }
}

}