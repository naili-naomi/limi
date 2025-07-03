// src/main/kotlin/com/limi/clients/GoogleBooksClient.kt
package com.limi.clients

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.net.URL

@Serializable
data class GoogleBooksResponse(val items: List<VolumeItem> = emptyList())

@Serializable
data class VolumeItem(val volumeInfo: VolumeInfo)

@Serializable
data class VolumeInfo(val imageLinks: ImageLinks? = null)

@Serializable
data class ImageLinks(val thumbnail: String? = null)

fun buscarImagemPorTitulo(titulo: String): String? {
    return try {
        val query = java.net.URLEncoder.encode(titulo, "UTF-8")
        val url = "https://www.googleapis.com/books/v1/volumes?q=intitle:$query&maxResults=1"
        val json = URL(url).readText()

        val resposta = Json { ignoreUnknownKeys = true }.decodeFromString<GoogleBooksResponse>(json)

        resposta.items.firstOrNull()
            ?.volumeInfo
            ?.imageLinks
            ?.thumbnail
            ?.replace("http://", "https://") // Forçar HTTPS
            ?: null // Fallback para null se não encontrar
    } catch (e: Exception) {
        println("Erro ao buscar imagem para '$titulo': ${e.message}")
        null
    }
}

fun gerarPlaceholder(titulo: String, autor: String): String {
    val encodedTitulo = java.net.URLEncoder.encode(titulo.take(20), "UTF-8")
    val encodedAutor = java.net.URLEncoder.encode(autor.take(15), "UTF-8")
    return "https://via.placeholder.com/240x320.png?text=$encodedTitulo%0A$encodedAutor"
}
