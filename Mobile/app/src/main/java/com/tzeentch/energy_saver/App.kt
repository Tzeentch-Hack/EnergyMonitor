package com.tzeentch.energy_saver

import android.app.Application
import com.tzeentch.energy_saver.di.networkModule
import com.tzeentch.energy_saver.di.prefs
import com.tzeentch.energy_saver.di.repositories
import com.tzeentch.energy_saver.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = org.koin.core.logger.Level.ERROR )
            androidContext(androidContext = this@App)
            modules(networkModule(), repositories(),viewModels(),prefs())
        }
    }
}