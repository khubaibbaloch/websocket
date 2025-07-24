package com.example.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Messages
import tables.Users

/**
 * Configures the database connection using HikariCP and Exposed ORM.
 * Initializes schema and ensures required tables exist.
 */
fun Application.configureDatabase() {
    // HikariCP connection pool configuration
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/ktor_chat" // PostgreSQL connection string
        driverClassName = "org.postgresql.Driver"
        username = "postgres"
        password = "Khubaib@301030"
        maximumPoolSize = 5 // Limits concurrent DB connections
        isAutoCommit = false // Manual transaction handling
        transactionIsolation = "TRANSACTION_REPEATABLE_READ" // Ensures consistency during reads
    }

    // Initialize Hikari data source with the config
    val dataSource = HikariDataSource(config)

    // Connect Exposed to the database via the data source
    Database.connect(dataSource)

    // Create Users and Messages tables if they don't already exist
    transaction {
        SchemaUtils.create(Users, Messages)
    }
}
