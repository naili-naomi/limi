import com.limi.models.Autores
import com.limi.models.Generos
import com.limi.models.Users
import com.limi.models.LivroGenero
import com.limi.models.Livros
import com.limi.models.Reviews

import org.junit.jupiter.api.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

abstract class DatabaseTest {
    companion object {
        val testDb = Database.connect(
            "jdbc:h2:mem:test_${UUID.randomUUID()};DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
    }

    @BeforeEach
    fun setup() {
        transaction(testDb) {
            SchemaUtils.create(  Autores,
                Users,
                Generos,
                Livros,
                LivroGenero,
                Reviews)
        }
    }

    @AfterEach
    fun cleanup() {
        transaction(testDb) {
            SchemaUtils.drop(Users, Livros, Autores, Reviews, Generos, LivroGenero)
        }
    }
}