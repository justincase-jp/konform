package io.konform.validation

typealias ValidationErrorFactory = (a: String) -> ValidationError

interface ValidationError {
    val apiMessage: String
}

class GeneralViolation(override val apiMessage: String): ValidationError

class EqualityViolation(val value: String): ValidationError {
    override val apiMessage = "must be $value"
}

class NotEnumMemberViolation(val value: String): ValidationError {
    override val apiMessage = "must be one of: $value"
}

class RequiredPropertyNotFound (val propertyName: String) : ValidationError {
    override val apiMessage = "must have property $propertyName"
}

class MaximumViolation(val value: String) : ValidationError {
    override val apiMessage = "must be at most $value"
}

class ExclusiveMaximumViolation(val value: String): ValidationError {
    override val apiMessage = "must be less than $value"
}

class MinimumViolation(val value: String): ValidationError {
    override val apiMessage = "must be at least $value"
}

class ExclusiveMinimumViolation(val value: String): ValidationError {
    override val apiMessage = "must be greater than $value"
}

class StringMinimumLengthViolation(val value: String): ValidationError {
    override val apiMessage = "must have at least $value characters"
}

class StringMaximumLengthViolation(val value: String): ValidationError {
    override val apiMessage = "must have at most $value characters"
}

class PatternViolation(val value: String): ValidationError {
    override val apiMessage = "must match the expected pattern"
}

class MinimumNumberOfItemsViolation(val value: String): ValidationError {
    override val apiMessage = "must have at least $value"
}

class MaximumNumberOfItemsViolation(val value: String): ValidationError {
    override val apiMessage = "must have at most $value items"
}
