package io.kotest.generation.sample

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KVisibility
import kotlin.reflect.full.primaryConstructor

class PublicCallablesFactory {

    fun callablesToTest(klass: KClass<*>): List<KCallable<*>> {
        return klass.members
            .filter { it.visibility in listOf(KVisibility.PUBLIC, KVisibility.INTERNAL) &&
                    it.name !in functionsNotToTest() &&
                    !it.isSuspend &&
                    !it.name.startsWith("component")}
            .filter { it.name !in primaryConstructorFields(klass) }
    }

    fun functionsNotToTest() = Any::class.members.map{ it.name }.toSet() + "copy"

    fun primaryConstructorFields(klass: KClass<*>): List<String> =
        klass.primaryConstructor?.let { it.parameters.mapNotNull { it.name } } ?: listOf()
}