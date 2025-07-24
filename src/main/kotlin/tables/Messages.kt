package tables

import org.jetbrains.exposed.sql.Table

/**
 * Table definition for storing chat messages.
 * Uses Exposed DSL to define schema for the "messages" table.
 */
object Messages : Table("messages") {

    // Auto-incremented primary key for each message
    val id = integer("id").autoIncrement()

    // ID of the user who sent the message
    val sender = varchar("sender", 50)

    // ID of the user who receives the message
    val receiver = varchar("receiver", 50)

    // Content of the message, limited to 500 characters
    val message = varchar("message", 500)

    // Status of the message: "sent", "delivered", or "seen"
    val status = varchar("status", 20).default("sent")

    // UNIX timestamp (in milliseconds) of when the message was sent
    val timestamp = long("timestamp")

    // Primary key constraint on the `id` column
    override val primaryKey = PrimaryKey(id)
}
