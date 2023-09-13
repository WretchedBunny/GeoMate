package com.example.geomate.model

import android.net.Uri

data class GroupWithUris(
    val group: Group = Group(),
    var uris: List<Uri?> = List(group.users.size) { Uri.EMPTY },
)
