package io.kotest.generation.actual

import io.kotest.generation.common.CodeSnippet
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe

class SerializersListKotest: StringSpec() {
    override fun isolationMode() = IsolationMode.InstancePerTest
    private val buffer = CodeSnippet()
    private val serializer1: SerializerVisitor = object : SerializerVisitor {
        override fun canHandle(instance: Any?): Boolean {
            TODO("Not yet implemented")
        }

        override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) {
            TODO("Not yet implemented")
        }
    }
    private val serializer2: SerializerVisitor = object : SerializerVisitor {
        override fun canHandle(instance: Any?): Boolean {
            TODO("Not yet implemented")
        }

        override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) {
            TODO("Not yet implemented")
        }
    }
    private val sut = SerializersList(listOf(serializer1, serializer2))

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
    }

    init {
        "invoke only first serializer" {
            val serializer: SerializerVisitor = object : SerializerVisitor {
                override fun canHandle(instance: Any?) = true

                override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) {
                    buffer.addClassName("class")
                    buffer.addLine("line")
                }
            }
            val actual = SerializersList(listOf(serializer, serializer2)).serialize("Instance", buffer, false)
            assertSoftly {
                actual shouldBe true
                buffer.qualifiedNames() shouldBe listOf("class")
                buffer.sourceCode() shouldBe listOf("line")
           }
        }

        "invoke both serializers, second should handle" {
            var counter = 0
            val serializer1: SerializerVisitor = object : SerializerVisitor {
                override fun canHandle(instance: Any?): Boolean {
                    counter++
                    return false
                }

                override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) { }
            }
            val serializer2: SerializerVisitor = object : SerializerVisitor {
                override fun canHandle(instance: Any?) = true

                override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) {
                    buffer.addClassName("class")
                    buffer.addLine("line")
                }
            }
            val actual = SerializersList(listOf(serializer1, serializer2)).serialize("Instance", buffer, false)
            assertSoftly {
                actual shouldBe true
                buffer.qualifiedNames() shouldBe listOf("class")
                buffer.sourceCode() shouldBe listOf("line")
                counter shouldBe 1
             }
        }

        "invoke both serializers, none should handle" {
            var counter1 = 0
            val serializer1: SerializerVisitor = object : SerializerVisitor {
                override fun canHandle(instance: Any?): Boolean {
                    counter1++
                    return false
                }

                override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) { }
            }
            var counter2 = 0
            val serializer2: SerializerVisitor = object : SerializerVisitor {
                override fun canHandle(instance: Any?): Boolean {
                    counter2++
                    return false
                }

                override fun handle(instance: Any?, buffer: CodeSnippet, isLast: Boolean) { }
            }
            val actual = SerializersList(listOf(serializer1, serializer2)).serialize("Instance", buffer, false)
            assertSoftly {
                actual shouldBe false
                buffer.qualifiedNames() shouldBe listOf()
                buffer.sourceCode() shouldBe listOf()
                counter1 shouldBe 1
                counter2 shouldBe 1
            }
        }
    }
}