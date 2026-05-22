package br.com.jrrunningapp.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class RunningArcGraphData(
    val progress: Float,
    val colors: List<Color>,
    val backgroundColor: Color,
    val strokeWidth: Dp = 14.dp
)

fun mockRunningArcGraphData(
    progress: Float = 50f,
    colors: List<Color> = listOf(Color.Green, Color.Red),
    backgroundColor: Color = Color.Gray
) = RunningArcGraphData(
    progress = progress,
    colors = colors,
    backgroundColor = backgroundColor
)

fun mockRunningStats() =  listOf(
    mockRunningArcGraphData(
        progress = 85f,
        colors = listOf(Color(0xFFD0FF71), Color(0xFF81FF00)),
        backgroundColor = Color(0xFF1A2E00) // Verde bem escuro/preto
    ),
    mockRunningArcGraphData(
        progress = 65f,
        colors = listOf(Color(0xFFFFB344), Color(0xFFE91E63)),
        backgroundColor = Color(0xFF2E1500) // Laranja bem escuro
    ),
    mockRunningArcGraphData(
        progress = 45f,
        colors = listOf(Color(0xFFFAFF00), Color(0xFFFFC107)),
        backgroundColor = Color(0xFF2E2D00) // Amarelo bem escuro
    )
)

@Composable
fun RunningDashboardStats(
    modifier: Modifier = Modifier,
    stats: List<RunningArcGraphData>
) {
    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        stats.forEachIndexed { index, data ->
            val paddingMultiplier = index * 20

            RunningArcGraph(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingMultiplier.dp),
                data = data
            )
        }
    }
}

@Composable
fun RunningArcGraph(
    modifier: Modifier = Modifier,
    data: RunningArcGraphData) {
    val gradientColors = remember(data.colors) {
        if (data.colors.size > 1) {
            data.colors + data.colors.first()
        } else {
            data.colors
        }
    }

    val brush = remember(gradientColors) {
        if (gradientColors.isNotEmpty()) {
            Brush.sweepGradient(colors = gradientColors)
        } else {
            SolidColor(Color.Transparent)
        }
    }



    Canvas(modifier.graphicsLayer()) {
        val strokeWidthPx = data.strokeWidth.toPx()

        val arcSize = size.minDimension - strokeWidthPx

        val topLeftOffset = Offset(
            x = (size.width - arcSize) / 2,
            y = (size.height - arcSize) / 2
        )

        drawArc(
            color = data.backgroundColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeftOffset,
            size = Size(arcSize, arcSize),
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )

        rotate(degrees = 270f) {
            drawArc(
                brush = brush,
                startAngle = 0f,
                sweepAngle = (data.progress / 100f) * 360f,
                useCenter = false,
                topLeft = topLeftOffset,
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

@Preview(showBackground = true, name = "Progresso 50%")
@Composable
fun RunningArcGraphPreview() {
    val data = mockRunningArcGraphData(
        progress = 50f,
        colors = listOf(Color(0xFF00FF00), Color(0xFF004400)),
        backgroundColor = Color.LightGray.copy(alpha = 0.5f)
    )

    RunningArcGraph(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        data = data
    )
}

@Preview(showBackground = true)
@Composable
fun RunningDashboardStatsPreview() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunningDashboardStats(
            modifier = Modifier.size(180.dp),
            stats = mockRunningStats()
        )
    }
}





