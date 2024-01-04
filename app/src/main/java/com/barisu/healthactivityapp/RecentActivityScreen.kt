package com.barisu.healthactivityapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot

@Composable
fun RecentActivityScreen(navigateToMainMenu: () -> Unit) {

    val context = LocalContext.current
    val recentActivityViewModel: RecentActivityViewModel = viewModel()
    val series = remember { mutableStateOf(SimpleXYSeries("Activity Data")) }
    lateinit var plot : XYPlot

    val key = remember { mutableStateOf(true)}

    if(key.value){
        LaunchedEffect(true){
            recentActivityViewModel.getGraphData(context)
            key.value = false
        }
    }

    // Value for androidplot's plot format
    val lineAndPointFormatter = LineAndPointFormatter(
        null, // Line color, represents Color.BLUE in AndroidPlot
        -16776961,
        null,
        null// No point labels
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Android view to generate and display the AndroidPlot XY Plot.
        AndroidView(
            factory = { plotContext ->
                plot = XYPlot(plotContext,"Recent Activity Graph")
                plot.addSeries(series.value, lineAndPointFormatter)
                plot
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        // Side effect to update the graph at fixed intervals using the timer.
        //LaunchedEffect(series) {
            //val counter = series.value.size()
           // series.value.addLast(counter, Math.random() * 100)
          //  plot.redraw()
         //
        //}

        Spacer(modifier = Modifier.height(16.dp))

        // Button to return to the main page
        Button(
            onClick = { navigateToMainMenu() },
            modifier = Modifier.fillMaxWidth().align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
        ) {
            Text(text = "Back to Main Page")
        }
    }
}


