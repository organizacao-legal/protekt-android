package me.brisson.protekt.presentation.create.credential

import me.brisson.protekt.domain.model.Credential

data class CreateCredentialUiState(
    val credential: Credential? = null,
    val loading: Boolean = false,
    val error: Exception? = null,
    val urlCorrect: Boolean = false,
    val urlError: Boolean = false
)
