package com.example.geomate.ui.screens.selectfriend

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.FriendshipRepository
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectFriendViewModel(
    private val usersRepository: UsersRepository,
    private val friendshipRepository: FriendshipRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    fun fetchFriends(groupId: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            friendshipRepository.getFriendsNotFromGroupAsFlow(groupId).collect { friends ->
                _uiState.update {
                    it.copy(
                        friends = friends.associateWith { Uri.EMPTY },
                        matchedFriends = friends,
                        isLoading = false
                    )
                }
                _uiState.update {
                    it.copy(
                        friends = friends.associateWith { user ->
                            usersRepository.getProfilePicture(user.uid)
                        }
                    )
                }
            }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = searchQuery,
                matchedFriends = uiState.value.friends.keys.filter { friend ->
                    filter(friend, searchQuery)
                }
            )
        }
    }

    private fun filter(friend: User, searchQuery: String): Boolean {
        return true // TODO: Implement
    }

    fun addFriendToGroup(friend: User) {
        // TODO: Add friend to a group
        Log.d("asdqwe", "onIconButton: Add friend to a group")
    }
}