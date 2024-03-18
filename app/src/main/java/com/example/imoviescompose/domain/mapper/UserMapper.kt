package com.example.imoviescompose.domain.mapper

import com.example.imoviescompose.data.entity.UserEntity
import com.example.imoviescompose.domain.model.user.User

object UserMapper {
    fun UserEntity.toUser(): User {
        return User(
            id = id ?: -1,
            lastDateVisit = lastDateVisit,
            lastPageVisit = lastPageVisit,
            lastPageVisitParam = lastPageVisitParam
        )
    }

    fun User.toUserEntity(): UserEntity {
        return UserEntity(
            id = id,
            lastDateVisit = lastDateVisit,
            lastPageVisit = lastPageVisit,
            lastPageVisitParam = lastPageVisitParam
        )
    }
}