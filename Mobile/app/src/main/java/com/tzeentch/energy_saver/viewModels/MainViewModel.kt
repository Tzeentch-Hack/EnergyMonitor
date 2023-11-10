package com.tzeentch.energy_saver.viewModels

import androidx.lifecycle.ViewModel
import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.repositories.AuthRepository
import com.tzeentch.energy_saver.repositories.MainRepository

class MainViewModel constructor(
    private val repository: MainRepository,
    private val prefs:PreferenceManager
) :ViewModel(){

}