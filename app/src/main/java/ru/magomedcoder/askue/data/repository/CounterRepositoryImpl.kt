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

    override suspend fun fetchList() = flow {
        emit(Resource.Loading())
        try {
            val data = api.doElectronicCounterList().results.map { it.toElectronicCounterDomain() }
            emit(Resource.Success(data = data))
        } catch (e: IOException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(error = e.message ?: R.string.unknown_error.toString()))
        }
    }

}