package ru.magomedcoder.askue.domain.useCase

import ru.magomedcoder.askue.domain.repository.CounterRepository

class FetchElectronicCounterUseCase(
    private val repository: CounterRepository
) {

    operator fun invoke(
        etFrom: String?,
        etTo: String?,
        etContractNumber: String?,
        etSerialNumber: String?,
        etLocality: String?,
        etStreet: String?,
        etNumber: String?,
        etApartmentNumber: String?
    ) = repository.fetchList(
        etFrom,
        etTo,
        etContractNumber,
        etSerialNumber,
        etLocality,
        etStreet,
        etNumber,
        etApartmentNumber
    )

}