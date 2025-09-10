package org.tavioribeiro.commitic.presentation.components.toast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel

class ToastViewModel {
    private val _toastState = MutableStateFlow<ToastUiModel?>(null)
    val toastState = _toastState.asStateFlow()

    fun showToast(toast: ToastUiModel) {
        _toastState.update { toast }
    }

    fun dismissToast() {
        _toastState.update { null }
    }
}