package com.example.lansocketandroid

import android.app.Application
import com.example.lansocketandroid.app.createAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        createComponents()
    }

    private fun createComponents() {
        startKoin {
            androidContext(this@MainApplication)
            modules(createAppModule())
        }
    }
}