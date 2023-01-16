package com.benyfrabasa.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benyfrabasa.app.domain.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao

}
