package ru.magomedcoder.askue.ui.event

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.useCase.FetchElectronicEventUseCase
import ru.magomedcoder.askue.ui.base.BaseViewModel
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val useCase: FetchElectronicEventUseCase
) : BaseViewModel() {

    private val _eventState = MutableStateFlow<EventState>(EventState.Empty)
    val eventState = _eventState.asStateFlow()

    init {
        getList()
    }

    fun getList() = viewModelScope.launch {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        _eventState.value = EventState.Success(response = response)
                    }
                }
                is Resource.Loading -> _eventState.value = EventState.Loading
                else -> Unit

            }
        }.launchIn(this)
    }

}