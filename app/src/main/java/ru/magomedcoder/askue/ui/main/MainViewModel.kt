package ru.magomedcoder.askue.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.model.Event
import ru.magomedcoder.askue.domain.repository.CounterRepository
import ru.magomedcoder.askue.domain.useCase.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.ui.base.BaseViewModel
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchElectronicCounterUseCase: FetchElectronicCounterUseCase,
    private val counterRepository: CounterRepository
) : BaseViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Empty)
    val mainState = _mainState.asStateFlow()

    fun getList(
        etFrom: String? = null,
        etTo: String? = null,
        etContractNumber: String? = null,
        etSerialNumber: String? = null,
        etLocality: String? = null,
        etStreet: String? = null,
        etNumber: String? = null,
        etApartmentNumber: String? = null
    ) = viewModelScope.launch {
        fetchElectronicCounterUseCase(
            etFrom,
            etTo,
            etContractNumber,
            etSerialNumber,
            etLocality,
            etStreet,
            etNumber,
            etApartmentNumber
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        _mainState.value = MainState.Success(response = response)
                    }
                }
                is Resource.Loading -> _mainState.value = MainState.Loading
                else -> Unit
            }
        }.launchIn(this)
    }

    fun getCounter() = viewModelScope.launch {
        when (val response = counterRepository.eventCounter()) {
            is Resource.Success -> {
                _mainState.value = MainState.Counter(
                    event = Event(
                        orangeLevel = response.data?.orangeLevel,
                        redLevel = response.data?.redLevel,
                        yellowLevel = response.data?.yellowLevel
                    )
                )
            }
            else -> Unit
        }
    }

}