package com.example.imoviescompose.domain.use_case.get_user

import com.example.imoviescompose.domain.model.user.User
import com.example.imoviescompose.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<User?> {
        return repository.getUser()
    }
}