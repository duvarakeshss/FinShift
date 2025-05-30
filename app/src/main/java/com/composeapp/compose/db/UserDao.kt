package com.composeapp.compose.db

import androidx.room.*
import com.composeapp.compose.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleUsers(users: List<User>)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): User?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    suspend fun isUsernameExists(username: String): Boolean

    @Query("SELECT COUNT(*) FROM users")
    fun getUserCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getUsersSortedByUsername(): Flow<List<User>>
}