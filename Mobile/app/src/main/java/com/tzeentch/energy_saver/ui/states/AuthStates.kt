package com.tzeentch.energy_saver.ui.states

sealed class AuthStates {
    object Initial : AuthStates()
    object Loading : AuthStates()
    data class Form(val error: String) : AuthStates()
    object GoToMainScreen : AuthStates()
}