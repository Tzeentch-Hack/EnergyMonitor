package com.tzeentch.energy_saver.di

import com.tzeentch.energy_saver.viewModels.AuthViewModel
import com.tzeentch.energy_saver.viewModels.DetailsViewModel
import com.tzeentch.energy_saver.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

fun viewModels():Module= module {
  viewModelOf(::AuthViewModel)
  viewModelOf(::MainViewModel)
  viewModelOf(::DetailsViewModel)
}