package com.tzeentch.energy_saver.ui.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.tzeentch.energy_saver.helpers.RoundFloor
import com.tzeentch.energy_saver.remote.dto.GraphicsList

@Composable
fun GraphsCreatorWatt(graphData:List<GraphicsList>){
    val list = listOf(1, 5, 10, 3, 4, 10, 30, 50, 30, 10, 20, 30)
    val lineChart1 = lineChart(
        lines = listOf(
            lineSpec(
                lineThickness = 3.dp,
                lineColor = Color(0xFF7F00FF),
            ),
        )
    )
    val lineChart2 = lineChart(
        lines = listOf(
            lineSpec(
                lineThickness = 3.dp,
                lineColor = Color(0xFF102C54),
            ),
        )
    )
    val entryCollections1 by remember {
        mutableStateOf(
            graphData.mapIndexed { index, watt ->
                FloatEntry(
                    x = index.toFloat(), y = watt.wattConsumption.substringBefore(',').toFloat()
                )
            }.toList()
        )
    }
    val entryCollections2 by remember {
        mutableStateOf(
            list.subList(8, list.size).mapIndexed { index, watt ->
                FloatEntry(
                    x = index.toFloat() + 8, y = watt.toFloat()
                )
            }.toList()
        )
    }
    val chartEntryModel1 =
        ChartEntryModelProducer(entryCollections1)
    val chartEntryModel2 =
        ChartEntryModelProducer(entryCollections2)
    val composedChartEntryModelProducer =
        chartEntryModel1 + chartEntryModel2
    Chart(
        chart = remember(
            lineChart1, lineChart2
        ) { lineChart1 + lineChart2 },
        chartModelProducer = composedChartEntryModelProducer,
        startAxis = rememberStartAxis(axisLabelComponent(color = Color.White)),
        bottomAxis = rememberBottomAxis(axisLabelComponent(color = Color.White)),
    )
}



@Composable
fun GraphsCreatorSum(graphData:List<GraphicsList>){
    val list = listOf(1, 5, 10, 3, 4, 10, 30, 50, 30, 10, 20, 30)
    val lineChart1 = lineChart(
        lines = listOf(
            lineSpec(
                lineThickness = 3.dp,
                lineColor = Color(0xFF7F00FF),
            ),
        )
    )
    val lineChart2 = lineChart(
        lines = listOf(
            lineSpec(
                lineThickness = 3.dp,
                lineColor = Color(0xFF102C54),
            ),
        )
    )
    val entryCollections1 by remember {
        mutableStateOf(
            graphData.mapIndexed { index, sum ->
                FloatEntry(
                    x = index.toFloat(), y = RoundFloor.roundOffDecimal(
                        sum.sumConsumption.toFloat()))
            }.toList()
        )
    }
    val entryCollections2 by remember {
        mutableStateOf(
            list.subList(8, list.size).mapIndexed { index, watt ->
                FloatEntry(
                    x = index.toFloat() + 8, y = watt.toFloat()
                )
            }.toList()
        )
    }
    val chartEntryModel1 =
        ChartEntryModelProducer(entryCollections1)
    val chartEntryModel2 =
        ChartEntryModelProducer(entryCollections2)
    val composedChartEntryModelProducer =
        chartEntryModel1 + chartEntryModel2
    Chart(
        chart = remember(
            lineChart1, lineChart2
        ) { lineChart1 + lineChart2 },
        chartModelProducer = composedChartEntryModelProducer,
        startAxis = rememberStartAxis(axisLabelComponent(color = Color.White)),
        bottomAxis = rememberBottomAxis(axisLabelComponent(color = Color.White)),
    )
}