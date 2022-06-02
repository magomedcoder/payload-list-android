package ru.magomedcoder.askue.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.domain.repository.UserRepository
import ru.magomedcoder.askue.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Empty)
    val authState = _authState.asStateFlow()

    init {
        _authState.value = AuthState.Logged(repository.isToken())
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val response = repository.login(username, password)) {
                is Resource.Success -> {
                    val data = response.data!!
                    repository.saveToken(data.authToken)
                    _authState.value = AuthState.Success(data)
                }
                is Resource.Error -> _authState.value =
                    AuthState.Failure(response.error ?: R.string.unknown_error.toString())
                else -> Unit
            }
        }
    }

}