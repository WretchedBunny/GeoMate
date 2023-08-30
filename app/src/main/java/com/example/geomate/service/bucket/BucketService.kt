package com.example.geomate.service.bucket

import android.net.Uri

interface BucketService {
    suspend fun get(path: String): Uri?
    fun store(path: String, uri: Uri)
}