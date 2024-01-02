package com.barisu.healthactivityapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


// Button composable used in mainscreen.
@Composable
fun ButtonWithIcon(buttonText: String, icon: ImageVector, onClick: () -> Unit){
    // Surface to create background
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = myButtonBGColor,
        shape = RoundedCornerShape(20)

    ) {
        // Button with transparent backgroudn and rounded corners.
        Button(
            modifier= Modifier.fillMaxWidth(),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(20)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                // Leftmost icon
                Icon(icon,contentDescription = null,modifier = Modifier.padding(8.dp), tint = Color.Black)

                Spacer(modifier= Modifier.width(8.dp))
                // Button text
                Text(text = buttonText,
                    color= Color.Black,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 16.dp))
            }
        }
    }
}