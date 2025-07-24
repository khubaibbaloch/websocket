package dao

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import tables.Users

/**
 * Data Access Object for user-related operations using Exposed.
 */
object UserDao {

    /**
     * Creates a new user in the database if the username or userId is not already taken.
     * @return true if user creation succeeds, false if username or userId already exists.
     */
    fun createUser(userId: String, username: String, password: String): Boolean {
        return transaction {
            // Check for existing username or userId
            if (Users.select { Users.username eq username or (Users.userId eq userId) }.count() > 0)
                return@transaction false

            // Insert new user record
            Users.insert {
                it[Users.userId] = userId
                it[Users.username] = username
                it[Users.password] = password
                it[Users.isOnline] = false
            }
            true
        }
    }

    /**
     * Verifies if the username and password combination exists in the database.
     * @return true if credentials match, false otherwise.
     */
    fun verifyUser(username: String, password: String): Boolean {
        return transaction {
            Users.select {
                (Users.username eq username) and (Users.password eq password)
            }.count() > 0
        }
    }

    /**
     * Fetches the userId associated with a given username.
     * @return userId if found, null otherwise.
     */
    fun findUserId(username: String): String? {
        return transaction {
            Users.select { Users.username eq username }
                .map { it[Users.userId] }
                .firstOrNull()
        }
    }

    /**
     * Updates the online status of a user.
     * @param isOnline true to mark user online, false to mark offline.
     */
    fun setUserOnlineStatus(userId: String, isOnline: Boolean) {
        transaction {
            Users.update({ Users.userId eq userId }) {
                it[Users.isOnline] = isOnline
            }
        }
    }

    /**
     * Checks if a user is currently marked as online.
     * @return true if user is online, false if offline or user not found.
     */
    fun isUserOnline(userId: String): Boolean {
        return transaction {
            Users.select { Users.userId eq userId }
                .map { it[Users.isOnline] }
                .firstOrNull() ?: false
        }
    }
}
