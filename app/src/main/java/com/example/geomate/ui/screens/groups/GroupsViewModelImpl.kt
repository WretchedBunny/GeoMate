package com.example.geomate.ui.screens.groups

import androidx.lifecycle.ViewModel
import com.example.geomate.model.Group
import com.example.geomate.model.User
import com.example.geomate.service.bucket.BucketService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val groupsWithUris: List<GroupWithUris> = listOf(
    GroupWithUris(
        group = Group(
            name = "Family",
            users = listOf(
                User(uid = "3fOeWU9LHnTCNf2TxbbCGnbik0F2"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
            ),
        )
    ),
    GroupWithUris(
        group = Group(
            name = "University",
            users = listOf(
                User(uid = "onYpZ3NG6cSraGnZCSCC4j0JRg73"),
                User(uid = "3fOeWU9LHnTCNf2TxbbCGnbik0F2"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
            ),
        )
    ),
    GroupWithUris(
        group = Group(
            name = "Football team asdf asdf asdf asdf",
            users = listOf(
                User(uid = "onYpZ3NG6cSraGnZCSCC4j0JRg73"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
                User(uid = "3fOeWU9LHnTCNf2TxbbCGnbik0F2"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
                User(uid = "tiBC6jmerjbXyv4JdNADqL8BZso1"),
            ),
        )
    ),
)

class GroupsViewModelImpl(
    private val bucketService: BucketService
) : ViewModel(), GroupsViewModel {
    private val _uiState = MutableStateFlow(GroupsUiState(groupsWithUris = groupsWithUris))
    override val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    override fun updateSearchQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    override suspend fun fetchProfilePictures(groupsWithUris: List<GroupWithUris>) {
        val groupsWithUrisCopy = uiState.value.groupsWithUris.map { groupWithUris ->
            groupWithUris.copy(uris = groupWithUris.group.users.map { bucketService.get(it.uid) })
        }
        _uiState.update { it.copy(groupsWithUris = groupsWithUrisCopy) }
    }

    override fun removeGroup(group: Group): Boolean {
        TODO("Not yet implemented")
    }
}