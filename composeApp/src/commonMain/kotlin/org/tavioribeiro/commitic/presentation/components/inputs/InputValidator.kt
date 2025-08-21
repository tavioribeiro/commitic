package org.tavioribeiro.commitic.presentation.components.inputs




internal class InputValidator(private val value: String) {

    /*companion object {

    }*/

    fun validateNotEmpty(message: String = "A string não pode ser vazia."): InputValidator {
        if (value.isEmpty()) {
            throw IllegalArgumentException(message)
        }
        return this
    }

    fun validateNotBlank(message: String = "A string não pode conter apenas espaços."): InputValidator {
        if (value.isBlank()) {
            throw IllegalArgumentException(message)
        }
        return this
    }

    fun validateMinLength(minLength: Int, message: String = "O comprimento mínimo é de $minLength caracteres."): InputValidator {
        if (value.length < minLength) {
            throw IllegalArgumentException(message)
        }
        return this
    }
}