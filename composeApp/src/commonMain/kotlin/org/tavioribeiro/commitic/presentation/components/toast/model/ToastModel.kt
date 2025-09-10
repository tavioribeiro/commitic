package org.tavioribeiro.commitic.presentation.components.toast.model


data class ToastUiModel(
    val title: String,
    val message: String,
    val type: ToastType,
    val duration: Long = 4000L
)