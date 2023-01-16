package com.benyfrabasa.app.data.repository

import com.benyfrabasa.app.data.cache.UserSession
import com.benyfrabasa.app.data.database.UserDao
import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val userSession: UserSession
): UserRepository {
    override suspend fun getUserByCredentials(email: String, password: String): User? {
        return dao.getUserByCredentials(email, password)
    }

    override suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    override fun setUserSession(user: User) {
        userSession.user = user
    }

    override fun getUserSession(): User? {
        return userSession.user
    }
}
