package com.limi.config

import com.limi.models.LivroEntity
import com.limi.models.AutorEntity
import com.limi.models.GeneroEntity
import com.limi.models.LivroGenero
import com.limi.services.ExternalBookService
import com.limi.models.Autores
import com.limi.models.Generos
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert

object DatabaseSeed {
    fun seedLivrosIniciais(client: HttpClient) {
        transaction {
            if (LivroEntity.count() > 0L) return@transaction // Não insere se já houver dados

            val externalBookService = ExternalBookService(client)

            val livros = listOf(
                "Capitães de Areia",
                "Dom Casmurro",
                "Harry Potter e a Pedra Filosofal",
                "Senhor dos Anéis: A Sociedade do Anel",
                "O Exorcista",
                "O Cemitério",
                "Orgulho e Preconceito",
                "Duna",
                "O Assassinato de Roger Ackroyd",
                "Longa Caminhada até a Liberdade",
                "Sapiens: Uma Breve História da Humanidade",
                "Guerra e Paz",
                "Cem Sonetos de Amor",
                "O Poder do Hábito",
                "Garota Exemplar"
            )

            livros.forEach { titulo ->
                runBlocking {
                    val bookDetails = externalBookService.getBookDetailsByTitle(titulo)
                    if (bookDetails != null) {
                        val autorEntity = AutorEntity
                            .find { Autores.nome eq (bookDetails.authors?.firstOrNull() ?: "Desconhecido") }
                            .firstOrNull()

                            ?: AutorEntity.new { nome = bookDetails.authors?.firstOrNull() ?: "Desconhecido" }

                        val livroEntity = LivroEntity.new {
                            this.titulo = bookDetails.title ?: ""
                            this.autor = autorEntity
                            this.anoPublicacao = bookDetails.publishedDate?.substring(0, 4)?.toIntOrNull() ?: 0
                            this.sinopse = bookDetails.description ?: "Sinopse não disponível"
                            this.urlImagem = bookDetails.imageLinks?.thumbnail
                        }

                        // Adiciona gêneros (a API do Google Books não fornece gêneros de forma consistente)
                        val generosFromApi = listOf("Ficção") // Gênero padrão
                        generosFromApi.forEach { generoNome ->
                            val generoEntity = GeneroEntity.find { Generos.nome eq generoNome }.firstOrNull()
                                ?: GeneroEntity.new { nome = generoNome }

                            LivroGenero.insert {
                                it[LivroGenero.livro] = livroEntity.id
                                it[LivroGenero.genero] = generoEntity.id
                            }
                        }
                    }
                }
            }
        }
    }
}
