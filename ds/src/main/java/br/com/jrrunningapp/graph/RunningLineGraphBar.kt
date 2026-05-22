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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.jrrunningapp.theme.Primary_100
import br.com.jrrunningapp.theme.Primary_200
import br.com.jrrunningapp.theme.Primary_300
import br.com.jrrunningapp.theme.Primary_50
import br.com.jrrunningapp.theme.Warning_300
import br.com.jrrunningapp.theme.Warning_500

data class RunningLineGraphBarData(
    val activities: List<RunningLineGraphBarActivityData>
)

enum class RunningLineGraphBarActivityStatusType(
    val color: Color,
    val status: String
) {
    REST(Warning_300, "Rest"),
    RUN(Primary_200, "Run")
}

data class RunningLineGraphBarActivityData(
    val color: Color,
    val y: Int,
    val time: String
)

fun mockLineGraphData() = listOf(
    RunningLineGraphBarActivityData(Warning_500, 10, "06:00"),
    RunningLineGraphBarActivityData(Warning_300, 20, "06:00"),
    RunningLineGraphBarActivityData(Primary_100, 30, "06:20"),
    RunningLineGraphBarActivityData(Primary_200, 40, "06:35"),
    RunningLineGraphBarActivityData(Warning_300, 10, "06:35"),
    RunningLineGraphBarActivityData(Primary_100, 20, "06:45"),
    RunningLineGraphBarActivityData(Primary_200, 35, "07:00"),
    RunningLineGraphBarActivityData(Primary_300, 40, "07:15")
)


@Composable
fun RunningLineGraphBar(
    modifier: Modifier = Modifier,
    data: List<RunningLineGraphBarActivityData>,
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

    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = Color.Gray, fontSize = 12.sp)

    var animatedData by remember {
        mutableStateOf(mockLineGraphData().map {
            it.copy(y = 0)
        })
    }

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

            data.forEachIndexed { index, data ->
                val x = horizontalPaddingPx + (index * (barWidthPx + spacingPx))
                drawText(
                    textMeasurer = textMeasurer,
                    text = data.time,
                    style = textStyle,
                    topLeft = Offset(x = x, y = size.height - 18.dp.toPx()),
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
