package com.example.geomate.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun fetchProfilePicture(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(profilePictureUri = usersRepository.getProfilePicture(userId)) }
    }

    fun fetchUser(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        usersRepository.get(userId).collect { userOrNull ->
            userOrNull?.let { user ->
                _uiState.update {
                    it.copy(user = user, isLoading = false)
                }
            }
        }
    }
}