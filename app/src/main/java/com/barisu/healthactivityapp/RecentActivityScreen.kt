// RecentActivityScreen.kt

package com.barisu.healthactivityapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PointLabelFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot

@Composable
fun RecentActivityScreen(navigateToMainMenu: () -> Unit) {
    val context = LocalContext.current
    val recentActivityViewModel: RecentActivityViewModel = viewModel()
    val graphData by recentActivityViewModel.graphData.observeAsState()

    // Launch effect to fetch graph data when the screen is first composed
    LaunchedEffect(Unit) {
        recentActivityViewModel.getGraphData(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Centered graph data or loading text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Take available space to center content
            contentAlignment = Alignment.Center // Center content within the Box
        ) {
            graphData?.let { data ->
                if (data.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(300.dp) // Adjust the size as needed
                    ) {
                        GraphPlot(data = data)
                    }
                } else {
                    Text(text = "Loading graph data...")
                }
            } ?: run {
                Text(text = "Loading graph data...")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navigateToMainMenu() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
        ) {
            Text(text = "Back to Main Page")
        }
    }
}

@Composable
fun GraphPlot(data: FloatArray) {
    val series = SimpleXYSeries(data.toList(), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Activity Data")
    val lineAndPointFormatter = LineAndPointFormatter(null, -16776961, null, PointLabelFormatter(-16776961))

    AndroidView(factory = { context ->
        val plot = XYPlot(context, "Recent Activity Graph")
        plot.addSeries(series, lineAndPointFormatter)
        plot.redraw() // Ensure the plot is redrawn
        plot
    })
}
