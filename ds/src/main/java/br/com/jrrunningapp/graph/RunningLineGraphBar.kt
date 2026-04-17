package br.com.jrrunningapp.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.PathEffect

@Composable
fun RunningLineGraphBar(modifier: Modifier = Modifier, data: List<LineGraphData>) {
    val barWidthDp = 34.dp
    val spacingDp = 10.dp
    val horizontalPaddingDp = 16.dp
    val topPaddingDp = 20.dp
    val bottomPaddingDp = 20.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val maxBarValue = data.maxOfOrNull { it.y }?.toFloat() ?: 1f
            val topPaddingPx = topPaddingDp.toPx()
            val bottomPaddingPx = bottomPaddingDp.toPx()
            val usableHeight = size.height - topPaddingPx - bottomPaddingPx

            val barWidthPx = barWidthDp.toPx()
            val spacingPx = spacingDp.toPx()
            val horizontalPaddingPx = horizontalPaddingDp.toPx()

            val numLines = 5
            val lineSpacing = usableHeight / (numLines - 1)
            repeat(numLines) { i ->
                val y = topPaddingPx + (i * lineSpacing)
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(x = horizontalPaddingPx, y = y),
                    end = Offset(x = size.width - horizontalPaddingPx, y = y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }

            data.forEachIndexed { index, item ->
                val barHeight = (item.y / maxBarValue) * usableHeight

                val x = horizontalPaddingPx + (index * (barWidthPx + spacingPx))

                val y = size.height - bottomPaddingPx - barHeight

                drawRoundRect(
                    color = item.color,
                    topLeft = Offset(x = x, y = y),
                    size = Size(width = barWidthPx, height = barHeight),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
        }
    }
}
