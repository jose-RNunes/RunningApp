package br.com.jrrunningapp.graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jrrunningapp.R
import br.com.jrrunningapp.cards.RunningLastActivityData
import kotlin.collections.maxOfOrNull

data class RunningLineGraphData(
    val distance: List<Int>,
    val week: List<String>,
    val activities: List<RunningLineGraphActivityData>
)

data class RunningLineGraphActivityData(
    val y: Int,
    val activities: List<RunningLastActivityData>
)

fun mockLineRunningLineGraphData() = RunningLineGraphData(
    distance = listOf(2, 4, 5, 7, 10),
    week = listOf("M", "T", "W", "T", "F", "S", "S"),
    activities = listOf(
        RunningLineGraphActivityData(
            y = 10,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.5 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 30,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 5,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 40,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 60,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 40,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        ),
        RunningLineGraphActivityData(
            y = 90,
            activities = listOf(
                RunningLastActivityData(
                    icon = R.drawable.ic_paces,
                    text = "8.0 km/h",
                    textColor = Color.White
                )
            )
        )
    )
)

@Composable
fun RunningLineGraph(modifier: Modifier = Modifier, data: RunningLineGraphData) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color(0xFFF5F5F5))
    ) {

        val animProgress = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            animProgress.animateTo(1f, tween(3000))
        }

        val topPaddingDp = 20.dp
        val bottomPaddingDp = 20.dp

        val textMeasurer = rememberTextMeasurer()

        val textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val topPaddingPx = topPaddingDp.toPx()
            val bottomPaddingPx = bottomPaddingDp.toPx()
            val leftPaddingPx = 48.dp.toPx()
            val usableHeight = size.height - topPaddingPx - bottomPaddingPx

            val textSpacing = (usableHeight / (data.distance.size - 1)) - 20.dp.toPx()
            data.distance.forEachIndexed { index, day ->
                val yOffset = (usableHeight - 32.dp.toPx()) - (index * textSpacing)

                drawText(
                    textMeasurer = textMeasurer,
                    text = day.toString(),
                    style = textStyle,
                    topLeft = Offset(x = 16.dp.toPx(), y = yOffset),
                )
            }

            val usableWidth = size.width - leftPaddingPx
            val weekTextSpacing = (usableWidth / data.week.size - 1)

            data.week.forEachIndexed { index, week ->
                val xOffset = leftPaddingPx + (index * weekTextSpacing)
                drawText(
                    textMeasurer = textMeasurer,
                    text = week,
                    style = textStyle,
                    topLeft = Offset(x = xOffset, y = usableHeight),
                )
            }

            val maxDataValue =
                (usableHeight - 32.dp.toPx()) - ((data.distance.size - 1) * textSpacing)



            val points = data.activities.mapIndexed { index, activity ->
                val x = leftPaddingPx + 5.dp.toPx() + (index * weekTextSpacing)
                val yFraction = activity.y.toFloat() / maxDataValue
                val y = usableHeight - (yFraction * usableHeight)
                Offset(x, y)
            }

            val path = Path()
            if(points.isNotEmpty()) {
                path.moveTo(points[0].x, points[0].y)

                points.forEach { point ->
                    path.lineTo(point.x, point.y)
                }
            }
            val last = points.last()
            val fakeX = last.x + (weekTextSpacing / 2f)
            val fakeY = last.y
            path.lineTo(fakeX, fakeY)

            clipRect(right = size.width * animProgress.value) {
                drawPath(
                    path = path,
                    color = Color.Green,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}

@Composable
fun RunningLienCurveGraph(modifier: Modifier = Modifier, data: RunningLineGraphData) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color(0xFFF5F5F5))
    ) {

        val animProgress = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            animProgress.animateTo(1f, tween(3000))
        }

        val topPaddingDp = 20.dp
        val bottomPaddingDp = 20.dp

        val textMeasurer = rememberTextMeasurer()

        val textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val topPaddingPx = topPaddingDp.toPx()
            val bottomPaddingPx = bottomPaddingDp.toPx()
            val leftPaddingPx = 48.dp.toPx()
            val usableHeight = size.height - topPaddingPx - bottomPaddingPx

            val textSpacing = (usableHeight / (data.distance.size - 1)) - 20.dp.toPx()
            data.distance.forEachIndexed { index, day ->
                val yOffset = (usableHeight - 32.dp.toPx()) - (index * textSpacing)

                drawText(
                    textMeasurer = textMeasurer,
                    text = day.toString(),
                    style = textStyle,
                    topLeft = Offset(x = 16.dp.toPx(), y = yOffset),
                )
            }

            val usableWidth = size.width - leftPaddingPx
            val weekTextSpacing = (usableWidth / data.week.size - 1)

            data.week.forEachIndexed { index, week ->
                val xOffset = leftPaddingPx + (index * weekTextSpacing)
                drawText(
                    textMeasurer = textMeasurer,
                    text = week,
                    style = textStyle,
                    topLeft = Offset(x = xOffset, y = usableHeight),
                )
            }

            val maxDataValue =
                (usableHeight - 32.dp.toPx()) - ((data.distance.size - 1) * textSpacing)

            val points = data.activities.mapIndexed { index, activity ->
                val x = leftPaddingPx + 5.dp.toPx() + (index * weekTextSpacing)
                val yFraction = activity.y.toFloat() / maxDataValue
                val y = usableHeight - (yFraction * usableHeight)
                Offset(x, y)
            }

            val path = Path()
            if (points.isNotEmpty()) {
                path.moveTo(points[0].x, points[0].y)

                for (i in 0 until points.size - 1) {
                    val p1 = points[i]
                    val p2 = points[i + 1]

                    val controlPoint1 = Offset(p1.x + (p2.x - p1.x) / 2f, p1.y)
                    val controlPoint2 = Offset(p1.x + (p2.x - p1.x) / 2f, p2.y)

                    path.cubicTo(
                        controlPoint1.x, controlPoint1.y,
                        controlPoint2.x, controlPoint2.y,
                        p2.x, p2.y
                    )
                }

                val last = points.last()
                val prev = points.getOrElse(points.size - 2) { last }

                val fakeX = last.x + (weekTextSpacing / 2f)
                val fakeY = last.y + (last.y - prev.y) * 0.05f // Continues the trend slightly

                val p1 = last
                val p2 = Offset(fakeX, fakeY)
                val controlPoint1 = Offset(p1.x + (p2.x - p1.x) / 2f, p1.y)
                val controlPoint2 = Offset(p1.x + (p2.x - p1.x) / 2f, p2.y)
                path.cubicTo(
                    controlPoint1.x,
                    controlPoint1.y,
                    controlPoint2.x,
                    controlPoint2.y,
                    p2.x,
                    p2.y
                )
            }

            clipRect(right = size.width * animProgress.value) {
                drawPath(
                    path = path,
                    color = Color(0xFF6200EE),
                    style = Stroke(
                        width = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}