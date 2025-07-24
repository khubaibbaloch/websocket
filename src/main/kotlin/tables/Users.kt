package tables

import org.jetbrains.exposed.sql.Table

/**
 * Defines the 'users' table schema using Exposed DSL.
 * Stores user credentials and online status.
 */
object Users : Table("users") {
    val id = integer("id").autoIncrement() // Auto-incrementing primary key
    val userId = varchar("user_id", 64).uniqueIndex() // Unique UID for app-level identification
    val username = varchar("username", 50).uniqueIndex() // Unique username for login or display
    val password = varchar("password", 64) // Encrypted or hashed password
    val isOnline = bool("is_online").default(false) // Tracks whether the user is currently online
    override val primaryKey = PrimaryKey(id) // Sets 'id' as the primary key
}
