package io.kotest.generation

import io.kotest.generation.generators.actual.serializeToAssertions
import io.kotest.generation.generators.actual.serializeToKotlin

fun serializeForTests(vararg instances: Any) {
    serializeToKotlin(*instances)
    serializeToAssertions(*instances)
}