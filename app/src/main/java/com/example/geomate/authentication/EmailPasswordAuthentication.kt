package com.example.geomate.service.authentication

import android.net.Uri
import com.example.geomate.authentication.Authentication
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class EmailPasswordAuthentication(
    private val email: String,
    private val password: String,
    private val username: String = "",
    private val firstName: String = "",
    private val lastName: String = "",
    private val bio: String = "",
    private val uri: Uri? = null,
    private val usersRepository: UsersRepository,
) : Authentication {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(): FirebaseUser? = try {
        auth.signInWithEmailAndPassword(email, password).await().user
    } catch (e: Exception) {
        null
    }

    override suspend fun signUp(): FirebaseUser? = try {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user
        user?.let { firebaseUser ->
            usersRepository.add(createUser(firebaseUser))
            uri?.let { usersRepository.addProfilePicture(user.uid, it) }
        }
        user
    } catch (e: Exception) {
        null
    }

    override suspend fun signOut() = auth.signOut()

    override fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = username,
        firstName = firstName,
        lastName = lastName,
        bio = bio
    )
}