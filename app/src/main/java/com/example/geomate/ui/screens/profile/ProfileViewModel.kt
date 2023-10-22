package com.example.geomate.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.FriendshipStatus.AcceptedWithNotifications
import com.example.geomate.data.models.FriendshipStatus.AcceptedWithoutNotifications
import com.example.geomate.data.models.FriendshipStatus.None
import com.example.geomate.data.models.FriendshipStatus.SentByMe
import com.example.geomate.data.models.FriendshipStatus.SentByUser
import com.example.geomate.data.repositories.FriendshipRepository
import com.example.geomate.data.repositories.UsersRepository
import com.example.geomate.statemachine.FriendshipState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val usersRepository: UsersRepository,
    private val friendshipRepository: FriendshipRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun fetchProfilePicture(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(profilePictureUri = usersRepository.getProfilePicture(userId)) }
    }

    fun fetchUser(userId: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        usersRepository.getSingleAsFlow(userId).collect { userOrNull ->
            userOrNull?.let { user ->
                _uiState.update {
                    it.copy(user = user, isLoading = false)
                }
            }
        }
    }

    fun updateIsMenuVisible(isMenuVisible: Boolean) {
        _uiState.update { it.copy(isMenuVisible = isMenuVisible) }
    }

    fun updateProfilePictureUri(profilePictureUri: Uri?) {
        _uiState.update { it.copy(profilePictureUri = profilePictureUri) }
        onChangePictureClick()
    }

    fun onResetPasswordClick() = viewModelScope.launch {
        try {
            usersRepository.sendRecoveryEmail(uiState.value.user.email)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onChangePictureClick() = viewModelScope.launch {
        val uid = Firebase.auth.uid.toString()
        try {
            uiState.value.profilePictureUri?.let {
                usersRepository.addProfilePicture(uid, it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createFriendshipState(userId: String) {
        val friendshipState = viewModelScope.async {
            val friendship = friendshipRepository.get(userId)
            Log.d("ProfileViewModel", friendship.toString())
            when (friendship.status) {
                None -> FriendshipState.None
                SentByMe -> FriendshipState.SentByMe
                SentByUser -> FriendshipState.SentByUser
                AcceptedWithNotifications -> FriendshipState.AcceptedWithNotifications
                AcceptedWithoutNotifications -> FriendshipState.AcceptedWithoutNotifications
            }
        }
    }
}