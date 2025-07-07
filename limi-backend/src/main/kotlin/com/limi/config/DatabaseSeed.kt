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
                "A Culpa é das Estrelas",
                "Um Dia",
                "O Sol é Para Todos",
                "As Vantagens de Ser Invisível",
                "A Biblioteca da Meia-Noite",
                "Verity",
                "É Assim que Acaba",
                "Eleanor & Park",
                "Pessoas Normais",
                "O Amor nas Entrelinhas",
                "Harry Potter e a Pedra Filosofal",
                "O Nome do Vento",
                "A Rainha Vermelha",
                "Trono de Vidro",
                "O Senhor dos Anéis: A Sociedade do Anel",
                "Sombra e Ossos",
                "Corte de Espinhos e Rosas",
                "O Príncipe Cruel",
                "As Crônicas de Nárnia",
                "Mago: Aprendiz",
                "Duna",
                "Fundação",
                "Neuromancer",
                "Eu, Robô",
                "O Fim da Infância",
                "O Problema dos Três Corpos",
                "Admirável Mundo Novo",
                "Fahrenheit 451",
                "Snow Crash",
                "Encarcerados",
                "It: A Coisa",
                "O Iluminado",
                "Drácula",
                "Frankenstein",
                "O Exorcista",
                "A Volta do Parafuso",
                "Coraline",
                "O Demonologista",
                "A Casa dos Pesadelos",
                "Hex",
                "O Silêncio dos Inocentes",
                "Os Homens que Não Amavam as Mulheres",
                "A Garota no Trem",
                "O Código Da Vinci",
                "Assassinato no Expresso do Oriente",
                "O Colecionador",
                "Antes de Dormir",
                "Um de Nós Está Mentindo",
                "O Segredo do Meu Marido",
                "A Paciente Silenciosa",
                "Orgulho e Preconceito",
                "Dom Quixote",
                "O Grande Gatsby",
                "Crime e Castigo",
                "1984",
                "Jane Eyre",
                "Os Miseráveis",
                "A Metamorfose",
                "Cem Anos de Solidão",
                "O Morro dos Ventos Uivantes",
                "O Poder do Hábito",
                "Inteligência Emocional",
                "Mindset",
                "Rápido e Devagar",
                "Essencialismo",
                "A Sutil Arte de Ligar o F*da-se",
                "O Milagre da Manhã",
                "Como Fazer Amigos e Influenciar Pessoas",
                "Roube Como um Artista",
                "Os Segredos da Mente Milionária",
                "Eu Sou Malala",
                "O Diário de Anne Frank",
                "Steve Jobs",
                "Becoming: Minha História",
                "Longa Caminhada até a Liberdade",
                "Educada",
                "Compaixão e Coragem",
                "O Ano do Pensamento Mágico",
                "Leonardo da Vinci",
                "Born a Crime",
                "O Pequeno Príncipe",
                "Matilda",
                "Como Treinar o Seu Dragão",
                "Extraordinário",
                "Diário de um Banana",
                "A Menina que Roubava Livros",
                "As Aventuras de Tom Sawyer",
                "O Jardim Secreto",
                "Coraline",
                "Os Goonies",
                "Jogos Vorazes",
                "Divergente",
                "Maze Runner",
                "O Conto da Aia",
                "Laranja Mecânica",
                "O Doador de Memórias",
                "Estação Onze",
                "O Círculo",
                "A Máquina do Tempo",
                "Nós"

            )

            val bookGenres = mapOf(
                "A Culpa é das Estrelas" to listOf("Romance", "Drama"),
                "Um Dia" to listOf("Romance", "Drama"),
                "O Sol é Para Todos" to listOf("Clássico", "Drama"),
                "As Vantagens de Ser Invisível" to listOf("Jovem Adulto", "Drama"),
                "A Biblioteca da Meia-Noite" to listOf("Ficção Científica", "Fantasia", "Filosofia"),
                "Verity" to listOf("Suspense", "Romance"),
                "É Assim que Acaba" to listOf("Romance", "Drama"),
                "Eleanor & Park" to listOf("Jovem Adulto", "Romance"),
                "Pessoas Normais" to listOf("Romance", "Drama"),
                "O Amor nas Entrelinhas" to listOf("Romance"),
                "Harry Potter e a Pedra Filosofal" to listOf("Fantasia", "Aventura"),
                "O Nome do Vento" to listOf("Fantasia Épica"),
                "A Rainha Vermelha" to listOf("Fantasia", "Distopia"),
                "Trono de Vidro" to listOf("Fantasia", "Jovem Adulto"),
                "O Senhor dos Anéis: A Sociedade do Anel" to listOf("Fantasia Épica", "Aventura"),
                "Sombra e Ossos" to listOf("Fantasia", "Jovem Adulto"),
                "Corte de Espinhos e Rosas" to listOf("Fantasia", "Romance"),
                "O Príncipe Cruel" to listOf("Fantasia", "Jovem Adulto"),
                "As Crônicas de Nárnia" to listOf("Fantasia", "Infantil"),
                "Mago: Aprendiz" to listOf("Fantasia"),
                "Duna" to listOf("Ficção Científica"),
                "Fundação" to listOf("Ficção Científica"),
                "Neuromancer" to listOf("Ficção Científica", "Cyberpunk"),
                "Eu, Robô" to listOf("Ficção Científica"),
                "O Fim da Infância" to listOf("Ficção Científica"),
                "O Problema dos Três Corpos" to listOf("Ficção Científica"),
                "Admirável Mundo Novo" to listOf("Distopia", "Ficção Científica"),
                "Fahrenheit 451" to listOf("Distopia", "Ficção Científica"),
                "Snow Crash" to listOf("Ficção Científica", "Cyberpunk"),
                "Encarcerados" to listOf("Ficção Científica"),
                "It: A Coisa" to listOf("Terror"),
                "O Iluminado" to listOf("Terror"),
                "Drácula" to listOf("Terror", "Clássico"),
                "Frankenstein" to listOf("Terror", "Clássico"),
                "O Exorcista" to listOf("Terror"),
                "A Volta do Parafuso" to listOf("Terror", "Clássico"),
                "Coraline" to listOf("Fantasia", "Terror", "Infantil"),
                "O Demonologista" to listOf("Terror"),
                "A Casa dos Pesadelos" to listOf("Terror"),
                "Hex" to listOf("Terror"),
                "O Silêncio dos Inocentes" to listOf("Suspense", "Crime"),
                "Os Homens que Não Amavam as Mulheres" to listOf("Suspense", "Crime"),
                "A Garota no Trem" to listOf("Suspense"),
                "O Código Da Vinci" to listOf("Suspense", "Mistério"),
                "Assassinato no Expresso do Oriente" to listOf("Mistério", "Crime"),
                "O Colecionador" to listOf("Suspense", "Thriller Psicológico"),
                "Antes de Dormir" to listOf("Suspense", "Thriller Psicológico"),
                "Um de Nós Está Mentindo" to listOf("Jovem Adulto", "Mistério"),
                "O Segredo do Meu Marido" to listOf("Suspense", "Drama"),
                "A Paciente Silenciosa" to listOf("Suspense", "Thriller Psicológico"),
                "Orgulho e Preconceito" to listOf("Clássico", "Romance"),
                "Dom Quixote" to listOf("Clássico", "Aventura"),
                "O Grande Gatsby" to listOf("Clássico", "Drama"),
                "Crime e Castigo" to listOf("Clássico", "Filosofia"),
                "1984" to listOf("Distopia", "Clássico"),
                "Jane Eyre" to listOf("Clássico", "Romance"),
                "Os Miseráveis" to listOf("Clássico", "Drama"),
                "A Metamorfose" to listOf("Clássico", "Ficção Absurda"),
                "Cem Anos de Solidão" to listOf("Realismo Mágico", "Clássico"),
                "O Morro dos Ventos Uivantes" to listOf("Clássico", "Romance"),
                "O Poder do Hábito" to listOf("Não Ficção", "Autoajuda"),
                "Inteligência Emocional" to listOf("Não Ficção", "Psicologia"),
                "Mindset" to listOf("Não Ficção", "Autoajuda"),
                "Rápido e Devagar" to listOf("Não Ficção", "Psicologia"),
                "Essencialismo" to listOf("Não Ficção", "Produtividade"),
                "A Sutil Arte de Ligar o F*da-se" to listOf("Não Ficção", "Autoajuda"),
                "O Milagre da Manhã" to listOf("Não Ficção", "Autoajuda"),
                "Como Fazer Amigos e Influenciar Pessoas" to listOf("Não Ficção", "Autoajuda"),
                "Roube Como um Artista" to listOf("Não Ficção", "Criatividade"),
                "Os Segredos da Mente Milionária" to listOf("Não Ficção", "Finanças"),
                "Eu Sou Malala" to listOf("Biografia", "Não Ficção"),
                "O Diário de Anne Frank" to listOf("Biografia", "História"),
                "Steve Jobs" to listOf("Biografia", "Negócios"),
                "Becoming: Minha História" to listOf("Biografia", "Memórias"),
                "Longa Caminhada até a Liberdade" to listOf("Biografia", "História"),
                "Educada" to listOf("Memórias", "Não Ficção"),
                "Compaixão e Coragem" to listOf("Não Ficção", "Psicologia"),
                "O Ano do Pensamento Mágico" to listOf("Memórias", "Não Ficção"),
                "Leonardo da Vinci" to listOf("Biografia", "História"),
                "Born a Crime" to listOf("Memórias", "Comédia"),
                "O Pequeno Príncipe" to listOf("Infantil", "Filosofia"),
                "Matilda" to listOf("Infantil", "Fantasia"),
                "Como Treinar o Seu Dragão" to listOf("Infantil", "Fantasia"),
                "Extraordinário" to listOf("Infantil", "Drama"),
                "Diário de um Banana" to listOf("Infantil", "Comédia"),
                "A Menina que Roubava Livros" to listOf("História", "Drama"),
                "As Aventuras de Tom Sawyer" to listOf("Clássico", "Aventura", "Infantil"),
                "O Jardim Secreto" to listOf("Clássico", "Infantil"),
                "Os Goonies" to listOf("Aventura", "Infantil"),
                "Jogos Vorazes" to listOf("Distopia", "Jovem Adulto"),
                "Divergente" to listOf("Distopia", "Jovem Adulto"),
                "Maze Runner" to listOf("Distopia", "Jovem Adulto"),
                "O Conto da Aia" to listOf("Distopia", "Ficção Científica"),
                "Laranja Mecânica" to listOf("Distopia", "Ficção Científica"),
                "O Doador de Memórias" to listOf("Distopia", "Jovem Adulto"),
                "Estação Onze" to listOf("Ficção Científica", "Pós-apocalíptico"),
                "O Círculo" to listOf("Distopia", "Ficção Científica"),
                "A Máquina do Tempo" to listOf("Ficção Científica", "Clássico"),
                "Nós" to listOf("Distopia", "Ficção Científica")
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

                        // Adiciona gêneros com base no mapeamento manual
                        val genresForBook = bookGenres[titulo] ?: emptyList() // Pega os gêneros do mapa ou uma lista vazia

                        genresForBook.forEach { generoNome ->
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
