package com.tzeentch.energy_saver.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tzeentch.energy_saver.helpers.NavigationItem
import com.tzeentch.energy_saver.ui.compose.authorization.Authorization
import com.tzeentch.energy_saver.ui.compose.details.DetailsScreen
import com.tzeentch.energy_saver.ui.compose.main.MainScreen
import com.tzeentch.energy_saver.ui.theme.EnergySaverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnergySaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Authorization.route
                    ) {
                        composable(route = NavigationItem.Authorization.route) {
                            Authorization(navController)
                        }
                        composable(route = NavigationItem.MainScreen.route) {
                            MainScreen(navController)
                        }
                        composable(route = NavigationItem.Details.route){
                            DetailsScreen()
                        }
                    }
                }
            }
        }
    }
}
