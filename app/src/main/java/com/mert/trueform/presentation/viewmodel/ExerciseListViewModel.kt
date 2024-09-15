package com.mert.trueform.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.usecase.GetExercisesUseCase
import com.mert.trueform.domain.usecase.GetSearchedExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getSearchedExercisesUseCase: GetSearchedExercisesUseCase
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises

    private var currentOffset = 0
    private val limit = 10
    var isLoading = false
    var isSearching = false

    init {
        loadExercises()
    }

    fun loadExercises() {
        if (isLoading || isSearching) return

        isLoading = true
        viewModelScope.launch {
            try {
                getExercisesUseCase(limit, currentOffset).collect { newExercises ->
                    _exercises.value += newExercises
                    currentOffset += limit
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun getSearchedExercises(query: String) {
        if (query.isBlank()) {
            isSearching = false
            _exercises.value = emptyList()
            currentOffset = 0
            loadExercises()
            return
        }

        isSearching = true
        viewModelScope.launch {
            try {
                getSearchedExercisesUseCase(query).collect { searchResults ->
                    _exercises.value = searchResults
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}