package com.mdlicht.zb.composesampleapp.example.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListExampleViewModel : ViewModel() {
    private var _job: Job? = null

    private val searchAnimalUseCase = SearchAnimalUseCase()

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Idle)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    fun searchAnimal(animalName: String) {
        _job?.cancel()
        _job = viewModelScope.launch {
            if (animalName.isEmpty()) {
                _uiState.value = ListUiState.Idle
            } else {
                _uiState.value = ListUiState.Loading

                delay(timeMillis = 500)
                
                val animalList = searchAnimalUseCase(animalName = animalName)
                _uiState.value = if (animalList.isEmpty()) {
                    ListUiState.Empty
                } else {
                    ListUiState.Result(keyword = animalName, list = animalList)
                }
            }
        }
    }

    fun clearInput() {
        _uiState.value = ListUiState.Idle
    }
}

sealed class ListUiState {
    data object Idle : ListUiState()

    data object Loading : ListUiState()

    data object Empty : ListUiState()

    class Result(
        val keyword: String,
        val list: List<String>,
    ) : ListUiState()
}