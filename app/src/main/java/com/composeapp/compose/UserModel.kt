package com.composeapp.compose

import android.content.Context
import com.composeapp.compose.db.UserDB
import kotlinx.coroutines.flow.Flow

class UserModel(context: Context) {
    internal val userDao = UserDB.getDatabase(context).userDao()

    // Get all users as a Flow
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    // Get user by ID
    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    // Insert or update user
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    // Create new user
    suspend fun createUser(username: String, password: String) {
        val user = User(
            username = username,
            password = password
        )
        insertUser(user)
    }
}