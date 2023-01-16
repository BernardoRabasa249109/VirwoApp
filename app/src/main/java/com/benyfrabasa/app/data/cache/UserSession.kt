package com.benyfrabasa.app.data.cache

import com.benyfrabasa.app.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var user: User? = null
}
