package com.example.geomate.ui.screens.search

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    fun fetchUsers() = viewModelScope.launch {
        searchQuery
            .debounce {
                _uiState.update { it.copy(duringDebounce = true) }
                500L
            }.collect { query ->
                _uiState.update { it.copy(duringDebounce = false) }

                val displayIndicator = launch {
                    delay(300L)
                    _uiState.update { it.copy(isLoading = true) }
                }

                val users = if (query.isBlank()) listOf() else {
                    val byFirstName = usersRepository.matchFirstName(query.uppercase()).toSet()
                    val byLastName = usersRepository.matchLastName(query.uppercase()).toSet()
                    val byUsername = usersRepository.matchUsername(query).toSet()
                    (byFirstName union byLastName union byUsername).toList()
                }
                _uiState.update {
                    it.copy(users = users.associateWith { Uri.EMPTY })
                }

                displayIndicator.cancel()
                _uiState.update { it.copy(isLoading = false) }
            }
    }

    fun updateSearchQuery(searchQuery: String) {
        _searchQuery.update { searchQuery }
    }
}