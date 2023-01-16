package com.benyfrabasa.app.domain.useCases

import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.repository.UserRepository
import javax.inject.Inject

class GetUserSession @Inject constructor(
    private val repository: UserRepository
) {
    fun runUseCase(): User? {
        return repository.getUserSession()
    }
}
