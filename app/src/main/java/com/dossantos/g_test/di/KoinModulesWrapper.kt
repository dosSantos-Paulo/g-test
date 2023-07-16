package com.dossantos.g_test.di

import com.dossantos.g_test.ui.test.screen.TouchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val koinModules by lazy {
    loadKoinModules(listOf(viewModelModule))
}

private val viewModelModule = module {
    viewModel { params -> TouchScreenViewModel(resources = params.get()) }
}

fun getKoinModules() = koinModules