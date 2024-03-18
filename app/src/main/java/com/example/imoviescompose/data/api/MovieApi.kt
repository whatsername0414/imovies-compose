package com.example.imoviescompose.data.api

import com.example.imoviescompose.data.common.BaseResponse
import com.example.imoviescompose.data.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search")
    suspend fun getMovies(
        @Query("country") country: String = "au",
        @Query("media") media: String = "movie",
        @Query("term") term: String
    ): Response<BaseResponse<List<MovieDto>>>

    @GET("lookup")
    suspend fun getMovie(
        @Query("country") country: String = "au",
        @Query("id") id: Int
    ): Response<BaseResponse<List<MovieDto>>>

}