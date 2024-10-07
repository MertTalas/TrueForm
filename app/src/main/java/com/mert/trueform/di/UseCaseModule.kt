package com.mert.trueform.di

import com.mert.trueform.domain.repository.ExerciseRepository
import com.mert.trueform.domain.usecase.GetExerciseDetailUseCase
import com.mert.trueform.domain.usecase.GetExercisesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetExercisesUseCase(repository: ExerciseRepository): GetExercisesUseCase {
        return GetExercisesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExerciseDetailUseCase(repository: ExerciseRepository): GetExerciseDetailUseCase {
        return GetExerciseDetailUseCase(repository)
    }
}