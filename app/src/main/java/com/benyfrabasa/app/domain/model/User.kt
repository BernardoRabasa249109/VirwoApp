package com.benyfrabasa.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val user: String? = null,
    val email: String? = null,
    val password: String? = null
)
