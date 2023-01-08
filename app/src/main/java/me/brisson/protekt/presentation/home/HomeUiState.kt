package me.brisson.protekt.presentation.home

import me.brisson.protekt.domain.model.Item

data class HomeUiState(
    val selectedChips: List<String> = emptyList(),
    val itemList: List<Item>? = null,
    val loadingItemList: Boolean = false,
    val errorItemList: Exception? = null
)