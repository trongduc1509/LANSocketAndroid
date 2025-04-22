package com.example.lansocketandroid.app

import com.example.lansocketandroid.app.repository.CommunicatorControlRepositoryReal
import com.example.lansocketandroid.app.repository.CommunicatorSendingRepositoryReal
import com.example.lansocketandroid.app.repository.CommunicatorStreamRepositoryReal
import com.example.lansocketandroid.app.service.CommunicatorControlServiceReal
import com.example.lansocketandroid.app.service.CommunicatorSendingServiceReal
import com.example.lansocketandroid.app.service.CommunicatorStreamServiceReal
import com.example.lansocketandroid.data.remote.service.CommunicatorControlService
import com.example.lansocketandroid.data.remote.service.CommunicatorSendingService
import com.example.lansocketandroid.data.remote.service.CommunicatorStreamService
import com.example.lansocketandroid.domain.repository.CommunicatorControlRepository
import com.example.lansocketandroid.domain.repository.CommunicatorSendingRepository
import com.example.lansocketandroid.domain.repository.CommunicatorStreamRepository
import com.example.lansocketandroid.platform.communicator.ClientCommunicator
import com.example.lansocketandroid.platform.communicator.CommunicatorControl
import com.example.lansocketandroid.presentation.client.ClientViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun createAppModule() = module {
    single<CommunicatorControl> { ClientCommunicator() }

    single<CommunicatorControlService> { CommunicatorControlServiceReal(get()) }
    single<CommunicatorSendingService> { CommunicatorSendingServiceReal(get()) }
    single<CommunicatorStreamService> { CommunicatorStreamServiceReal(get()) }

    factory<CommunicatorControlRepository> { CommunicatorControlRepositoryReal(get()) }
    factory<CommunicatorSendingRepository> { CommunicatorSendingRepositoryReal(get()) }
    factory<CommunicatorStreamRepository> { CommunicatorStreamRepositoryReal(get()) }

    viewModel { ClientViewModel(get(), get(), get()) }
}