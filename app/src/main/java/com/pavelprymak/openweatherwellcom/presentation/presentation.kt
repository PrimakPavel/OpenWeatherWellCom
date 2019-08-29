package com.pavelprymak.openweatherwellcom.presentation

import com.pavelprymak.openweatherwellcom.presentation.viewModels.WeatherViewModel
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module

val presentation = module {
    viewModel { WeatherViewModel(get(), get()) }
}