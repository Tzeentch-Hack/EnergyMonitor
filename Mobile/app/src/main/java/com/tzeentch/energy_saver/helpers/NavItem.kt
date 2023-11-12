package com.tzeentch.energy_saver.helpers

import androidx.compose.runtime.Composable

sealed class NavigationItem(
    val route: String,
) {
    data object Authorization : NavigationItem("auth")
    data object MainScreen : NavigationItem("main")
    data object Details: NavigationItem("details")
}

sealed class PagerItems(
    open val screen: @Composable () -> Unit
) {

    class PageScreen(override val screen: @Composable () -> Unit) : PagerItems({})
}