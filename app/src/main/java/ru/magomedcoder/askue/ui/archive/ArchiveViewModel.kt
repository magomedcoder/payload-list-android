package ru.magomedcoder.askue.ui.archive

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.domain.useCase.FetchElectronicArchiveUseCase
import ru.magomedcoder.askue.ui.base.BaseViewModel
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val useCase: FetchElectronicArchiveUseCase
) : BaseViewModel() {

    private val _archiveState = MutableStateFlow<ArchiveState>(ArchiveState.Empty)
    val archiveState = _archiveState.asStateFlow()

    init {
        getList()
    }

    fun getList() = viewModelScope.launch {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        _archiveState.value = ArchiveState.Success(response = response)
                    }
                }
                is Resource.Loading -> _archiveState.value = ArchiveState.Loading
                else -> Unit

            }
        }.launchIn(this)
    }

}