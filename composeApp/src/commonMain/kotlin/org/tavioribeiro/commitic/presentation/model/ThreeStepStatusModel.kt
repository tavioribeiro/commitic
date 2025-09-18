package org.tavioribeiro.commitic.presentation.model


enum class ThreeStepStatusColors {
    GRAY,
    ORANGE,
    RED,
    GREEN
}

data class ThreeStepStatusModel(
    val currentStep: String,
    val stepOneColor: ThreeStepStatusColors,
    val stepTwoColor: ThreeStepStatusColors,
    val stepThreeColor: ThreeStepStatusColors
)