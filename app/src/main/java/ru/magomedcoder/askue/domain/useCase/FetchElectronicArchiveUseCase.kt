package ru.magomedcoder.askue.domain.useCase

import ru.magomedcoder.askue.domain.repository.CounterRepository

class FetchElectronicArchiveUseCase(
    private val repository: CounterRepository
) {

    operator fun invoke(startDate: String?, endDate: String?) = repository.fetchArchiveList(startDate, endDate)

}