package io.kotest.generation.sample

import io.kotest.generation.common.CodeSnippet
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.ZoneId

class LastResortVisitorKotest: StringSpec() {
    private val systemToTest = LastResortVisitor()

    init {
        "handle a class" {
            val buffer = CodeSnippet()
            systemToTest.handle(MyClass::class, buffer, false)
            buffer.sourceCode() shouldBe
                listOf(
                    "MyClass(",
                    "//TODO: initialize",
                    "),"
                )
        }

        "handle an interface" {
            val buffer = CodeSnippet()
            systemToTest.handle(MyInterface::class, buffer, false)
//            serializeToKotlin("aaa.txt", buffer.sourceCode())
            buffer.sourceCode() shouldBe
                listOf(
                    """MyInterface(""",
                    """//TODO: initialize""",
                    """),"""
            )
        }

        "computes classes to import" {
            val buffer = CodeSnippet()
            systemToTest.handle(Location::class, buffer, false)
//            serializeToKotlin("aaa.txt", buffer.qualifiedNames())
            buffer.qualifiedNames() shouldBe
                setOf(
                    """io.kotest.generation.sample.LastResortVisitorKotest.Location""",
                )
        }
    }

    class MyClass(val name: String)

    interface MyInterface {
        val name: String
        val quantity: Int
    }

    interface Location {
        fun timeZone(): ZoneId
        val latitude: BigDecimal
    }
}