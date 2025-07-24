package models

import kotlinx.serialization.Serializable

/**
 * Data class used for marking all messages from a specific sender to a receiver as 'seen'.
 * - sender: the user who sent the messages
 * - receiver: the user who received and is now marking them as seen
 */
@Serializable
data class MarkAsSeenRequest(
    val sender: String,
    val receiver: String
)
