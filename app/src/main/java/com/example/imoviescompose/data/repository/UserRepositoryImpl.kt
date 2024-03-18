package com.example.imoviescompose.data.repository

import com.example.imoviescompose.data.local.UserDao
import com.example.imoviescompose.domain.mapper.UserMapper.toUser
import com.example.imoviescompose.domain.mapper.UserMapper.toUserEntity
import com.example.imoviescompose.domain.model.user.User
import com.example.imoviescompose.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun updateUser(user: User): Flow<Boolean> {
        return flow {
            try {
                userDao.updateUser(user.toUserEntity())
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }
    }

    override suspend fun getUser(): Flow<User?> {
        return flow {
            try {
                val user = userDao.getUsers()?.firstOrNull()?.toUser()
                emit(user)
            } catch (e: Exception) {
                emit(null)
            }
        }
    }
}