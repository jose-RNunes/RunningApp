package br.com.jrrunningapp.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.jrrunningapp.cards.RunningCard
import br.com.jrrunningapp.cards.RunningCardData
import br.com.jrrunningapp.cards.RunningCardSizeType
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun RunningGrid(
    defaultSpacing: Dp = 12.dp,
    contentPadding: Dp = 16.dp,
    lazyGridScope: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        columns = object : GridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                val firstColumn = ((availableSize - spacing) * 1.2 / 3).roundToInt()
                val othersColumns = (availableSize - spacing * 2 - firstColumn) / 2
                return listOf(firstColumn, othersColumns, othersColumns)
            }
        },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        lazyGridScope()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RunningFlowGrid(
    modifier: Modifier = Modifier,
    items: List<RunningCardData>
) {
    BoxWithConstraints(modifier = modifier) {
        val availableWidth = maxWidth - 16.dp

        val firstColumn = (availableWidth / 3f) * 1.2f
        val othersColumns = (availableWidth - 16.dp - firstColumn) / 2

        RunningFlowGrid(
            modifier = modifier,
        ) {

            items.forEach { item ->
                val type = item.type
                val width = if (type == RunningCardSizeType.BIG) {
                    firstColumn
                } else {
                    othersColumns
                }

                RunningCard(
                    modifier = Modifier.size(
                        width = width,
                        height = type.size.y.dp
                    ),
                    data = item
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RunningFlowGrid(
    modifier: Modifier = Modifier,
    content: @Composable FlowRowScope.() -> Unit
) {
    FlowRow(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 3
    ) {
        content()
    }
}