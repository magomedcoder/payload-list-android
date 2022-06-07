package ru.magomedcoder.askue.ui.out

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.useCase.FetchElectronicOutUseCase
import ru.magomedcoder.askue.ui.base.BaseViewModel
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class OutViewModel @Inject constructor(
    private val useCase: FetchElectronicOutUseCase
) : BaseViewModel() {

    private val _outState = MutableStateFlow<OutState>(OutState.Empty)
    val outState = _outState.asStateFlow()

    init {
        getList()
    }

    fun getList() = viewModelScope.launch {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        _outState.value = OutState.Success(response = response)
                    }
                }
                is Resource.Loading -> _outState.value = OutState.Loading
                else -> Unit
            }
        }.launchIn(this)
    }

}