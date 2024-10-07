package com.mert.trueform.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.usecase.GetExercisesUseCase
import com.mert.trueform.domain.usecase.GetSearchedExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getSearchedExercisesUseCase: GetSearchedExercisesUseCase
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> get() = _isSearching

    private var currentOffset = 0
    private val limit = 10

    init {
        loadExercises()
    }

    fun loadExercises() {
        if (_isLoading.value || _isSearching.value) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                getExercisesUseCase(limit, currentOffset).collect { newExercises ->
                    _exercises.value += newExercises
                    currentOffset += limit
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSearchedExercises(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        _isSearching.value = true
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

    fun clearSearch() {
        _isSearching.value = false
        _exercises.value = emptyList()
        currentOffset = 0
        loadExercises()
    }
}