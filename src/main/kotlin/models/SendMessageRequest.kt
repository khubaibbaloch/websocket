package models

import kotlinx.serialization.Serializable

/**
 * Represents the structure of a message sent over the network.
 * This data class is used for sending messages through WebSocket or HTTP.
 *
 * @property sender The user ID of the sender.
 * @property receiver The user ID of the receiver.
 * @property message The text content of the message.
 * @property timestamp The UNIX time (in milliseconds) when the message was sent.
 */
@Serializable
data class SendMessageRequest(
    val sender: String,
    val receiver: String,
    val message: String,
    val timestamp: Long
)
