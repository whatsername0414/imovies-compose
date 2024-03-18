package com.example.imoviescompose.domain.use_case.update_user

import com.example.imoviescompose.domain.model.user.User
import com.example.imoviescompose.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Flow<Boolean> {
        return repository.updateUser(user)
    }
}