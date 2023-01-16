package com.benyfrabasa.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benyfrabasa.app.domain.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
}
