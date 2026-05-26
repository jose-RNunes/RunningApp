package br.com.jrrunningapp.dropdown

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import br.com.jrrunningapp.text.RunningText
import br.com.jrrunningapp.text.RunningTextData
import br.com.jrrunningapp.text.RunningTextSizeType
import br.com.jrrunningapp.theme.Transparent_500

fun mockRunningActivitiesDropDownData() = listOf(
    "Today",
    "Yesterday",
    "This Week",
    "This Month"
)

@Composable
fun RunningActivitiesDropDown(
    modifier: Modifier = Modifier,
    items: List<String> = mockRunningActivitiesDropDownData(),
    onItemSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by rememberSaveable { mutableStateOf(items.firstOrNull() ?: "") }

    var rowWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Icon Rotation"
    )

    val topCornerRadius by animateDpAsState(targetValue = if (expanded) 24.dp else 100.dp, label = "Top Corner")
    val bottomCornerRadius by animateDpAsState(targetValue = if (expanded) 0.dp else 100.dp, label = "Bottom Corner")

    val backgroundColor = Transparent_500
    val borderColor = Color(0xFF232326)

    val containerShape = RoundedCornerShape(
        topStart = topCornerRadius,
        topEnd = topCornerRadius,
        bottomStart = bottomCornerRadius,
        bottomEnd = bottomCornerRadius
    )

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        Row(
            modifier = Modifier
                .onSizeChanged {
                    rowWidth = with(density) { it.width.toDp() }
                }
                .clip(containerShape)
                .background(backgroundColor)
                .border(BorderStroke(1.dp, borderColor), containerShape)
                .clickable { expanded = true }
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RunningText(
                data = RunningTextData(
                    text = selectedItem,
                    textColor = Color.White,
                    textSize = RunningTextSizeType.MEDIUM,
                    fontWeight = FontWeight.Normal
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation)
            )
        }

        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(
                extraSmall = RoundedCornerShape(
                    bottomEnd = 24.dp,
                    bottomStart = 24.dp
                )
            )
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 0.dp),
                modifier = Modifier
                    .width(rowWidth)
                    .background(backgroundColor)
                    .border(
                        BorderStroke(1.dp, borderColor),
                        RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
                    )
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            RunningText(
                                data = RunningTextData(
                                    text = item,
                                    textColor = Color.White,
                                    textSize = RunningTextSizeType.MEDIUM,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        onClick = {
                            selectedItem = item
                            expanded = false
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun RunningActivitiesDropDownPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        RunningActivitiesDropDown()
    }
}