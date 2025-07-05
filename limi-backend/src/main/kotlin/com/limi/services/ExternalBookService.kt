package com.limi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksResponse(
    val totalItems: Int,
    val items: List<Volume> = emptyList()
)

@Serializable
data class Volume(
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    val publisher: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String?
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

    // Função para buscar detalhes completos
    suspend fun getBookDetailsByTitle(title: String): VolumeInfo? {
        val query = title.replace(" ", "+")
        val url = "https://www.googleapis.com/books/v1/volumes?q=intitle:$query&maxResults=1"
        val resp: GoogleBooksResponse = client.get(url).body()
        return resp.items.firstOrNull()?.volumeInfo
    }
}