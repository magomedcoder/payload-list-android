package ru.magomedcoder.askue.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.magomedcoder.askue.data.remote.response.ElectronicCounterResponse
import javax.inject.Singleton

@Singleton
interface CounterApi {

    @GET("scadaapi/main")
    suspend fun doElectronicCounterList(
        @Query("start_date") etFrom: String?,
        @Query("end_date") etTo: String?,
        @Query("contract_number") etContractNumber: String?,
        @Query("ser_num") etSerialNumber: String?,
        @Query("city") etLocality: String?,
        @Query("street") etStreet: String?,
        @Query("number") etNumber: String?,
        @Query("flat_number") etApartmentNumber: String?
    ): ElectronicCounterResponse

}