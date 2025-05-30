package com.composeapp.compose

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String = "",
    var password: String = ""
)