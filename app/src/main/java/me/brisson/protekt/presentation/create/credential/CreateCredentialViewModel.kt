package me.brisson.protekt.presentation.create.credential

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.brisson.protekt.AppDestinationsArgs.ITEM_ID_ARGS
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Password
import me.brisson.protekt.domain.model.Result
import me.brisson.protekt.domain.repository.ItemRepository
import javax.inject.Inject

@HiltViewModel
class CreateCredentialViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateCredentialUiState())
    val uiState: StateFlow<CreateCredentialUiState> = _uiState.asStateFlow()

    private val itemId: String? = savedStateHandle[ITEM_ID_ARGS]

    init {
        itemId?.let { getItem(it) }
    }

    private fun getItem(itemId: String) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result = itemRepository.getItemById(itemId)) {
                is Result.Success -> {
                    (result.data as Credential).let { credential ->
                        _uiState.update {
                            it.copy(loading = false, credential = credential)
                        }
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(loading = false, error = result.exception)
                    }
                }
            }
        }
    }

    fun createCredential(credential: Credential) {
        _uiState.update { it.copy(postCredentialLoading = true) }
        viewModelScope.launch {
            when (val result = itemRepository.postItem(credential)) {
                is Result.Success -> {
                    (result.data as Credential).let {credential ->
                        _uiState.update {
                            it.copy(postCredentialLoading = false, postCredentialSuccess = credential)
                        }
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(postCredentialLoading = false, postCredentialError = result.exception)
                    }
                }
            }
        }
    }

    fun validateUrl(url: String) {
        val regex =
            "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".toRegex()

        url.ifEmpty {
            _uiState.update { it.copy(isUrlValid = null) }
            return
        }

        val isValid = url.matches(regex)
        _uiState.update { it.copy(isUrlValid = isValid) }
    }

    fun validateName(name: String) {
        name.ifEmpty {
            _uiState.update { it.copy(isNameValid = null) }
            return
        }

        _uiState.update { it.copy(isNameValid = true) }
    }

    fun validateUsername(username: String) {
        username.ifEmpty {
            _uiState.update { it.copy(isUsernameValid = null) }
            return
        }

        _uiState.update { it.copy(isUsernameValid = true) }
    }

    fun validatePassword(password: Password) {
        password.value.ifEmpty {
            _uiState.update { it.copy(isPasswordValid = null) }
            return
        }

        val isValid = password.calculatePasswordSafety() >= .8F
        _uiState.update { it.copy(isPasswordValid = isValid) }
    }

    fun validateNote(note: String) {
        note.ifEmpty {
            _uiState.update { it.copy(isNoteValid = null) }
            return
        }

        _uiState.update { it.copy(isNoteValid = true) }
    }
}