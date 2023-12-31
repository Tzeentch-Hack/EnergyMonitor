package com.tzeentch.energy_saver.ui.compose.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.core.extension.sumByFloat
import com.tzeentch.energy_saver.R
import com.tzeentch.energy_saver.helpers.NavigationItem
import com.tzeentch.energy_saver.local.Constants
import com.tzeentch.energy_saver.remote.dto.DeviceDto
import com.tzeentch.energy_saver.ui.compose.components.GraphsCreatorSum
import com.tzeentch.energy_saver.ui.compose.components.GraphsCreatorWatt
import com.tzeentch.energy_saver.ui.compose.components.IpDialog
import com.tzeentch.energy_saver.ui.compose.components.TipOfTheDayDialog
import com.tzeentch.energy_saver.ui.states.MainStates
import com.tzeentch.energy_saver.viewModels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.floor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = koinViewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openChangeIPDialog = remember { mutableStateOf(false) }
    var isSum by remember { mutableStateOf(true) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val showTipDialog = remember { mutableStateOf(false) }
    val textModifier = Modifier.padding(top = 10.dp)

    LaunchedEffect(key1 = Unit) {
        showTipDialog.value = true
    }

    val devices = viewModel.deviceList.collectAsState().value

    var selectedDevice by remember {
        mutableStateOf(DeviceDto("", "", "", "", "", "", "", ""))
    }

    when (viewModel.homeState.collectAsState().value) {
        is MainStates.Main -> {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            IconButton(
                                onClick = { scope.launch { drawerState.close() } },
                                modifier = Modifier.padding(start = 15.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_west_24),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Text(text = "Совет Дня!", style = TextStyle(fontSize = 25.sp))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = Constants.TIP_OF_THE_DAY)
                            }

                            Button(
                                onClick = {
                                    openChangeIPDialog.value = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .height(55.dp)
                            ) {
                                Text(text = "Сменить IP")
                            }

                            Button(
                                onClick = {
                                    viewModel.exit()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .height(55.dp)
                            ) {
                                Text(text = "Выйти")
                            }
                        }
                    }
                },
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.partialExpand()
                            }
                        })
                    }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_dehaze_24),
                                contentDescription = "",
                                tint = Color.White
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Watt")
                            Switch(checked = isSum, onCheckedChange = {
                                isSum = it
                            })
                            Text(text = "So'm")
                        }
                    }

                    SpiderCluster(
                        isSum = isSum,
                        deviceList = devices,
                        onTipClick = { index ->
                            scope.launch {
                                selectedDevice = devices[index]
                                viewModel.getGraphsById(devices[index].deviceId)
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
                                    modifier = Modifier.semantics {
                                        contentDescription = "dragHandleDescription"
                                    },
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


                                    val graphData = viewModel.dataGraphById.collectAsState().value
                                    Text(
                                        text = "Потребление: ${
                                            selectedDevice.wattConsumption.substringBefore(
                                                ","
                                            )
                                        }"
                                    )
                                    GraphsCreatorWatt(graphData = graphData.data)
                                    Text(
                                        text = "Потрачено сум: ${
                                            selectedDevice.sumConsumption?.toFloat()?.roundToInt()
                                        }"
                                    )
                                    GraphsCreatorSum(graphData = graphData.data)

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
            }
            if (openChangeIPDialog.value) {
                IpDialog(onChangeIpClick = {
                    viewModel.changeIP(it)
                }) {
                    openChangeIPDialog.value = false
                }
            }
            if (showTipDialog.value) {
                TipOfTheDayDialog(
                    onCancelClick = { showTipDialog.value = false }
                )
            }
        }

        is MainStates.Exit -> {
            navController.navigate(NavigationItem.Authorization.route) {
                popUpTo(NavigationItem.MainScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

}