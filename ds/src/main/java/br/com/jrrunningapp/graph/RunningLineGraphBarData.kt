package br.com.jrrunningapp.graph

import androidx.compose.ui.graphics.Color


data class LineGraphData(
    val color: Color,
    val y: Int,
)

fun mockLineGraphData() = listOf(
    LineGraphData(Color.Blue, 10),
    LineGraphData(Color.Green, 20),
    LineGraphData(Color.Red, 30),
    LineGraphData(Color.Yellow, 40),
    LineGraphData(Color.Cyan, 10),
    LineGraphData(Color.Blue, 10),
    LineGraphData(Color.Green, 20),
    LineGraphData(Color.Red, 15)
)
