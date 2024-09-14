package com.mert.trueform.domain.usecase

import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class GetExercisesUseCase(private val repository: ExerciseRepository) {

    suspend operator fun invoke(limit: Int, offset: Int): Flow<List<Exercise>> {
        return repository.getExercises(limit, offset)
    }
}