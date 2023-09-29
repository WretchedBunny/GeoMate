package com.example.geomate.ext

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listener = addSnapshotListener { value, error ->
        Log.d("asdqwe", "snapshotFlow produced: ${value?.documents?.map { it.id }}, $error")
        error?.let { close(); return@addSnapshotListener}
        value?.let { trySend(it) }
    }
    awaitClose { listener.remove() }
}

fun DocumentReference.snapshotFlow(): Flow<DocumentSnapshot> = callbackFlow {
    val listener = addSnapshotListener { value, error ->
        error?.let { close(); return@addSnapshotListener}
        value?.let { trySend(it) }
    }
    awaitClose { listener.remove() }
}