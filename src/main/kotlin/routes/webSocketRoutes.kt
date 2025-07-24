package routes

import dao.MessageDao
import dao.UserDao
import io.ktor.server.routing.Route
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import models.SendMessageRequest
import java.util.concurrent.ConcurrentHashMap

/**
 * Defines WebSocket route handlers for real-time messaging.
 */
fun Route.webSocketRoutes() {

    // Thread-safe map to track active user WebSocket sessions by userId
    val userSessions = ConcurrentHashMap<String, DefaultWebSocketServerSession>()

    // Establish WebSocket connection for a user
    webSocket("/ws/{userId}") {
        val userId = call.parameters["userId"] ?: "anonymous_${this.hashCode()}"
        userSessions[userId] = this // Store current session

        // Mark user as online in database
        UserDao.setUserOnlineStatus(userId, true)
        println("User [$userId] connected")

        // Step 1: Retrieve any pending (undelivered) messages for this user
        val pendingMessages = MessageDao.getPendingMessagesForUser(userId)

        // Step 2: Send pending messages to the connected user
        for (pending in pendingMessages) {
            val sendMessage = SendMessageRequest(
                sender = pending.sender,
                receiver = pending.receiver,
                message = pending.message,
                timestamp = pending.timestamp
            )
            val json = Json.encodeToString(sendMessage)
            send(Frame.Text(json))

            // Step 3: Mark message status as delivered after sending
            MessageDao.updateMessageStatus(
                sender = pending.sender,
                receiver = pending.receiver,
                fromStatus = "sent",
                toStatus = "delivered"
            )
        }

        try {
            // Listen for incoming messages from this user
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val jsonText = frame.readText()

                    // Deserialize message; skip if malformed
                    val message = try {
                        Json.decodeFromString<SendMessageRequest>(jsonText)
                    } catch (e: Exception) {
                        println("Invalid JSON: $jsonText")
                        continue
                    }

                    // Save message to database
                    val result = MessageDao.sendMessage(
                        sender = message.sender,
                        receiver = message.receiver,
                        message = message.message
                    )

                    println("Received from ${message.sender}: ${message.message}")

                    // Forward message to receiver if they are online
                    val receiverSession = userSessions[message.receiver]
                    if (receiverSession != null && result) {
                        val responseJson = Json.encodeToString(message)
                        receiverSession.send(Frame.Text(responseJson))

                        // Mark as delivered after successful send
                        MessageDao.updateMessageStatus(
                            sender = message.sender,
                            receiver = message.receiver,
                            fromStatus = "sent",
                            toStatus = "delivered"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // Log session errors
            println("Error in session for user $userId: ${e.localizedMessage}")
        } finally {
            // Cleanup on disconnect: update status and remove session
            UserDao.setUserOnlineStatus(userId, false)
            userSessions.remove(userId)
            println("User [$userId] disconnected")
        }
    }
}
