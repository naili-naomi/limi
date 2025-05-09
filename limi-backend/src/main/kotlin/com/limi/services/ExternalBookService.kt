package com.limi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
private data class GoogleBooksResponse(
    val totalItems: Int,
    val items: List<Volume> = emptyList()
)

@Serializable
private data class Volume(
    val volumeInfo: VolumeInfo
)

@Serializable
private data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    val description: String? = null
)

/**
 * Serviço para validar existência de um livro na Google Books.
 */
class ExternalBookService(private val client: HttpClient) {

    suspend fun existsByIsbn(isbn: String): Boolean {
        val url = "https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn"
        val resp: GoogleBooksResponse = client.get(url).body()
        return resp.totalItems > 0
    }

    suspend fun existsByTitle(title: String): Boolean {
        val query = title.replace(" ", "+")
        val url = "https://www.googleapis.com/books/v1/volumes?q=intitle:$query"
        val resp: GoogleBooksResponse = client.get(url).body()
        return resp.totalItems > 0
    }
}
