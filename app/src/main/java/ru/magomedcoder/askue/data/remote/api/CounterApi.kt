package ru.magomedcoder.askue.data.remote.api

import retrofit2.http.GET
import ru.magomedcoder.askue.data.remote.response.ElectronicCounterResponse
import javax.inject.Singleton

@Singleton
interface CounterApi {

    @GET("scadaapi/main")
    suspend fun doElectronicCounterList(): ElectronicCounterResponse

}