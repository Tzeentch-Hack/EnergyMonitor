package com.tzeentch.energy_saver.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.remote.NetworkResultState
import com.tzeentch.energy_saver.remote.dto.AuthResultDto
import com.tzeentch.energy_saver.remote.isLoading
import com.tzeentch.energy_saver.remote.onFailure
import com.tzeentch.energy_saver.remote.onSuccess
import com.tzeentch.energy_saver.repositories.AuthRepository
import com.tzeentch.energy_saver.ui.states.AuthStates
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel constructor(
private val repository: AuthRepository,
private val prefs: PreferenceManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthStates>(AuthStates.Loading)
    val authState = _authState.asStateFlow()

    private val coroutineExceptionHandler =
        Dispatchers.IO + CoroutineExceptionHandler { _, thr ->
            Log.d("smt", thr.message.toString())
            _authState.value = AuthStates.Form("Unexpected Error")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            val (name, password) = prefs.getAuthData()
            val ip = prefs.getIp()
            if (name.isEmpty() || password.isEmpty()||ip.isEmpty()) {
                _authState.value = AuthStates.Initial
            } else {
                loginUser(name, password)
            }
        }
    }

    fun setIp(ip:String){
        viewModelScope.launch(coroutineExceptionHandler) {
            prefs.setIp(ip)
        }
    }

    fun loginUser(name: String, password: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.loginUser(name, password,prefs.getIp()).collect { result ->
                processAuthData(result, name, password)
            }
        }
    }

    fun registerUser(
        name: String,
        password: String
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.registerUser(name, password,prefs.getIp()).collect { result ->
                processAuthData(result, name, password)
            }
        }
    }

    private fun processAuthData(result:NetworkResultState<AuthResultDto>, name: String, password: String){
        result.isLoading {
            _authState.value = AuthStates.Loading
        }.onSuccess {
            viewModelScope.launch(coroutineExceptionHandler) {
                prefs.setAuthData(name, password)
                prefs.setToken(it.token)
                _authState.value = AuthStates.GoToMainScreen
            }
        }.onFailure {
            viewModelScope.launch(coroutineExceptionHandler) {
                prefs.setAuthData("", "")
                _authState.value  =
                    AuthStates.Form(it)
            }
        }
    }
}