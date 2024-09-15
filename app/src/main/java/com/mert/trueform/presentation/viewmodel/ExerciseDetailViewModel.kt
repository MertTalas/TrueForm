package com.mert.trueform.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.domain.usecase.GetExerciseDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase
) : ViewModel() {

    private val _exerciseDetail = MutableStateFlow<Exercise?>(null)
    val exerciseDetail: StateFlow<Exercise?> get() = _exerciseDetail

    fun loadExerciseDetail(id: String) {
        viewModelScope.launch {
            try {
                val detail = getExerciseDetailUseCase(id)
                _exerciseDetail.value = detail
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}