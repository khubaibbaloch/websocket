package com.example.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configures JSON serialization using kotlinx.serialization.
 * Enables automatic conversion between Kotlin objects and JSON.
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json() // Uses kotlinx.serialization to handle JSON automatically
    }

    // Sample route to test JSON serialization
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world")) // Returns a simple JSON response
        }
    }
}
