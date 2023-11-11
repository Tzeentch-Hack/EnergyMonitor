package com.tzeentch.energy_saver.ui.states

sealed class MainStates {

    data object Main:MainStates()

    data object Exit:MainStates()
}