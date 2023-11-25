package com.example.geomate.ui.screens.friends

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.FriendshipRepository
import com.example.geomate.data.repositories.UsersRepository
import com.example.geomate.localsearch.Abbreviation
import com.example.geomate.localsearch.Contains
import com.example.geomate.localsearch.Rule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val usersRepository: UsersRepository,
    private val friendshipRepository: FriendshipRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    private val searchRules: List<Rule> = listOf(Contains, Abbreviation)

    fun fetchFriends() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            friendshipRepository.getFriendsAsFlow().collect { friends ->
                _uiState.update {
                    it.copy(
                        friends = friends.associateWith { Uri.EMPTY },
                        matchedFriends = friends,
                        isLoading = false,
                    )
                }
                _uiState.update {
                    it.copy(
                        friends = friends.associateWith { friend ->
                            usersRepository.getProfilePicture(friend.uid)
                        },
                    )
                }
            }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        val friends = uiState.value.friends.filter { pair ->
            listOf("${pair.key.firstName} ${pair.key.lastName}", pair.key.username).any { userInfo ->
                searchRules.any { rule -> rule.match(userInfo, searchQuery) }
            }
        }
        _uiState.update {
            it.copy(
                searchQuery = searchQuery,
                matchedFriends = friends.keys.toList(),
            )
        }
    }

    fun removeFriend(friend: User) {
        viewModelScope.launch {
            friendshipRepository.remove(friend.uid)
        }
    }
}