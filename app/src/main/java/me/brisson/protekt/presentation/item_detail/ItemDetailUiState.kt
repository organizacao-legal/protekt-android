package me.brisson.protekt.presentation.item_detail

import me.brisson.protekt.domain.model.Item

data class ItemDetailUiState(
    val item: Item? = null,
    val loading: Boolean = false,
    val error: Exception? = null
)
