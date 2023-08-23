package com.example.geomate.service.bucket

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseBucketService(private val bucket: FirebaseStorage) : BucketService {
    override suspend fun get(path: String): Uri? {
        val reference = bucket.reference.child("images/$path")
        return try {
            reference.downloadUrl.await()
        } catch (e: Exception) { null }
    }

    override fun store(path: String, uri: Uri) {
        val reference = bucket.reference.child("images/$path")
        reference.putFile(uri)
    }
}