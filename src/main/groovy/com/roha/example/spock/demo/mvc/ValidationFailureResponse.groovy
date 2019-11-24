package com.roha.example.spock.demo.mvc

import org.springframework.validation.FieldError

class ValidationFailureResponse {
    private FieldError[] fieldErrors

    ValidationFailureResponse(FieldError[] fieldErrors) {
        this.fieldErrors = fieldErrors.sort{
            it.field
        }
    }

    FieldError[] getFieldErrors() {
        return fieldErrors
    }
}
