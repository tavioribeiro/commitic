package org.tavioribeiro.commitic.presentation.model


enum class FiveStepStatusColors {
    GRAY,
    ORANGE,
    RED,
    GREEN
}

data class FiveStepStatusModel(
    val currentStep: String,
    val stepOneColor: FiveStepStatusColors,
    val stepTwoColor: FiveStepStatusColors,
    val stepThreeColor: FiveStepStatusColors,
    val stepFourColor: FiveStepStatusColors
)