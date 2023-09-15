package com.example.geomate.ui.screens.groups

import android.net.Uri
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

class GroupsViewModel(
    private val usersRepository: UsersRepository,
    private val groupsRepository: GroupsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    fun fetchGroups(userId: String) = viewModelScope.launch {
        groupsRepository.getAll(userId).collect { groups ->
            _uiState.update {
                it.copy(
                    groups = groups.associateWith { group ->
                        List(group.users.size) { Uri.EMPTY }
                    }.toMutableMap()
                )
            }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    suspend fun fetchProfilePictures() {
        // TODO: Implement
    }

    fun removeGroup(group: Group) = viewModelScope.launch {
        groupsRepository.remove(group)
    }
}