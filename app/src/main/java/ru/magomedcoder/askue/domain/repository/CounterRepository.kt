package ru.magomedcoder.askue.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.utils.Resource

interface CounterRepository {

    suspend fun fetchList(
        etFrom: String?,
        etTo: String?,
        etContractNumber: String?,
        etSerialNumber: String?,
        etLocality: String?,
        etStreet: String?,
        etNumber: String?,
        etApartmentNumber: String?
    ): Flow<Resource<List<ElectronicCounter>>>

}