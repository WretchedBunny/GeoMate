package com.example.geomate.statemachine

import com.example.geomate.data.models.FriendshipRequest
import com.example.geomate.data.models.FriendshipStatus
import com.example.geomate.data.repositories.FriendshipRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Date

sealed class FriendshipState {
    class None(private val friendshipRepository: FriendshipRepository) : FriendshipState() {
        suspend fun send(friendId: String) {
            friendshipRepository.add(
                FriendshipRequest(
                    createdAt = Date(),
                    senderHasNotifications = true,
                    recipientHasNotifications = true,
                    senderId = Firebase.auth.uid.toString(),
                    recipientId = friendId,
                    status = FriendshipStatus.Sent,
                )
            )
        }
    }

    class SentByMe(private val friendshipRepository: FriendshipRepository) : FriendshipState() {
        suspend fun revoke(friendId: String) {
            friendshipRepository.remove(friendId)
        }
    }

    class SentByUser(private val friendshipRepository: FriendshipRepository) : FriendshipState() {
        suspend fun accept(friendId: String) =
            friendshipRepository.updateStatus(friendId, FriendshipStatus.Accepted)

        suspend fun decline(friendId: String) =
            friendshipRepository.remove(friendId)
    }

    interface Accepted {
        suspend fun remove(userId: String)
    }

    class AcceptedWithNotifications(private val friendshipRepository: FriendshipRepository) :
        FriendshipState(), Accepted {
        suspend fun turnOffNotifications(userId: String) {
            friendshipRepository.updateCurrentUserNotifications(userId, false)
        }

        override suspend fun remove(userId: String) = friendshipRepository.remove(userId)
    }

    class AcceptedWithoutNotifications(private val friendshipRepository: FriendshipRepository) :
        FriendshipState(), Accepted {
        suspend fun turnOnNotifications(userId: String) {
            friendshipRepository.updateCurrentUserNotifications(userId, true)
        }

        override suspend fun remove(userId: String) = friendshipRepository.remove(userId)
    }
}
