package com.example.imoviescompose.domain.model.user

data class User (
    val id: Int? = null,
    val lastDateVisit: String,
    val lastPageVisit: String,
    val lastPageVisitParam: Int? = null
)