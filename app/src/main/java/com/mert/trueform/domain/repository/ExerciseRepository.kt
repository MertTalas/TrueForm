package com.mert.trueform.domain.repository

import com.mert.trueform.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun getExercises(limit: Int, offset: Int): Flow<List<Exercise>>

    suspend fun getExerciseDetail(id: String): Exercise

    suspend fun getSearchedExercises(query: String): Flow<List<Exercise>>
}