package com.benyfrabasa.app.domain.useCases

import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.repository.UserRepository
import javax.inject.Inject

class SetUserSession @Inject constructor(
    private val repository: UserRepository
) {
    fun runUseCase(user: User) {
        return repository.setUserSession(user)
    }
}
