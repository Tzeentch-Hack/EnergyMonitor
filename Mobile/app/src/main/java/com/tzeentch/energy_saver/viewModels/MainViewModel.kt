package com.tzeentch.energy_saver.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.repositories.AuthRepository
import com.tzeentch.energy_saver.repositories.MainRepository
import com.tzeentch.energy_saver.ui.states.AuthStates
import com.tzeentch.energy_saver.ui.states.MainStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: MainRepository,
    private val prefs:PreferenceManager
) :ViewModel(){

    private val _homeState = MutableStateFlow<MainStates>(MainStates.Main)
    val homeState = _homeState.asStateFlow()

    fun changeIP(ip:String){
        viewModelScope.launch {
            prefs.setIp(ip)
        }
    }
    fun exit(){
        viewModelScope.launch {
            prefs.setAuthData("","")
            _homeState.value = MainStates.Exit
        }
    }
}