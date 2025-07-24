package dao

import models.MessageResponse
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import tables.Messages
import java.time.Instant

/**
 * DAO for interacting with the Messages table.
 * Handles message sending, retrieval, and status updates.
 */
object MessageDao {

    /**
     * Inserts a new message into the Messages table with status "sent".
     * @return true if insertion succeeds (always returns true currently).
     */
    fun sendMessage(sender: String, receiver: String, message: String): Boolean {
        return transaction {
            Messages.insert {
                it[Messages.sender] = sender
                it[Messages.receiver] = receiver
                it[Messages.message] = message
                it[Messages.status] = "sent" // Initial status
                it[Messages.timestamp] = Instant.now().toEpochMilli()
            }
            true
        }
    }

    /**
     * Retrieves all messages exchanged between two users, regardless of direction.
     * @return A list of maps, each representing a message record.
     */
    fun getMessagesBetween(user1: String, user2: String): List<Map<String, Any>> {
        return transaction {
            Messages.select {
                ((Messages.sender eq user1) and (Messages.receiver eq user2)) or
                        ((Messages.sender eq user2) and (Messages.receiver eq user1))
            }.map {
                mapOf(
                    "id" to it[Messages.id],
                    "sender" to it[Messages.sender],
                    "receiver" to it[Messages.receiver],
                    "message" to it[Messages.message],
                    "status" to it[Messages.status],
                    "timestamp" to it[Messages.timestamp]
                )
            }
        }
    }

    /**
     * Updates the status of all messages from a sender to a receiver that match a specific status.
     * Used to mark messages as delivered or seen.
     */
    fun updateMessageStatus(sender: String, receiver: String, fromStatus: String, toStatus: String) {
        transaction {
            Messages.update({
                (Messages.sender eq sender) and
                        (Messages.receiver eq receiver) and
                        (Messages.status eq fromStatus)
            }) {
                it[status] = toStatus
            }
        }
    }

    /**
     * Marks all delivered messages from a sender to a receiver as "seen".
     */
    fun markMessagesAsSeen(sender: String, receiver: String) {
        updateMessageStatus(sender, receiver, "delivered", "seen")
    }

    /**
     * Marks all sent messages from a sender to a receiver as "delivered".
     */
    fun markMessagesAsDelivered(sender: String, receiver: String) {
        updateMessageStatus(sender, receiver, "sent", "delivered")
    }

    /**
     * Retrieves all pending (not yet delivered) messages for a specific user.
     * Used when a user connects via WebSocket to fetch missed messages.
     * @return A list of MessageResponse objects ordered by timestamp (oldest first).
     */
    fun getPendingMessagesForUser(userId: String): List<MessageResponse> {
        return transaction {
            Messages.select {
                (Messages.receiver eq userId) and (Messages.status eq "sent")
            }.orderBy(Messages.timestamp to SortOrder.ASC).map {
                MessageResponse(
                    id = it[Messages.id],
                    sender = it[Messages.sender],
                    receiver = it[Messages.receiver],
                    message = it[Messages.message],
                    seen = it[Messages.status], // Holds current status (e.g., sent, delivered)
                    timestamp = it[Messages.timestamp]
                )
            }
        }
    }
}
