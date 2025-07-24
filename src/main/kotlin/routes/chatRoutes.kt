package routes

import dao.MessageDao
import dao.UserDao
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import models.*

/**
 * Defines all HTTP routes related to chat and user operations.
 */
fun Route.chatRoutes() {

    // 🔐 User Signup
    // Creates a new user account with a generated UUID. Responds with user details on success.
    post("/signup") {
        val request = call.receive<UserRequest>()
        val userId = java.util.UUID.randomUUID().toString()
        val success = UserDao.createUser(userId, request.username, request.password)

        if (success) {
            call.respond(HttpStatusCode.OK, UserResponse(userId, request.username, false))
        } else {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }
    }

    // 🔐 User Login
    // Verifies provided username and password. Returns user info with online status on success.
    post("/login") {
        val request = call.receive<UserRequest>()
        val verified = UserDao.verifyUser(request.username, request.password)

        if (verified) {
            val userId = UserDao.findUserId(request.username)
            call.respond(
                HttpStatusCode.OK,
                UserResponse(userId = userId.toString(), username = request.username, isOnline = true)
            )
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    // 🔄 Update User Online Status
    // Allows client to set the user's online or offline status manually.
    post("/user/status") {
        val request = call.receive<UserStatusUpdateRequest>()
        UserDao.setUserOnlineStatus(request.userId, request.isOnline)
        call.respond(HttpStatusCode.OK, "Status updated")
    }

    // 📤 Send Message
    // Saves a message from sender to receiver in the database.
    post("/message/send") {
        val request = call.receive<SendMessageRequest>()
        val result = MessageDao.sendMessage(
            sender = request.sender,
            receiver = request.receiver,
            message = request.message
        )
        call.respond(HttpStatusCode.OK, mapOf("success" to result))
    }

    // ✅ Mark Messages as Seen
    // Updates all messages from sender to receiver as "seen".
    post("/message/seen") {
        val request = call.receive<MarkAsSeenRequest>()
        MessageDao.markMessagesAsSeen(request.sender, request.receiver)
        call.respond(HttpStatusCode.OK, "Messages marked as seen")
    }

    // 📜 Get Message History
    // Retrieves all messages exchanged between two users (sender and receiver).
    get("/messages/{sender}/{receiver}") {
        val sender = call.parameters["sender"]!!
        val receiver = call.parameters["receiver"]!!
        val messages = MessageDao.getMessagesBetween(sender, receiver)
        call.respond(HttpStatusCode.OK, messages)
    }

    // 🟢 Get User Online Status
    // Returns the current online status of a specific user.
    get("/user/status/{userId}") {
        val userId = call.parameters["userId"]!!
        val online = UserDao.isUserOnline(userId)
        call.respond(HttpStatusCode.OK, mapOf("isOnline" to online))
    }
}
