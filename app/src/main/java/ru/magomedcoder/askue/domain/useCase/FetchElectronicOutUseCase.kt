package ru.magomedcoder.askue.domain.useCase

import ru.magomedcoder.askue.domain.repository.CounterRepository

class FetchElectronicOutUseCase(
    private val repository: CounterRepository
) {

    suspend operator fun invoke() = repository.fetchOutList()

}