package com.limi.clients

import java.net.URLEncoder

fun gerarImagemMockada(titulo: String): String {
    // Codifica o título para URL
    val query = URLEncoder.encode(titulo, "UTF-8")
    // Exemplo usando placeholder API do Google Books
    return "https://books.google.com/books/content?id=$query&printsec=frontcover&img=1&zoom=1"
}
