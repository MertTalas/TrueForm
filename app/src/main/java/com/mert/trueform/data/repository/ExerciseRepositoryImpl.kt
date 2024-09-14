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
}