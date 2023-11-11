package com.tzeentch.energy_saver.ui.compose.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tzeentch.energy_saver.R
import com.tzeentch.energy_saver.helpers.NavigationItem
import com.tzeentch.energy_saver.ui.compose.components.IpDialog
import com.tzeentch.energy_saver.ui.states.MainStates
import com.tzeentch.energy_saver.viewModels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = koinViewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openChangeIPDialog = remember { mutableStateOf(false) }
    var isSum by remember { mutableStateOf(false) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

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
                                Text(text = "выключайте свет и в мире станет намного светлее!")
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                            Switch(
                                checked = isSum,
                                onCheckedChange = {
                                    isSum = it
                                }
                            )
                            Text(text = "So'm")
                        }
                    }
                    SpiderCluster(
                        legVisibility = listOf(
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true
                        )
                    )
                    BottomSheetScaffold(
                        scaffoldState = bottomSheetScaffoldState,
                        sheetDragHandle = {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .padding(vertical = 0.dp)
                                        .semantics { contentDescription = "dragHandleDescription" },
                                    color = Color.Gray,
                                    shape = MaterialTheme.shapes.extraLarge
                                ) {
                                    Box(
                                        Modifier
                                            .size(
                                                width = 32.dp,
                                                height = 4.dp
                                            )
                                    )
                                }
                                Text(modifier = Modifier.padding(vertical = 8.dp), text = "Refrigerator 228")
                            }
                        },
                        sheetContent = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(128.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text("Swipe up to expand sheet")
                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(64.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Sheet content")
                                Spacer(Modifier.height(20.dp))
                                Button(
                                    onClick = {
                                        scope.launch { bottomSheetScaffoldState.bottomSheetState.partialExpand() }
                                    }
                                ) {
                                    Text("Click to collapse sheet")
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