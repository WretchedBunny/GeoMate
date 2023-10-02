package com.example.geomate.authentication

import android.net.Uri
import com.example.geomate.data.models.User
import com.example.geomate.data.repositories.UsersRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EmailAndPasswordSignUp(
    private val email: String,
    private val password: String,
    private val username: String,
    private val firstName: String,
    private val lastName: String,
    private val bio: String,
    private val uri: Uri?,
    private val usersRepository: UsersRepository,
) : SignUp {
    private val auth = Firebase.auth

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

    override fun createUser(user: FirebaseUser): User = User(
        uid = user.uid,
        email = user.email ?: "",
        username = username,
        firstName = firstName,
        lastName = lastName,
        bio = bio
    )
}