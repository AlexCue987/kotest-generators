package io.kotest.generation.generators.sample

import io.kotest.generation.common.*
import io.kotest.generation.sample.SampleInstanceFactory
import java.io.File
import kotlin.reflect.KClass

fun generateSampleInstances(vararg klasses: KClass<*>
) = generateSampleInstancesWithCustomClasses(
    SampleInstanceFactory(),
    "Sample${simpleNameOfFirstClass(*klasses)}.kt",
    *klasses
)

fun generateSampleInstances(filename: String,
                            vararg klasses: KClass<*>
) = generateSampleInstancesWithCustomClasses(
    SampleInstanceFactory(),
    filename,
    *klasses
)

fun SampleInstanceFactory.generateSampleInstances(filename: String,
                            vararg klasses: KClass<*>
) = generateSampleInstancesWithCustomClasses(
    this,
    filename,
    *klasses
)

private fun generateSampleInstancesWithCustomClasses(
    sampleInstanceFactory: SampleInstanceFactory,
    filename: String,
    vararg klasses: KClass<*>
) {
    val instances = klasses.map { VariableAssignment(variableNameOf(it), sampleInstanceFactory.serializeSampleValue(it)) }
    val classesToImport = ImportsGenerator().generate(instances.map { it.code })
    val code = """
${classesToImport.joinToString("\n") {"import $it" }}

// generated by io.kotest.generation:kotests-generator
object ${className(filename)} {
${instances.map { instance -> "fun ${instance.name}() = ${instance.code.sourceCodeAsOneString()}" }.joinToString("\n\n")}
}
        """.trimIndent()
    File(filename).writeText(code)
}

data class VariableAssignment(val name: String, val code: CodeSnippet)

fun simpleNameOfFirstClass(vararg klasses: KClass<*>) =
    klasses.asSequence().firstOrNull()?.let { it.simpleName } ?: "NoArguments"