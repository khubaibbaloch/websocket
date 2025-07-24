package models

import kotlinx.serialization.Serializable

/**
 * Represents user credentials sent during signup or login requests.
 * - username: Unique identifier chosen by the user
 * - password: Plaintext or hashed password submitted for authentication
 */
@Serializable
data class UserRequest(
    val username: String,
    val password: String
)
