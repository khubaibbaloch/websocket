package models

import kotlinx.serialization.Serializable

/**
 * Request model to update a user's online/offline status.
 * Typically used when a user connects or disconnects from a WebSocket.
 *
 * @param userId Unique identifier (UUID) of the user
 * @param isOnline True if the user is online, false if offline
 */
@Serializable
data class UserStatusUpdateRequest(
    val userId: String,
    val isOnline: Boolean
)
