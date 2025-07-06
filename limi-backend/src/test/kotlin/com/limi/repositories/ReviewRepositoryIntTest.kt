package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.models.Livro
import com.limi.models.Review
import com.limi.models.User
import io.ktor.client.* 
import io.ktor.client.engine.cio.* 
import io.ktor.client.plugins.contentnegotiation.* 
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewRepositoryIntTest {

    private lateinit var repo: ReviewRepository
    private lateinit var livroRepo: LivroRepository
    private lateinit var userRepo: UserRepository
    private var livroId: Int = 0
    private var userId: Int = 0

    @BeforeAll
    fun setup() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
        DatabaseFactory.init(
            client = client,
            url = "jdbc:h2:mem:test-reviews;DB_CLOSE_DELAY=-1", 
            driver = "org.h2.Driver"
        )

        repo = ReviewRepository()
        livroRepo = LivroRepository()
        userRepo = UserRepository()

        // Insere um livro e um usuário no banco para associar à review
        transaction {
            val livro = livroRepo.addLivro(
                Livro(0, "Dom Casmurro", "Machado de Assis", 1899, "Clássico da literatura", null, listOf("Romance"))
            )
            val user = userRepo.addUser(
                User(0, "Carlos", "carluxo", "carlos@teste.com", "senha123")
            )
            livroId = livro.id
            userId = user.id
        }
    }

    @Test
    fun `deve adicionar e recuperar review`() {
        val review = Review(
            id = 0,
            livroId = livroId,
            userId = userId,
            comentario = "Excelente leitura, recomendo!",
            nota = 5
        )

        val criado = repo.addReview(review)
        assertNotNull(criado.id)

        val reviewsDoLivro = repo.getReviewsByBookId(livroId)
        assertEquals(1, reviewsDoLivro.size)
        assertEquals("Excelente leitura, recomendo!", reviewsDoLivro[0].comentario)
        assertEquals(5, reviewsDoLivro[0].nota)
    }

    

}