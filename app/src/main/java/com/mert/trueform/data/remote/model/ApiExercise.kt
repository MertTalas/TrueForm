package com.mert.trueform.data.remote.model

import com.mert.trueform.domain.model.Exercise

data class ApiExercise(
    val id: String,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val instructions: List<String>
) {
    fun toDomainModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            bodyPart = bodyPart,
            equipment = equipment,
            gifUrl = gifUrl,
            target = target,
            secondaryMuscles = secondaryMuscles,
            instructions = instructions
        )
    }
}