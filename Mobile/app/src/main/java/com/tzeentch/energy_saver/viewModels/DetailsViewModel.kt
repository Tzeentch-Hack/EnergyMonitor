package com.tzeentch.energy_saver.viewModels

import androidx.lifecycle.ViewModel
import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.repositories.AuthRepository
import com.tzeentch.energy_saver.repositories.DetailsRepository

class DetailsViewModel constructor(
    private val repository: DetailsRepository,
    private val prefs: PreferenceManager
) : ViewModel() {
}