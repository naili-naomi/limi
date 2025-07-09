package com.limi.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ExternalBookServiceTest {

    private fun createMockClient(responseBody: String): HttpClient {
        val mockEngine = MockEngine { request ->
            respond(
                content = responseBody,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Test
    fun `existsByIsbn should return true when book exists`() = runBlocking {
        // Given
        val responseBody = "{\"totalItems\": 1}"
        val client = createMockClient(responseBody)
        val externalBookService = ExternalBookService(client)

        // When
        val result = externalBookService.existsByIsbn("1234567890")

        // Then
        assertTrue(result)
    }

    @Test
    fun `existsByTitle should return true when book exists`() = runBlocking {
        // Given
        val responseBody = "{\"totalItems\": 1}"
        val client = createMockClient(responseBody)
        val externalBookService = ExternalBookService(client)

        // When
        val result = externalBookService.existsByTitle("Test Title")

        // Then
        assertTrue(result)
    }

    @Test
    fun `getBookDetailsByTitle should return volume info when book exists`() = runBlocking {
        // Given
        val responseBody = "{\"totalItems\": 1, \"items\": [{\"volumeInfo\": {\"title\": \"Test Title\"}}]}"
        val client = createMockClient(responseBody)
        val externalBookService = ExternalBookService(client)
        val expectedVolumeInfo = VolumeInfo(title = "Test Title")

        // When
        val result = externalBookService.getBookDetailsByTitle("Test Title")

        // Then
        assertEquals(expectedVolumeInfo, result)
    }
}