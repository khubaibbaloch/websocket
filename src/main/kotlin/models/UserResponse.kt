package models

import kotlinx.serialization.Serializable

/**
 * Represents the response data for a user object.
 * Typically returned after login, registration, or when fetching user lists.
 *
 * @param userId Unique identifier (e.g., UUID) used internally
 * @param username Display name or handle of the user
 * @param isOnline Boolean flag indicating if the user is currently online
 */
@Serializable
data class UserResponse(
    val userId: String,       // UUID format
    val username: String,
    val isOnline: Boolean
)
