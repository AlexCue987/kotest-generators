package io.kotest.generation.sample

import io.kotest.generation.common.CodeSnippet
import io.kotest.generation.common.CodeSnippetFactory
import kotlin.reflect.KClass

class LastResortVisitor(
    private val codeSnippetFactory: CodeSnippetFactory = CodeSnippetFactory()
): SampleSerializerVisitor {
    override fun canHandle(klass: KClass<*>): Boolean = true

    override fun handle(klass: KClass<*>, buffer: CodeSnippet, isLast: Boolean) {
        buffer.addClassName(klass.qualifiedName!!)
        buffer.addLine("${klass.simpleName}(", suppressTerminator = true)
        buffer.addLine("//TODO: initialize", suppressTerminator = true)
        buffer.addLine(")", suppressTerminator = isLast)
    }
}