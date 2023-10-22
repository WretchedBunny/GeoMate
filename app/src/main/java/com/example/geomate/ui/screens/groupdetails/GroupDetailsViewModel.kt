package com.example.geomate.ui.screens.groupdetails

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroupDetailsViewModel(
    private val usersRepository: UsersRepository,
    private val groupsRepository: GroupsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState: StateFlow<GroupDetailsUiState> = _uiState.asStateFlow()

    fun fetchGroup(groupId: String) = viewModelScope.launch {
        groupsRepository.getSingleAsFlow(groupId).collect { groupOrNull ->
            groupOrNull?.let { group ->
                usersRepository.getAllAsFlow(group.users).collect { users ->
                    _uiState.update { it.copy(
                        name = group.name,
                        users = users.associateWith { Uri.EMPTY }.toMutableMap()
                    ) }
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun removeUser(groupId: String, userId: String) = viewModelScope.launch {
        groupsRepository.removeUser(groupId, userId)
    }
}