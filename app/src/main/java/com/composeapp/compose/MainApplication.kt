package com.composeapp.compose

import android.app.Application
import com.composeapp.compose.db.UserDB

class MainApplication : Application() {
    // Lazy initialization of the database
    private val database by lazy { UserDB.getDatabase(this) }

    // Lazy initialization of the UserModel
    private val userModel by lazy { UserModel(this) }

    companion object {
        private lateinit var instance: MainApplication

        fun getInstance(): MainApplication = instance

        // Accessor for UserModel
        fun getUserModel(): UserModel = instance.userModel

        // Accessor for Database
        fun getDatabase(): UserDB = instance.database
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}