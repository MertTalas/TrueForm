package com.mert.trueform.data.remote.api

import com.mert.trueform.data.remote.model.ApiExercise
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("X-RapidAPI-Key: a9e8f1f95bmshd38372b7d9c2bc3p130d8fjsnb0878cb484e3", "X-RapidAPI-Host: exercisedb.p.rapidapi.com")
    @GET("exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<ApiExercise>

    @Headers("X-RapidAPI-Key: a9e8f1f95bmshd38372b7d9c2bc3p130d8fjsnb0878cb484e3", "X-RapidAPI-Host: exercisedb.p.rapidapi.com")
    @GET("/exercises/exercise/{id}")
    suspend fun getExerciseDetail(
        @Path("id") id: String
    ): ApiExercise

    @Headers("X-RapidAPI-Key: a9e8f1f95bmshd38372b7d9c2bc3p130d8fjsnb0878cb484e3", "X-RapidAPI-Host: exercisedb.p.rapidapi.com")
    @GET("/exercises/name/{name}")
    suspend fun searchExercises(
        @Path("name") name: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Int.MAX_VALUE
    ): List<ApiExercise>
}