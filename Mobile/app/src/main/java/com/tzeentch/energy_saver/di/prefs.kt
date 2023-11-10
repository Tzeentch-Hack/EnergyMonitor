package com.tzeentch.energy_saver.di

import com.tzeentch.energy_saver.local.PreferenceManager
import com.tzeentch.energy_saver.local.PreferenceManagerImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun prefs():Module = module {
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
}