package ru.magomedcoder.askue.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.utils.Resource

interface CounterRepository {

    suspend fun fetchList(): Flow<Resource<List<ElectronicCounter>>>

}