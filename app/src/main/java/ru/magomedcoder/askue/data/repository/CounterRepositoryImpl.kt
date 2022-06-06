package ru.magomedcoder.askue.data.repository

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.data.remote.api.CounterApi
import ru.magomedcoder.askue.data.remote.toElectronicCounterDomain
import ru.magomedcoder.askue.domain.repository.CounterRepository
import ru.magomedcoder.askue.utils.Resource
import java.io.IOException

class CounterRepositoryImpl(
    private val api: CounterApi
) : CounterRepository {

    override suspend fun fetchList(
        etFrom: String?,
        etTo: String?,
        etContractNumber: String?,
        etSerialNumber: String?,
        etLocality: String?,
        etStreet: String?,
        etNumber: String?,
        etApartmentNumber: String?
    ) = flow {
        emit(Resource.Loading())
        try {
            val data = api.doElectronicCounterList(
                etFrom,
                etTo,
                etContractNumber,
                etSerialNumber,
                etLocality,
                etStreet,
                etNumber,
                etApartmentNumber
            )
            emit(Resource.Success(data = data.results.map { it.toElectronicCounterDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        }
    }

}