package com.example.geomate.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.Notification
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.NotificationsRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val usersRepository: UsersRepository,
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun fetchNotifications(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        notificationsRepository.getFriendshipRequestsFlow(userId).collect {
            val notifications = it.map { notification ->
                val sender = usersRepository.getSingle(notification.senderId) ?: User()
                val senderProfilePicture = usersRepository.getProfilePicture(notification.senderId)
                Notification.FriendshipRequest(sender, senderProfilePicture, notification.createdAt)
            }
            _uiState.update { it.copy(notifications = notifications, isLoading = false) }
        }
    }
}