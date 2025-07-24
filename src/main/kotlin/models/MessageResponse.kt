package models

import kotlinx.serialization.Serializable

/**
 * Represents a message that is sent from one user to another, including metadata.
 * - id: Unique identifier of the message in the database
 * - sender: User ID of the message sender
 * - receiver: User ID of the message receiver
 * - message: Text content of the message
 * - seen: Status of the message ("sent", "delivered", "seen")
 * - timestamp: Unix timestamp representing when the message was sent
 */
@Serializable
data class MessageResponse(
    val id: Int,
    val sender: String,
    val receiver: String,
    val message: String,
    val seen: String,
    val timestamp: Long
)
