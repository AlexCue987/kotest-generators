package io.kotest.generation.common

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ImportsGeneratorKotest: StringSpec() {
    private val generatorToTest = ImportsGenerator()

    init {
        "sorts distinct imports" {
            val systemToTest: HasImports = object : HasImports {
                override fun qualifiedNames(): Collection<String> {
                    return listOf("shapes.Square", "shapes.Circle", "angles.Right")
                }
            }
            val unitTest: HasImports = object : HasImports {
                override fun qualifiedNames(): Collection<String> {
                    return listOf("shapes.Square", "angles.Acute", "angles.Right")
                }
            }

            val actual = generatorToTest.generate(systemToTest, unitTest)

            actual shouldBe listOf("angles.Acute", "angles.Right", "shapes.Circle", "shapes.Square")
        }
    }
}