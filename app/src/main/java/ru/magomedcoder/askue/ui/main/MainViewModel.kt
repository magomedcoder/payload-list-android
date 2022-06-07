package ru.magomedcoder.askue.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.useCase.FetchElectronicCounterUseCase
import ru.magomedcoder.askue.ui.base.BaseViewModel
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchElectronicCounterUseCase: FetchElectronicCounterUseCase
) : BaseViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Empty)
    val mainState = _mainState.asStateFlow()

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

}