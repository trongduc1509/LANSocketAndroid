package com.example.lansocketandroid.app

import com.example.lansocketandroid.app.communicator.RealClientCommunicator
import com.example.lansocketandroid.presentation.client.ClientViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun createAppModule() = module {
    factory<ClientCommunicator>{ RealClientCommunicator() }

    viewModel { ClientViewModel(get()) }
}