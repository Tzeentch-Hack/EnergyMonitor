package com.tzeentch.energy_saver.ui.compose.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
import com.patrykandpatrick.vico.core.extension.sumByFloat
import com.tzeentch.energy_saver.remote.dto.DeviceDto
import com.tzeentch.energy_saver.viewModels.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageContent(
    viewModel: MainViewModel,
    isSum: Boolean,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val textModifier = Modifier.padding(top = 10.dp)
    val scope = rememberCoroutineScope()
    val devices = viewModel.deviceList.collectAsState().value

    var selectedDevice by remember {
        mutableStateOf(DeviceDto("", "", "", "", "", "", "", ""))
    }
    SpiderCluster(
        isSum = isSum,
        deviceList = devices,
        onTipClick = { index ->
            scope.launch {
                selectedDevice = devices[index]
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }
    ) {
        selectedDevice = DeviceDto("", "", "", "", "", "", "", "")
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetDragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier.semantics { contentDescription = "dragHandleDescription" },
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Box(
                        Modifier.size(width = 32.dp, height = 4.dp)
                    )
                }
                if (devices.isNotEmpty()) {
                    if (selectedDevice.deviceName.isNotEmpty()) {
                        Text(
                            modifier = textModifier,
                            text = selectedDevice.deviceName
                        )
                    } else {
                        Text(
                            modifier = textModifier,
                            text = "Общая статистика"
                        )
                    }
                } else {
                    Text(
                        modifier = textModifier,
                        text = "Нет подключенных девайсов"
                    )
                }
            }
        },
        sheetContent = {
            if (selectedDevice.sumConsumption?.isEmpty() == true) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "Потребление: ${
                            devices.sumByFloat {
                                it.wattConsumption.substringBefore(
                                    ","
                                ).toFloat()
                            }.roundToInt()
                        }W"
                    )
                    Text(
                        text = "Потрачено сум: ${
                            devices.sumByFloat {
                                floor(
                                    it.sumConsumption?.toFloat() ?: (0f * 100.0f)
                                ) / 100.0f
                            }
                        }"
                    )

                    Text(
                        text = "За все время истрачено уже: ${
                            devices.sumByFloat { it.consumptionSummary.toFloat() }
                                .roundToInt()
                        }W"
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "Потребление: ${
                            selectedDevice.wattConsumption.substringBefore(
                                ","
                            )
                        }"
                    )
                    Text(
                        text = "Потрачено сум: ${
                            selectedDevice.sumConsumption?.toFloat()?.roundToInt()
                        }"
                    )
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
                            list.subList(0, 9).mapIndexed { index, watt ->
                                FloatEntry(
                                    x = index.toFloat(), y = watt.toFloat()
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

                    Text(
                        text = "За все время истрачено уже: ${
                            selectedDevice.consumptionSummary.substringBefore(
                                ','
                            ).toFloat().roundToInt()
                        }W"
                    )

                    if (selectedDevice.deviceId.isNotEmpty()) {
                        Button(
                            onClick = {
                                viewModel.disableDevice(selectedDevice.deviceId)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .height(55.dp)
                        ) {
                            Text(text = "Выключить девайс")
                        }

                    }
                }
            }
        },
        content = {},
    )
}