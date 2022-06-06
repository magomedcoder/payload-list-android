package ru.magomedcoder.askue.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.useCase.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.utils.Resource

class MainViewModel(
    private val fetchElectronicCounterUseCase: FetchElectronicCounterUseCase
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Empty)
    val mainState get() = _mainState.asStateFlow()

    init {
        getList()
    }

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
        fetchElectronicCounterUseCase(etFrom, etTo, etContractNumber, etSerialNumber, etLocality, etStreet, etNumber, etApartmentNumber).onEach { result ->
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

}