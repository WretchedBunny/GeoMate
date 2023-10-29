package com.example.geomate.ui.screens.selectfriend

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectFriendViewModel(
    private val usersRepository: UsersRepository,
    private val groupsRepository: GroupsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    fun fetchFriends(groupId: String) {
        _uiState.update { it.copy(isLoading = true) }
        // TODO: Fetch all friends that are not already in the group (from uiState)
        Log.d("asdqwe", "fetchFriends: Fetching friends that are not in the group")
        _uiState.update { it.copy(isLoading = false) }
    }

    fun updateSearchQuery(searchQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = searchQuery,
                matchedFriends = uiState.value.friends.filter { pair ->
                    filter(pair.key, searchQuery)
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