package com.example.geomate.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.FriendshipStatus
import com.example.geomate.data.models.Notification
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.FriendshipRepository
import com.example.geomate.data.repositories.NotificationRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val usersRepository: UsersRepository,
    private val friendshipRepository: FriendshipRepository,
    private val notificationRepository: NotificationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun fetchNotifications() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        notificationRepository.getUserSentAsFlow().collect {
            val notifications = it.map { notification ->
                val sender = usersRepository.getSingle(notification.senderId) ?: User()
                val senderProfilePicture = usersRepository.getProfilePicture(notification.senderId)
                Notification.FriendshipRequest(sender, senderProfilePicture, notification.createdAt)
            }
            _uiState.update { it.copy(notifications = notifications, isLoading = false) }
        }
    }

    fun declineRequest(userId: String) {
        viewModelScope.launch {
            friendshipRepository.remove(userId)
        }
    }

    fun acceptRequest(userId: String) {
        viewModelScope.launch {
            friendshipRepository.updateStatus(
                userId,
                FriendshipStatus.Sent
            )
        }
    }
}