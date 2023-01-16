package com.benyfrabasa.app.domain.useCases

import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByCredentials @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun runUseCase(email: String, password: String): User? {
        return repository.getUserByCredentials(email, password)
    }
}
