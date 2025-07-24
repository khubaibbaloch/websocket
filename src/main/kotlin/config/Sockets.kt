package com.example.config

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds

/**
 * Configures WebSocket settings for the Ktor application.
 * Sets up ping intervals, timeout, and other low-level frame settings.
 */
fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        // Sends a ping every 15 seconds to keep the connection alive

        timeout = 15.seconds
        // Closes the connection if no pong or data is received within this period

        maxFrameSize = Long.MAX_VALUE
        // Allows maximum frame size for large messages (like images or long texts)

        masking = false
        // Disables masking; recommended to keep false on the server side (clients usually mask)
    }
}
