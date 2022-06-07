package ru.magomedcoder.askue.domain.useCase

import ru.magomedcoder.askue.domain.repository.CounterRepository

class FetchElectronicArchiveUseCase(
    private val repository: CounterRepository
) {

    suspend operator fun invoke() = repository.fetchArchiveList()

}