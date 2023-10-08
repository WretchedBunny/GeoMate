package com.example.geomate.statemachine

sealed class FriendshipState {
    object None : FriendshipState() {
        fun send() {}
    }
    object SentByMe : FriendshipState() {
        fun revoke() {}
    }
    object SentByUser : FriendshipState() {
        fun accept() {}
        fun decline() {}
    }
    interface Accepted {
        fun remove() {}
    }
    object AcceptedWithNotifications : FriendshipState(), Accepted {
        fun turnOffNotifications() {}
    }
    object AcceptedWithoutNotifications : FriendshipState(), Accepted {
        fun turnOnNotifications() {}
    }
}
