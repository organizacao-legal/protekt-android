package me.brisson.protekt.presentation.create.credential

import me.brisson.protekt.domain.model.Credential

data class CreateCredentialUiState(
    val credential: Credential? = null,
    val loading: Boolean = false,
    val error: Exception? = null,
    val postCredentialLoading: Boolean = false,
    val postCredentialError: Exception? = null,
    val postCredentialSuccess: Credential? = null,
    val isUrlValid: Boolean? = null,
    val isNameValid: Boolean? = null,
    val isUsernameValid: Boolean? = null,
    val isPasswordValid: Boolean? = null,
    val isNoteValid: Boolean? = null,
)
