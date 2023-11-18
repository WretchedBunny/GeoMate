package com.example.geomate.ui.screens.groupdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.Group
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class GroupDetailsViewModel(
    private val usersRepository: UsersRepository,
    private val groupsRepository: GroupsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState: StateFlow<GroupDetailsUiState> = _uiState.asStateFlow()

    fun fetchGroup(groupId: String) {
        _uiState.update { it.copy(isLoading = true) }

        if (groupId.isEmpty()) {
            _uiState.update { it.copy(isLoading = false) }
            return
        }

        viewModelScope.launch {
            groupsRepository.getSingleAsFlow(groupId).collect { group ->
                if (group == null) {
                    return@collect
                }

                usersRepository.getAllAsFlow(group.users).collect { value ->
                    val users = value.associateWith { user ->
                        usersRepository.getProfilePicture(user.uid)
                    }
                    _uiState.update { it.copy(groupId = groupId, name = group.name, users = users) }
                }
            }
        }

        _uiState.update { it.copy(isLoading = false) }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun removeUser(userId: String) {
        val users = uiState.value.users.toMutableMap()
        val user = users.keys.find { user -> user.uid == userId }
        users.remove(user)
        _uiState.update { it.copy(users = users) }
    }

    fun create() {
        viewModelScope.launch {
            groupsRepository.add(
                Group(
                    uid = UUID.randomUUID().toString(),
                    name = uiState.value.name,
                    users = uiState.value.users.keys.map { it.uid }.toList()
                )
            )
        }
    }

    fun update() {
        viewModelScope.launch {
            groupsRepository.update(
                groupId = uiState.value.groupId,
                name = uiState.value.name,
                users = uiState.value.users.keys.map { it.uid }
            )
        }
    }
}