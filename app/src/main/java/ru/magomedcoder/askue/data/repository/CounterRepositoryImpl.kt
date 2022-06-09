package ru.magomedcoder.askue.data.repository

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.data.local.UserPreferences
import ru.magomedcoder.askue.data.remote.api.CounterApi
import ru.magomedcoder.askue.data.remote.toElectronicArchiveDomain
import ru.magomedcoder.askue.data.remote.toElectronicCounterDomain
import ru.magomedcoder.askue.data.remote.toElectronicEventDomain
import ru.magomedcoder.askue.data.remote.toElectronicOutDomain
import ru.magomedcoder.askue.domain.model.Event
import ru.magomedcoder.askue.domain.repository.CounterRepository
import ru.magomedcoder.askue.utils.Resource
import java.io.IOException

class CounterRepositoryImpl(
    private val api: CounterApi,
    private val userPreferences: UserPreferences
) : CounterRepository {

    override fun fetchList(
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
        val organizationId = userPreferences.getOrganizationId()?.toInt()
        try {
            val data = api.doElectronicCounterList(
                organizationId,
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

    override suspend fun eventCounter(): Resource<Event> {
        return try {
            val response = api
                .doEventCounter()
                .toEventResponse()
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        } catch (e: IOException) {
            Resource.Error(error = e.message ?: R.string.unknown_error.toString())
        }
    }

    override fun fetchEventList() = flow {
        emit(Resource.Loading())
        val organizationId = userPreferences.getOrganizationId()?.toInt()
        try {
            val data = api.doElectronicEventList(
                organizationId,
                true,
                true
            )
            emit(Resource.Success(data = data.results.map { it.toElectronicEventDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        }
    }

    override fun fetchArchiveList(startDate: String?, endDate: String?) = flow {
        emit(Resource.Loading())
        val organizationId = userPreferences.getOrganizationId()?.toInt()
        try {
            val data = api.doElectronicArchiveList(
                organizationId,
                startDate,
                endDate,
                false,
                false,
                true,
                true
            )
            emit(Resource.Success(data = data.results.map { it.toElectronicArchiveDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        }
    }

    override fun fetchOutList() = flow {
        emit(Resource.Loading())
        try {
            val data = api.doElectronicOutList(
                1,
                7
            )
            emit(Resource.Success(data = data.results.map { it.toElectronicOutDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        }
    }

}