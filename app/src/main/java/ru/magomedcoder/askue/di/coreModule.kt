package ru.magomedcoder.askue.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.magomedcoder.askue.Constants
import ru.magomedcoder.askue.data.remote.api.CounterApi
import ru.magomedcoder.askue.data.repository.CounterRepositoryImpl
import ru.magomedcoder.askue.domain.repository.CounterRepository
import ru.magomedcoder.askue.domain.use_case.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.ui.main.MainViewModel
import ru.magomedcoder.askue.utils.NetworkHelper

val coreModule = module {
    single {
        Retrofit.Builder()
            .client(NetworkHelper().addHeaderAuthorization())
            .baseUrl(Constants.Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CounterApi::class.java)
    }
    single<CounterRepository> {
        CounterRepositoryImpl(api = get())
    }
    single {
        FetchElectronicCounterUseCase(repository = get())
    }
    viewModel {
        MainViewModel(fetchElectronicCounterUseCase = get())
    }
}
