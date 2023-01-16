package com.benyfrabasa.app.domain.useCases

import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.repository.UserRepository
import javax.inject.Inject

class InsertUser @Inject constructor(
    private var repository: UserRepository
) {
    suspend fun runUseCase(user: User) {
        repository.insertUser(user)
    }
}
