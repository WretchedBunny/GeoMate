package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.Group
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.FriendshipRepository
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import com.example.geomate.localsearch.Abbreviation
import com.example.geomate.localsearch.Contains
import com.example.geomate.localsearch.Rule
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class GroupDetailsViewModel(
    private val usersRepository: UsersRepository,
    private val groupsRepository: GroupsRepository,
    private val friendshipRepository: FriendshipRepository,
) : ViewModel() {
    private val _groupDetailsUiState = MutableStateFlow(GroupDetailsUiState())
    val groupDetailsUiState: StateFlow<GroupDetailsUiState> = _groupDetailsUiState.asStateFlow()

    private val _selectFriendUiState = MutableStateFlow(SelectFriendUiState())
    val selectFriendUiState: StateFlow<SelectFriendUiState> = _selectFriendUiState.asStateFlow()

    private val searchRules: List<Rule> = listOf(Contains, Abbreviation)

    fun fetchGroup(groupId: String) {
        _groupDetailsUiState.update { it.copy(isLoading = true) }

        if (groupId.isEmpty()) {
            _groupDetailsUiState.update {
                it.copy(
                    groupId = "",
                    name = "",
                    users = emptyMap(),
                    isLoading = false
                )
            }
            return
        }

        viewModelScope.launch {
            groupsRepository.getSingleAsFlow(groupId).collect { group ->
                if (group == null) {
                    return@collect
                }

                usersRepository.getAllAsFlow(group.users).collect { users ->
                    _groupDetailsUiState.update {
                        it.copy(
                            groupId = groupId,
                            name = group.name,
                            users = users.associateWith { Uri.EMPTY },
                            isLoading = false,
                        )
                    }
                    _groupDetailsUiState.update {
                        it.copy(
                            users = users.associateWith { user ->
                                usersRepository.getProfilePicture(user.uid)
                            }
                        )
                    }
                }
            }
        }
    }

    fun fetchFriends() {
        _selectFriendUiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            friendshipRepository.getFriendsAsFlow().collect { value ->
                val friends = value.toSet().minus(groupDetailsUiState.value.users.keys).toList()
                _selectFriendUiState.update {
                    it.copy(
                        friends = friends.associateWith { Uri.EMPTY },
                        matchedFriends = friends,
                        isLoading = false
                    )
                }
                _selectFriendUiState.update {
                    it.copy(
                        friends = friends.associateWith { user ->
                            usersRepository.getProfilePicture(user.uid)
                        }
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        _groupDetailsUiState.update { it.copy(name = name) }
    }

    fun addUser(user: User, profilePicture: Uri) {
        val users = groupDetailsUiState.value.users.toMutableMap()
        users[user] = profilePicture
        _groupDetailsUiState.update { it.copy(users = users) }
    }

    fun removeUser(user: User) {
        val users = groupDetailsUiState.value.users.toMutableMap()
        users.remove(user)
        _groupDetailsUiState.update { it.copy(users = users) }
    }

    fun create() {
        viewModelScope.launch {
            groupsRepository.add(
                Group(
                    uid = UUID.randomUUID().toString(),
                    owner = Firebase.auth.uid ?: "",
                    name = groupDetailsUiState.value.name,
                    users = groupDetailsUiState.value.users.keys.map { it.uid }.toList()
                )
            )
        }
    }

    fun update() {
        viewModelScope.launch {
            groupsRepository.update(
                groupId = groupDetailsUiState.value.groupId,
                name = groupDetailsUiState.value.name,
                users = groupDetailsUiState.value.users.keys.map { it.uid }
            )
        }
    }

    fun toggleSelectFriendVisibility() {
        _selectFriendUiState.update { it.copy(visible = !selectFriendUiState.value.visible) }
    }

    fun updateSearchQuery(searchQuery: String) {
        _selectFriendUiState.update {
            it.copy(
                searchQuery = searchQuery,
                matchedFriends = selectFriendUiState.value.friends.keys.filter { friend ->
                    listOf("${friend.firstName} ${friend.lastName}", friend.username).any { friendInfo ->
                        searchRules.any { rule -> rule.match(friendInfo, searchQuery) }
                    }
                }
            )
        }
    }
}