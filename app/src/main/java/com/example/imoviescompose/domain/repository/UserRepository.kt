package com.example.imoviescompose.domain.repository

import com.example.imoviescompose.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun updateUser(user: User): Flow<Boolean>

    suspend fun getUser(): Flow<User?>
}