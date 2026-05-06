package br.com.jrrunninapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jrrunninapp.ui.theme.RunningAppTheme
import br.com.jrrunningapp.R
import br.com.jrrunningapp.R.drawable
import br.com.jrrunningapp.cards.RunningCard
import br.com.jrrunningapp.cards.RunningCardChallenge
import br.com.jrrunningapp.cards.RunningCardChallengeData
import br.com.jrrunningapp.cards.RunningCardData
import br.com.jrrunningapp.cards.RunningCardGoals
import br.com.jrrunningapp.cards.RunningCardGoalsData
import br.com.jrrunningapp.cards.RunningCardSizeType
import br.com.jrrunningapp.cards.RunningLastActivityData
import br.com.jrrunningapp.graph.RunningLineGraph
import br.com.jrrunningapp.graph.RunningLineGraphBar
import br.com.jrrunningapp.graph.mockLineGraphData
import br.com.jrrunningapp.graph.mockLineRunningLineGraphData
import br.com.jrrunningapp.grid.RunningFlowGrid
import br.com.jrrunningapp.grid.RunningGrid
import br.com.jrrunningapp.text.RunningTextData
import br.com.jrrunningapp.text.RunningTextSizeType
import kotlinx.coroutines.delay
import kotlin.text.toInt
import kotlin.times

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunningAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                vertical = innerPadding.calculateTopPadding(),
                                horizontal = 16.dp
                            )
                    ) {

                        item {
                            val data = mockLineGraphData()

                            RunningLineGraphBar(data = data)
                        }

                        item {
                            val mockGrids = mockRunningCardData()

                            RunningFlowGrid(items = mockGrids)
                        }
                        item {
                            RunningCardChallenge(
                                modifier = Modifier.padding(16.dp),
                                data = RunningCardChallengeData(
                                    icon = drawable.ic_challenge,
                                    backgroundIconColor = Color.LightGray,
                                    bigText = RunningTextData(
                                        text = "Run 3 days this week",
                                        textColor = Color.White,
                                        textSize = RunningTextSizeType.LARGE,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    backgroundColor = Color.Gray,
                                    progress = 0.4f,
                                    smallText = RunningTextData(
                                        text = "Run at least 20 mins on 3 separate da..",
                                        textColor = Color.White,
                                        textSize = RunningTextSizeType.SMALL,
                                        fontWeight = FontWeight.Normal
                                    ),
                                    challengeDate = RunningTextData(
                                        text = "1 May - 7 May",
                                        textColor = Color.White,
                                        textSize = RunningTextSizeType.SMALL,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            )
                        }

                        item {
                            RunningCardGoals(
                                modifier = Modifier.padding(16.dp),
                                data = RunningCardGoalsData(
                                    icon = drawable.ic_goals,
                                    backgroundIconColor = Color.LightGray,
                                    bigText = RunningTextData(
                                        text = "Run 10 km this week",
                                        textColor = Color.White,
                                        textSize = RunningTextSizeType.LARGE,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    backgroundColor = Color.Gray,
                                    progress = 0.7f,
                                    lastActivities = listOf(
                                        RunningLastActivityData(
                                            icon = drawable.ic_paces,
                                            text = "8.5 km/h",
                                            textColor = Color.White
                                        ),
                                        RunningLastActivityData(
                                            icon = drawable.ic_pin_distance,
                                            text = "8.0km",
                                            textColor = Color.White
                                        ),
                                        RunningLastActivityData(
                                            icon = drawable.ic_timer_line,
                                            text = "1h",
                                            textColor = Color.White
                                        )
                                    ),
                                    lastActivity = RunningTextData(
                                        text = "Last activity: 3 km on 3 May",
                                        textColor = Color.White,
                                        textSize = RunningTextSizeType.SMALL,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            )
                        }

                        item {
                            val mockLineGraphData = mockLineRunningLineGraphData()
                            RunningLineGraph(data = mockLineGraphData)
                        }
                    }
                }
            }
        }
    }

    fun mockRunningCardData() = listOf(
        RunningCardData(
            icon = drawable.ic_pin_distance,
            type = RunningCardSizeType.BIG,
            smallText = RunningTextData(
                text = "Distance",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "5.2 km",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        ),
        RunningCardData(
            icon = drawable.ic_paces,
            type = RunningCardSizeType.DEFAULT,
            smallText = RunningTextData(
                text = "Avg Pace",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "5:25 km",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        ),
        RunningCardData(
            icon = drawable.ic_steps,
            type = RunningCardSizeType.DEFAULT,
            smallText = RunningTextData(
                text = "Steps",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "7,500",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        ),
        RunningCardData(
            icon = drawable.ic_timer_line,
            type = RunningCardSizeType.BIG,
            smallText = RunningTextData(
                text = "Time",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "00:56:42",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        ),
        RunningCardData(
            icon = drawable.ic_pulse_health,
            type = RunningCardSizeType.DEFAULT,
            smallText = RunningTextData(
                text = "Heart Heat",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "124 bpm",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        ),
        RunningCardData(
            icon = drawable.ic_calories,
            type = RunningCardSizeType.DEFAULT,
            smallText = RunningTextData(
                text = "Calories",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            ),
            bigText = RunningTextData(
                text = "500 kcal",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            ),
            backgroundColor = Color.Gray
        )
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RunningAppTheme {
        Greeting("Android")
    }
}