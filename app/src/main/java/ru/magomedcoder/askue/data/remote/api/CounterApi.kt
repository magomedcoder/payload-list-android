package ru.magomedcoder.askue.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.magomedcoder.askue.data.remote.response.ElectronicArchiveResponse
import ru.magomedcoder.askue.data.remote.response.ElectronicEventResponse
import ru.magomedcoder.askue.data.remote.response.ElectronicCounterResponse
import ru.magomedcoder.askue.data.remote.response.ElectronicOutResponse
import javax.inject.Singleton

@Singleton
interface CounterApi {

    @GET("scadaapi/main")
    suspend fun doElectronicCounterList(
        @Query("organization_id") organizationId: Int?,
        @Query("start_date") etFrom: String?,
        @Query("end_date") etTo: String?,
        @Query("contract_number") etContractNumber: String?,
        @Query("ser_num") etSerialNumber: String?,
        @Query("city") etLocality: String?,
        @Query("street") etStreet: String?,
        @Query("number") etNumber: String?,
        @Query("flat_number") etApartmentNumber: String?
    ): ElectronicCounterResponse

    @GET("scadaapi/badgealerts")
    suspend fun doElectronicEventList(
        @Query("organization_id") organizationId: Int?,
        @Query("detail") detail: Boolean?,
        @Query("alarm_reset") alarmReset: Boolean?
    ): ElectronicEventResponse

    @GET("scadaapi/badgealerts")
    suspend fun doElectronicArchiveList(
        @Query("organization_id") organizationId: Int?,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?,
        @Query("red_level") redLevel: Int?,
        @Query("orange_level") orangeLevel: Int?,
        @Query("yellow_level") yellowLevel: Boolean?,
        @Query("detail") detail: Boolean?
    ): ElectronicArchiveResponse

    @GET("scadaapi/non_working_devices")
    suspend fun doElectronicOutList(
        @Query("page") page: Int?,
        @Query("day") day: Int?
    ): ElectronicOutResponse

}