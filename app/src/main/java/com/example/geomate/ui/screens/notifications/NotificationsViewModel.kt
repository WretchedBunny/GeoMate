package com.example.geomate.ui.screens.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.Notification
import com.example.geomate.data.repositories.NotificationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun fetchNotifications(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val notifications = notificationsRepository.getFriendshipRequests(userId)
        Log.d("asdqwe", "fetchNotifications: $notifications")
        _uiState.update { it.copy(notifications = notifications, isLoading = false) }
    }
}