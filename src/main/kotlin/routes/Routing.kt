package routes

import dao.MessageDao
import dao.UserDao
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

/**
 * Configures routing for the Ktor application.
 * This is the main entry point for defining all application routes.
 */
fun Application.configureRouting() {
    routing {
        // A simple health check or welcome endpoint at the root path.
        get("/") {
            call.respondText("Hello World!") // Responds with plain text when root is accessed.
        }

        // Registers HTTP-based chat-related routes (defined elsewhere).
        chatRoutes()

        // Registers WebSocket-based routes for real-time communication.
        webSocketRoutes()
    }
}
