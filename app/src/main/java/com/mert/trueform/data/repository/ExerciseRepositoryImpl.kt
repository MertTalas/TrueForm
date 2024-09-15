package com.mert.trueform.data.repository

import com.mert.trueform.data.remote.api.ApiService
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExerciseRepositoryImpl(
    private val apiService: ApiService
) : ExerciseRepository {

    override suspend fun getExercises(limit: Int, offset: Int): Flow<List<Exercise>> = flow {
        try {
            val response = apiService.getExercises(limit, offset)
            val exercises = response.map { it.toDomainModel() }
            emit(exercises)
        } catch (e: Exception) {
            emit(emptyList())
            e.printStackTrace()
        }
    }

    override suspend fun getExerciseDetail(id: String): Exercise {
        val response = apiService.getExerciseDetail(id)
        return response.toDomainModel()
    }

    override suspend fun getSearchedExercises(query: String): Flow<List<Exercise>> = flow {
        try {
            val response = apiService.searchExercises(query)
            val exercises = response.map { it.toDomainModel() }

            val filteredExercises = exercises.filter { exercise ->
                exercise.name.contains(query, ignoreCase = true)
            }

            emit(filteredExercises)
        } catch (e: Exception) {
            emit(emptyList())
            e.printStackTrace()
        }
    }
}