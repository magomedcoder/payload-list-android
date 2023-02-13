package ru.magomedcoder.askue.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.magomedcoder.askue.domain.model.*
import ru.magomedcoder.askue.utils.Resource

interface CounterRepository {

    fun fetchList(
        etFrom: String?,
        etTo: String?,
        etContractNumber: String?,
        etSerialNumber: String?,
        etLocality: String?,
        etStreet: String?,
        etNumber: String?,
        etApartmentNumber: String?
    ): Flow<Resource<List<ElectronicCounter>>>

    suspend fun eventCounter(): Resource<Event>

    fun fetchEventList(): Flow<Resource<List<ElectronicEvent>>>

    fun fetchArchiveList(
        startDate: String?,
        endDate: String?
    ): Flow<Resource<List<ElectronicArchive>>>

    fun fetchOutList(): Flow<Resource<List<ElectronicOut>>>

    suspend fun deviceStatus(devEui: String): Resource<DeviceStatus>

    suspend fun deviceMgmt(devEui: String, status: Int): Resource<DeviceStatus>

}