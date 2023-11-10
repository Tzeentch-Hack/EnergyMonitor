package com.tzeentch.energy_saver.di

import com.tzeentch.energy_saver.repositories.AuthRepository
import com.tzeentch.energy_saver.repositories.DetailsRepository
import com.tzeentch.energy_saver.repositories.MainRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun repositories(): Module = module {
    single { MainRepository(get()) }
    single { AuthRepository(get()) }
    single { DetailsRepository(get()) }
}