package com.mert.trueform.domain.usecase

import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExerciseDetailUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {

    suspend operator fun invoke(id: String): Exercise {
        return repository.getExerciseDetail(id)
    }
}