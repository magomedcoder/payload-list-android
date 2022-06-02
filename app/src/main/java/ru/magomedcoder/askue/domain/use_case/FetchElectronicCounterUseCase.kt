package ru.magomedcoder.askue.domain.use_case

import ru.magomedcoder.askue.domain.repository.CounterRepository

class FetchElectronicCounterUseCase(
    private val repository: CounterRepository
) {

    suspend operator fun invoke() = repository.fetchList()

}