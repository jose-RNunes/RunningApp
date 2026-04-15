package br.com.jrrunningapp.progress

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class RunningLineProgressData(
    val width: Dp,
    val height: Dp,
    val progress: Float
)

@Composable
fun RunningLineProgress(
    modifier: Modifier = Modifier,
    data: RunningLineProgressData
) {
    LinearProgressIndicator(
        modifier = modifier
            .width(data.width).height(data.height),
        color = Color.Green,
        trackColor = Color.LightGray,
        gapSize = (-15).dp,
        drawStopIndicator = {},
        progress = { data.progress }
    )
}