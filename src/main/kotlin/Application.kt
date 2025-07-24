package com.example

import com.example.config.configureDatabase
import com.example.config.configureSerialization
import com.example.config.configureSockets
import routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
//    configureSecurity()
//    configureHTTP()
//    configureMonitoring()
//    configureSerialization()
    configureDatabase()
    configureSockets()
    configureSerialization()
    configureRouting()


}
