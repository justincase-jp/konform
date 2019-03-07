package io.konform.validation.jsonschema

import io.konform.validation.*

fun <T> ValidationBuilder<T>.const(allowed: T) =
    addConstraint(
        { EqualityViolation(it) },
        allowed.toString()
    ) { it == allowed }

fun <T> ValidationBuilder<T>.enum(vararg allowed: T) =
    addConstraint(
        { NotEnumMemberViolation(it) },
        allowed.joinToString("', '", "'", "'")
    ) { it in allowed }

inline fun <reified T : Enum<T>> ValidationBuilder<String>.enum(): Constraint<String> {
    val enumNames = enumValues<T>().map { it.name }
    T::class.simpleName
    return addConstraint(
        { NotEnumMemberViolation(it) },
        enumNames.joinToString("', '", "'", "'")
    ) { it in enumNames }
}

fun <T : Number> ValidationBuilder<T>.maximum(maximumInclusive: Number) = addConstraint(
    { MaximumViolation(it) },
    maximumInclusive.toString()
) { it.toDouble() <= maximumInclusive.toDouble() }

fun <T : Number> ValidationBuilder<T>.exclusiveMaximum(maximumExclusive: Number) = addConstraint(
    { ExclusiveMaximumViolation(it) },
    maximumExclusive.toString()
) { it.toDouble() < maximumExclusive.toDouble() }

fun <T : Number> ValidationBuilder<T>.minimum(minimumInclusive: Number) = addConstraint(
    { MinimumViolation(it) },
    minimumInclusive.toString()
) { it.toDouble() >= minimumInclusive.toDouble() }

fun <T : Number> ValidationBuilder<T>.exclusiveMinimum(minimumExclusive: Number) = addConstraint(
    { ExclusiveMinimumViolation(it) },
    minimumExclusive.toString()
) { it.toDouble() > minimumExclusive.toDouble() }

fun ValidationBuilder<String>.minLength(length: Int): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("minLength requires the length to be >= 0") }
    return addConstraint(
        { StringMinimumLengthViolation(it) },
        length.toString()
    ) { it.length >= length }
}

fun ValidationBuilder<String>.maxLength(length: Int): Constraint<String> {
    require(length >= 0) { IllegalArgumentException("maxLength requires the length to be >= 0") }
    return addConstraint(
        { StringMaximumLengthViolation(it) },
        length.toString()
    ) { it.length <= length }
}

fun ValidationBuilder<String>.pattern(pattern: String) = pattern(Regex(pattern))

fun ValidationBuilder<String>.pattern(pattern: Regex) = addConstraint(
    { PatternViolation(it) },
    pattern.toString()
) { it.matches(pattern) }

inline fun <reified T> ValidationBuilder<T>.minItems(minSize: Int): Constraint<T> = addConstraint(
    { MinimumNumberOfItemsViolation(it) },
    minSize.toString()
) {
    when (it) {
        is Iterable<*> -> it.count() >= minSize
        is Array<*> -> it.count() >= minSize
        is Map<*, *> -> it.count() >= minSize
        else -> throw IllegalStateException("minItems can not be applied to type ${T::class}")
    }
}

inline fun <reified T> ValidationBuilder<T>.maxItems(maxSize: Int): Constraint<T> = addConstraint(
    { MaximumNumberOfItemsViolation(it) },
    maxSize.toString()
) {
    when (it) {
        is Iterable<*> -> it.count() <= maxSize
        is Array<*> -> it.count() <= maxSize
        is Map<*, *> -> it.count() <= maxSize
        else -> throw IllegalStateException("maxItems can not be applied to type ${T::class}")
    }
}
