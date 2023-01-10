package me.brisson.protekt.presentation.item_detail

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
import me.brisson.protekt.domain.repository.ItemRepository
import javax.inject.Inject
import me.brisson.protekt.domain.model.Result

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemDetailUiState())
    val uiState: StateFlow<ItemDetailUiState> = _uiState.asStateFlow()

    private val itemId = savedStateHandle.get<String>(ITEM_ID_ARGS)

    init {
        itemId?.let { id -> getItem(id) }
    }

    private fun getItem(itemId: String) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result = itemRepository.getItemById(itemId)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(loading = false, item = result.data)
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
}