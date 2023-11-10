package com.tzeentch.energy_saver.ui.states

sealed class AuthStates {
    data object Initial : AuthStates()
    data object Loading : AuthStates()
    data class Form(val error: String) : AuthStates()
    data object GoToMainScreen : AuthStates()
}