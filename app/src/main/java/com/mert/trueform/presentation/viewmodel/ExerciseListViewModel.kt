package com.mert.trueform.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises

    private var currentOffset = 0
    private val limit = 10
    var isLoading = false
    private var endReached = false

    init {
        loadExercises()
    }

    fun loadExercises() {
        if (isLoading || endReached) return

        isLoading = true
        viewModelScope.launch {
            try {
                getExercisesUseCase(limit, currentOffset).collect { newExercises ->
                    if (newExercises.isEmpty()) {
                        endReached = true
                    } else {
                        _exercises.update { currentList ->
                            currentList + newExercises
                        }
                        currentOffset += limit
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}