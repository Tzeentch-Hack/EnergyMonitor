package com.tzeentch.energy_saver.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.remote.dto.DeviceDto
import com.tzeentch.energy_saver.remote.dto.GraphicsResultDto
import com.tzeentch.energy_saver.remote.isLoading
import com.tzeentch.energy_saver.remote.onFailure
import com.tzeentch.energy_saver.remote.onSuccess
import com.tzeentch.energy_saver.repositories.MainRepository
import com.tzeentch.energy_saver.ui.states.MainStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: MainRepository,
    private val prefs: PreferenceManager
) : ViewModel() {

    private val _homeState = MutableStateFlow<MainStates>(MainStates.Main)
    val homeState = _homeState.asStateFlow()

    private val _deviceList = MutableStateFlow<List<DeviceDto>>(emptyList())
    val deviceList = _deviceList.asStateFlow()

    private val _mainDataGraph =
        MutableStateFlow<GraphicsResultDto>(GraphicsResultDto(data = emptyList()))
    val mainDataGraph = _mainDataGraph.asStateFlow()

    private val _dataGraphById =
        MutableStateFlow<GraphicsResultDto>(GraphicsResultDto(data = emptyList()))
    val dataGraphById = _mainDataGraph.asStateFlow()

    init {
        getDeviceInfo()
        getGraphsInfo()
    }


    private fun getGraphsInfo() {
        viewModelScope.launch {
            repository.getGraphicsData(prefs.getToken(), prefs.getIp()).collect { result ->
                result.isLoading {

                }.onSuccess {
                    _mainDataGraph.value = it
                }.onFailure {

                }
            }
        }
    }

    fun getGraphsById(id:String){
        viewModelScope.launch {
            repository.getGraphicsDataById(prefs.getToken(), prefs.getIp(),id).collect { result ->
                result.isLoading {

                }.onSuccess {
                    _dataGraphById.value = it
                }.onFailure {

                }
            }
        }
    }


    fun changeIP(ip: String) {
        viewModelScope.launch {
            prefs.setIp(ip)
        }
    }

    fun exit() {
        viewModelScope.launch {
            prefs.setAuthData("", "")
            _homeState.value = MainStates.Exit
        }
    }

    fun disableDevice(id: String) {
        viewModelScope.launch {
            repository.disableDevices(ip = prefs.getIp(), token = prefs.getToken(), id = id)
                .collect { result ->
                    result.isLoading {

                    }.onSuccess {

                    }.onFailure {

                    }
                }
        }
    }

    private fun getDeviceInfo() {
        viewModelScope.launch() {
            while (true) {
                repository.getDevices(prefs.getToken(), prefs.getIp()).collect { result ->
                    result.isLoading {

                    }.onSuccess {
                        _deviceList.value = it
                    }.onFailure {
                        exit()
                    }
                }
                delay(6000)
            }
        }
    }
}