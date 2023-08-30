package com.example.geomate.service.bucket

import android.net.Uri

class BucketServiceMock : BucketService {
    override suspend fun get(path: String): Uri? { TODO() }
    override fun store(path: String, uri: Uri) { TODO() }
}