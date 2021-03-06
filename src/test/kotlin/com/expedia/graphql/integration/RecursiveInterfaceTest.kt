package com.expedia.graphql.integration

import com.expedia.graphql.TopLevelObject
import com.expedia.graphql.testSchemaConfig
import com.expedia.graphql.toSchema
import kotlin.test.Test
import kotlin.test.assertEquals

class RecursiveInterfaceTest {

    @Test
    fun recursiveInterface() {
        val queries = listOf(TopLevelObject(RecursiveInterfaceQuery()))
        val schema = toSchema(queries = queries, config = testSchemaConfig)
        assertEquals(1, schema.queryType.fieldDefinitions.size)
        val field = schema.queryType.fieldDefinitions.first()
        assertEquals("getRoot", field.name)
    }
}

class RecursiveInterfaceQuery {
    fun getRoot() = RecursiveInterfaceA()
}

interface SomeInterface {
    val id: String
}

class RecursiveInterfaceA : SomeInterface {
    override val id = "A"
    fun getB() = RecursiveInterfaceB()
}

class RecursiveInterfaceB : SomeInterface {
    override val id = "B"
    fun getA() = RecursiveInterfaceA()
}
