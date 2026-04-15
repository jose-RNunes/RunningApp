package br.com.jrrunningapp.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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