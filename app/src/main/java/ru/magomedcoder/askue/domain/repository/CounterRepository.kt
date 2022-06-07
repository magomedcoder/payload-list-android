package ru.magomedcoder.askue.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.magomedcoder.askue.domain.model.ElectronicArchive
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.domain.model.ElectronicEvent
import ru.magomedcoder.askue.domain.model.ElectronicOut
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

    fun fetchEventList(): Flow<Resource<List<ElectronicEvent>>>

    fun fetchArchiveList(): Flow<Resource<List<ElectronicArchive>>>

    fun fetchOutList(): Flow<Resource<List<ElectronicOut>>>

}