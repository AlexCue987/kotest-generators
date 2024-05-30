package io.kotest.generation.kotest

import io.kotest.core.spec.style.StringSpec
import io.kotest.generation.common.CodeSnippetFactory
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class KotestDataKotest: StringSpec() {
    init {
        "combines classes to import" {
            val method1 = MethodToTest(BreaksCalendar::lunchTime, CodeSnippetFactory())
            val method2 = MethodToTest(BreaksCalendar::cutoff, CodeSnippetFactory())
            val systemToTest = SystemToTest(BreaksCalendar::class, CodeSnippetFactory())
            val actual = KotestData(systemToTest, listOf(method1, method2))
            actual.classesToImport shouldContainExactlyInAnyOrder listOf(
                "io.kotest.generation.kotest.BreaksCalendar",
                "java.time.LocalDate",
                "java.time.LocalDateTime",
                "kotlin.Long"
            )
        }
    }
}

class BreaksCalendar {
    fun lunchTime(date: LocalDate): LocalDateTime = LocalDateTime.of(date, LocalTime.of(12, 0))
    fun cutoff(date: LocalDate, minutes: Long): LocalDateTime = LocalDateTime.of(date, LocalTime.of(0, 0)).plusMinutes(minutes)
}