package com.example.imoviescompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val lastDateVisit: String,
    val lastPageVisit: String,
    val lastPageVisitParam: Int? = null
)
