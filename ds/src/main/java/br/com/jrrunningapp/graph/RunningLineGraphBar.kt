package br.com.jrrunningapp.graph

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import kotlin.text.toFloat

@Composable
fun RunningLineGraphBar(
    modifier: Modifier = Modifier,
    data: List<LineGraphData>,
    maxItems: Int = 8,
    animationDurationMs: Int = 800
) {
    val density = LocalDensity.current
    val spacingPx = remember { with(density) { 8.dp.toPx() } }
    val horizontalPaddingPx = remember { with(density) { 16.dp.toPx() } }
    val topPaddingPx = remember { with(density) { 20.dp.toPx() } }
    val bottomPaddingPx = remember { with(density) { 20.dp.toPx() } }
    val strokeWidthPx = remember { with(density) { 1.dp.toPx() } }
    val cornerRadiusPx = remember { with(density) { 10.dp.toPx() } }

    var animatedData by remember { mutableStateOf(mockLineGraphData().map {
        it.copy(y = 0)
    }) }

    val displayedData = remember(data, maxItems) { data.take(maxItems) }
    val maxBarValue = remember(displayedData) {
        displayedData.maxOfOrNull { it.y }?.toFloat() ?: 1f
    }

    val pathEffect = remember { PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) }

    val animatedHeights = animatedData.mapIndexed { index, item ->
        key(index) {
            val animatedHeight by animateFloatAsState(
                targetValue = item.y.toFloat(),
                animationSpec = tween(durationMillis = animationDurationMs),
                label = "barHeight_$index"
            )
            animatedHeight
        }
    }

    LaunchedEffect(Unit) {
        animatedData = data
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val usableWidth = size.width - horizontalPaddingPx * 2
            val usableHeight = size.height - topPaddingPx - bottomPaddingPx
            val sumSpacing = (displayedData.size - 1) * spacingPx
            val barWidthPx = (usableWidth - sumSpacing) / displayedData.size

            val numLines = 5
            val lineSpacing = usableHeight / (numLines - 1)
            repeat(numLines) { i ->
                val y = topPaddingPx + (i * lineSpacing)
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(x = horizontalPaddingPx, y = y),
                    end = Offset(x = size.width - horizontalPaddingPx, y = y),
                    strokeWidth = strokeWidthPx,
                    pathEffect = pathEffect
                )
            }

            displayedData.forEachIndexed { index, item ->
                val barHeight = (animatedHeights[index] / maxBarValue) * usableHeight

                val x = horizontalPaddingPx + (index * (barWidthPx + spacingPx))

                val y = size.height - bottomPaddingPx - barHeight

                drawRoundRect(
                    color = item.color,
                    topLeft = Offset(x = x, y = y),
                    size = Size(width = barWidthPx, height = barHeight),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RunningLineGraphBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RunningLineGraphBar(data = mockLineGraphData())
    }
}
