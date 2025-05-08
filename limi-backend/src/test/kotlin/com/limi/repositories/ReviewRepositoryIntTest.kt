package com.limi.repositories

import com.limi.config.DatabaseFactory
import com.limi.models.Livro
import com.limi.models.Review
import com.limi.models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewRepositoryIntTest {

    private lateinit var repo: ReviewRepository
    private lateinit var livroRepo: LivroRepository
    private lateinit var userRepo: UserRepository
    private var livroId: Int = 0
    private var userId: Int = 0

    @BeforeAll
    fun setup() {
        Database.connect("jdbc:h2:mem:test-reviews;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        DatabaseFactory.init()

        repo = ReviewRepository()
        livroRepo = LivroRepository()
        userRepo = UserRepository()

        // Insere um livro e um usuário no banco para associar à review
        transaction {
            val livro = livroRepo.addLivro(
                Livro(0, "Dom Casmurro", "Machado de Assis", 1899, "Clássico da literatura", listOf("Romance"))
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

        val reviewsDoLivro = repo.getReviewsByLivroId(livroId)
        assertEquals(1, reviewsDoLivro.size)
        assertEquals("Excelente leitura, recomendo!", reviewsDoLivro[0].comentario)
        assertEquals(5, reviewsDoLivro[0].nota)
    }
}
