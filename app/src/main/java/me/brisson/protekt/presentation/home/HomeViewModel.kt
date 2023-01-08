package me.brisson.protekt.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.brisson.protekt.domain.repository.ItemRepository
import me.brisson.protekt.domain.model.Result
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllItems()
    }

    private fun getAllItems() {
        _uiState.update { it.copy(loadingItemList = true) }
        viewModelScope.launch {
            when (val result = itemRepository.getAllItems()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            loadingItemList = false,
                            itemList = result.data
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            loadingItemList = false,
                            errorItemList = result.exception
                        )
                    }
                }
            }
        }
    }
}