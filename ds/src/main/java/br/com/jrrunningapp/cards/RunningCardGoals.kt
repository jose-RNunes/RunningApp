package br.com.jrrunningapp.cards

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.jrrunningapp.R
import br.com.jrrunningapp.progress.RunningLineProgress
import br.com.jrrunningapp.progress.RunningLineProgressData
import br.com.jrrunningapp.text.RunningText
import br.com.jrrunningapp.text.RunningTextData
import br.com.jrrunningapp.text.RunningTextSizeType

open class RunningCardGoalsAndChallengeData(
    @DrawableRes open val icon: Int,
    open val backgroundIconColor: Color,
    open val bigText: RunningTextData,
    open val backgroundColor: Color,
    open val progress: Float = 0f
)

data class RunningCardGoalsData(
    @DrawableRes override val icon: Int,
    override val backgroundIconColor: Color,
    override val bigText: RunningTextData,
    override val backgroundColor: Color,
    override val progress: Float,
    val lastActivities: List<RunningLastActivityData>,
    val lastActivity: RunningTextData,
) : RunningCardGoalsAndChallengeData(
    icon = icon,
    backgroundIconColor = backgroundIconColor,
    bigText = bigText,
    backgroundColor = backgroundColor,
    progress = progress
)

data class RunningLastActivityData(
    @DrawableRes val icon: Int,
    val text: String,
    val textColor: Color
) {
    fun buildRunningTextData() = RunningTextData(
        text = text,
        textColor = textColor,
        textSize = RunningTextSizeType.SMALL,
        fontWeight = FontWeight.Normal
    )
}

data class RunningCardChallengeData(
    @DrawableRes override val icon: Int,
    override val backgroundIconColor: Color,
    override val bigText: RunningTextData,
    override val backgroundColor: Color,
    override val progress: Float,
    val smallText: RunningTextData,
    val challengeDate: RunningTextData
) : RunningCardGoalsAndChallengeData(
    icon = icon,
    backgroundIconColor = backgroundIconColor,
    bigText = bigText,
    backgroundColor = backgroundColor,
    progress = progress
)

@Composable
fun RunningCardGoalsAndChallenge(
    modifier: Modifier = Modifier,
    data: RunningCardGoalsAndChallengeData,
    title: @Composable RowScope.() -> Unit,
    description: @Composable RowScope.() -> Unit,
    bottomInfo: @Composable RowScope.() -> Unit
) {

    var heightDp by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    var targetProgress by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(data.progress) {
        targetProgress = data.progress
    }

    val progressAnimation by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 2000),
        label = "progress"
    )

    Row(
        modifier = modifier
            .background(
                color = data.backgroundColor, shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onGloballyPositioned { coordinates ->
                heightDp = with(density) { coordinates.size.height.toDp() }
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(heightDp - 16.dp)
                .background(
                    color = data.backgroundIconColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp),
            painter = painterResource(id = data.icon),
            contentDescription = null
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row {
                title()
            }
            Row {
                description()
            }

            RunningLineProgress(
                data = RunningLineProgressData(
                    width = 100.dp,
                    height = 4.dp,
                    progress = progressAnimation
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomInfo()
            }
        }
    }
}

@Composable
fun RunningCardChallenge(
    modifier: Modifier = Modifier,
    data: RunningCardChallengeData
) {
    RunningCardGoalsAndChallenge(
        modifier = modifier,
        data = data,
        title = {
            RunningText(modifier = Modifier.fillMaxWidth(), data = data.bigText)
        },
        description = {
            RunningText(modifier = Modifier.fillMaxWidth(), data = data.smallText)
        },
        bottomInfo = {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_calendar_challenge),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(2.dp))

            RunningText(modifier = Modifier.fillMaxWidth(), data = data.challengeDate)
        }
    )
}

@Composable
fun RunningCardGoals(
    modifier: Modifier = Modifier,
    data: RunningCardGoalsData
) {
    RunningCardGoalsAndChallenge(
        modifier = modifier,
        data = data,
        title = {
            RunningText(modifier = Modifier.fillMaxWidth(), data = data.bigText)
        },
        description = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                data.lastActivities.forEach { activity ->
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = activity.icon),
                        contentDescription = null
                    )

                    RunningText(data = activity.buildRunningTextData())
                }
            }
        },
        bottomInfo = {
            RunningText(modifier = Modifier.fillMaxWidth(), data = data.lastActivity)
        }
    )
}