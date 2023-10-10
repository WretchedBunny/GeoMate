package com.example.geomate.ui.screens.groups

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geomate.data.models.Group
import com.example.geomate.data.repositories.GroupsRepository
import com.example.geomate.data.repositories.UsersRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private val _matchGroups: MutableStateFlow<MutableMap<Group, List<Uri>>> =
        MutableStateFlow(mutableMapOf())
    val matchGroups: StateFlow<MutableMap<Group, List<Uri>>> = _matchGroups.asStateFlow()
    fun fetchGroups(userId: String) = viewModelScope.launch {
        groupsRepository.getAll(userId).collect { groups ->
            _uiState.update {
                it.copy(
                    groups = groups.associateWith { group ->
                        List(group.users.size) {
                            Uri.EMPTY
                        }
                    }.toMutableMap()
                )
            }
            updateMatchGroups(_uiState.value.groups)
        }
    }

    private fun fetchMatchGroups() = viewModelScope.launch {
        uiState.collect {
            if (uiState.value.searchQuery.isBlank()) {
                Firebase.auth.uid?.let { fetchGroups(it) }
            } else {
                val groups = matchGroups.value.filterKeys {
                    it.name.contains(
                        uiState.value.searchQuery,
                        true
                    )
                }.toMutableMap()
                updateMatchGroups(groups)
                updateGroups(groups)
            }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
        fetchMatchGroups()
    }

    fun updateGroups(group: MutableMap<Group, List<Uri>>) {
        _uiState.update { it.copy(groups = group) }
    }

    fun updateMatchGroups(matchGroup: MutableMap<Group, List<Uri>>) {
        _matchGroups.update { matchGroup }
    }

    suspend fun fetchProfilePictures() {
        // TODO: Implement
    }

    fun removeGroup(group: Group) = viewModelScope.launch {
        groupsRepository.remove(group)
    }
}