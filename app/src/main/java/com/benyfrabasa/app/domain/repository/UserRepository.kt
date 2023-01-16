package com.benyfrabasa.app.domain.repository

import com.benyfrabasa.app.domain.model.User

interface UserRepository {
    suspend fun getUserByCredentials(email: String, password: String): User?
    suspend fun insertUser(user: User)
    fun setUserSession(user: User)
    fun getUserSession(): User?
}
