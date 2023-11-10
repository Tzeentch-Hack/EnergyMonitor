package com.tzeentch.energy_saver.helpers

sealed class NavigationItem(
    val route: String,
) {
    data object Authorization : NavigationItem("auth")
    data object MainScreen : NavigationItem("main")
    data object Details:NavigationItem("details")
}