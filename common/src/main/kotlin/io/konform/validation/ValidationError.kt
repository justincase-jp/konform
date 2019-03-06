package io.konform.validation

typealias ValidationErrorFactory = (a: String) -> ValidationError

interface ValidationError {
    val message: String
}

class NotEnumMemberViolation(val value: String): ValidationError {
    override val message = "must be one of: $value"
}

class RequiredPropertyNotFound (propertyName: String) : ValidationError {
    override val message = "must have property $propertyName"
}

class MaximumViolation(val value: String) : ValidationError {
    override val message = "must be at most $value"
}

class ExclusiveMaximumViolation(val value: String): ValidationError {
    override val message = "must be less than $value"
}

class MinimumViolation(val value: String): ValidationError {
    override val message = "must be at least $value"
}

class ExclusiveMinimumViolation(val value: String): ValidationError {
    override val message = "must be greater than $value"
}

class StringMinimumLengthViolation(val value: String): ValidationError {
    override val message = "must have at least $value characters"
}

class StringMaximumLengthViolation(val value: String): ValidationError {
    override val message = "must have at most $value characters"
}

class PatternViolation(val value: String): ValidationError {
    override val message = "must match the expected pattern"
}

class MinimumNumberOfItemsViolation(val value: String): ValidationError {
    override val message = "must have at least $value"
}

class MaximumNumberOfItemsViolation(val value: String): ValidationError {
    override val message = "must have at most $value items"
}
