package com.example.geomate.service.bucket

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage

class FirebaseBucketService(private val bucket: FirebaseStorage) : BucketService {
    override fun get(path: String): Uri {
        val reference = bucket.reference.child("images/$path")
        return reference.downloadUrl.result
    }

    override fun store(path: String, uri: Uri) {
        val reference = bucket.reference.child("images/$path")
        reference.putFile(uri)
    }
}