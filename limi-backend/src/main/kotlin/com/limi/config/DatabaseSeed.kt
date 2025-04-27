package com.limi.config

import com.limi.models.LivroEntity
import com.limi.models.AutorEntity
import com.limi.models.GeneroEntity
import com.limi.models.LivroGenero
import com.limi.models.Autores
import com.limi.models.Generos
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert


object DatabaseSeed {
    fun seedLivrosIniciais() {
        transaction {
            if (LivroEntity.count() > 0L) return@transaction // Não insere se já houver dados


            val livros = listOf(
                LivroSeed(
                    titulo = "Capitães de Areia",
                    autor = "Jorge Amado",
                    anoPublicacao = 1937,
                    sinopse = "A obra retrata a vida de um grupo de " +
                            "menores abandonados, que crescem nas ruas da cidade de Salvador, Bahia, " +
                            "vivendo em um trapiche, num ambiente hostil, roubando para sobreviver, chamados " +
                            "de \"Capitães da Areia\".  A narrativa explora a luta pela sobrevivência e a formação d" +
                            "a identidade desses jovens à margem da sociedade",
                    generos = listOf("Romance", "Ficção")
                ),
                LivroSeed(
                    titulo = "Dom Casmurro",
                    autor = "Machado de Assis",
                    anoPublicacao = 1899,
                    sinopse = "Conta a história de Bento Santiago (Bentinho), apelidado de Dom Casmurro por ser calado e introvertido." +
                            " Na adolescência, apaixona-se por Capítu, abandonando o seminário e, com ele, os desígnios traçados por sua mãe, " +
                            "Dona Glória, para que se tornasse padre. Casam-se e tudo corre bem, até o amor se tornar ciúme e desconfiança. É esta a " +
                            "grande questão que magistralmente Dom Casmurro expõe ao longo de 148 capítulos: a dúvida que paira ao longo de toda a obra sobre " +
                            "a possibilidade de traição de Capítu, agravada pela extraordinária semelhança do filho de ambos, Ezequiel, com o grande amigo de Bentinho, Escobar.",
                    generos = listOf("Romance", "Ficção")
                ),
                LivroSeed(
                    titulo = "Harry Potter e a Pedra Filosofal",
                    autor = "J.K. Rowling",
                    anoPublicacao = 1997,
                    sinopse = "Harry Potter é um garoto órfão que vive uma vida triste com seus tios e seu primo." +
                            " No seu 11º aniversário, ele descobre que é um bruxo e foi aceito na Escola de Magia e Bruxaria de Hogwarts." +
                            " Lá, ele faz amigos leais, como Rony e Hermione, e descobre mais sobre o mistério da morte de seus pais. Ao longo do ano, " +
                            "Harry se envolve em aventuras mágicas e precisa enfrentar forças obscuras ligadas ao bruxo das trevas Voldemort, " +
                            "enquanto tenta impedir que a lendária Pedra Filosofal caia em mãos erradas.",
                    generos = listOf("Fantasia", "Aventura", "Mistério","Ficção")
                ),
                LivroSeed(
                    titulo = "Senhor dos Anéis: A Sociedade do Anel",
                    autor = " J.R.R. Tolkien",
                    anoPublicacao = 1954,
                    sinopse = "Em uma terra fantástica e única, um hobbit recebe de presente de seu tio um anel mágico e maligno que precisa ser destruído antes " +
                            "que caia nas mãos do mal. Para isso, o hobbit Frodo tem um caminho árduo pela frente, onde encontra perigo, medo e seres bizarros. Ao seu " +
                            "lado para o cumprimento desta jornada, ele aos poucos pode contar com outros hobbits, um elfo, um anão, dois humanos e um mago, totalizando nove seres que formam a Sociedade do Anel.",
                    generos = listOf("Fantasia")
                ),
                LivroSeed(
                    titulo = "O Exorcista",
                    autor = "William Peter Blatty",
                    anoPublicacao = 1971,
                    sinopse = "Uma atriz vai gradativamente tomando consciência de que a sua filha de doze anos está tendo um comportamento completamente assustador. " +
                            "Deste modo, ela pede ajuda a um padre, que também é um psiquiatra, e este chega a conclusão de que a garota está possuída pelo demônio. Ele " +
                            "solicita então a ajuda de um segundo sacerdote, especialista em exorcismo, para tentar livrar a menina desta terrível possessão.",
                    generos = listOf("Terror", "Suspense", "Mistério")
                ),
                LivroSeed(
                    titulo = "O Cemitério",
                    autor = "Stephen King",
                    anoPublicacao = 1983,
                    sinopse = " Louis Creed, um jovem médico de Chicago, acredita que encontrou seu lugar em uma pequena cidade do Maine. A boa casa, o trabalho na universidade " +
                            "e a felicidade da esposa e dos filhos lhe trazem a certeza de que fez a melhor escolha. Num dos primeiros passeios pela região, conhecem um cemitério no " +
                            "bosque próximo à sua casa. Ali, gerações de crianças enterraram seus animais de estimação. Mas, para além dos pequenos túmulos, há um outro cemitério. Uma terra " +
                            "maligna que atrai pessoas com promessas sedutoras. Um universo dominado por forças estranhas capazes de tornar real o que sempre pareceu impossível. A princípio, Louis " +
                            "Creed se diverte com as histórias fantasmagóricas do vizinho Crandall. No entanto, quando o gato de sua filha Eillen morre atropelado e, subitamente, retorna à vida, ele " +
                            "percebe que há coisas que nem mesmo a sua ciência pode explicar.",
                    generos = listOf( "Terror")
                ),
                LivroSeed(
                    titulo = "Orgulho e Preconceito",
                    autor = "Jane Austen",
                    anoPublicacao = 1813,
                    sinopse = "Elizabeth Bennet é uma jovem espirituosa que enfrenta a pressão de sua sociedade para se casar bem. Quando conhece o orgulhoso Mr. Darcy, uma série de mal-entendidos e " +
                            "julgamentos precipitados cria tensões entre eles. À medida que ambos confrontam seus preconceitos, um amor verdadeiro começa a surgir.",
                    generos = listOf("Romance", "Ficção")
                ),
                LivroSeed(
                    titulo = "Duna",
                    autor = "Frank Herbert",
                    anoPublicacao = 1965,
                    sinopse = "Em um futuro distante, o jovem Paul Atreides é levado ao planeta desértico Arrakis, lar da especiaria mais valiosa do universo. Traído por nobres rivais, Paul embarca em uma " +
                            "jornada de sobrevivência e descoberta, onde seu destino se entrelaça com o de todo o império galáctico.",
                    generos = listOf("Ficção Científica", "Romance")
                ),
                LivroSeed(
                    titulo = "O Assassinato de Roger Ackroyd",
                    autor = "Agatha Christie",
                    anoPublicacao = 1926,
                    sinopse = "Na pacata vila de King’s Abbot, a morte de Roger Ackroyd desencadeia uma investigação liderada pelo brilhante detetive Hercule Poirot. Em meio a pistas enganosas e " +
                            "suspeitos improváveis, Poirot precisa usar toda sua astúcia para desvendar um crime repleto de reviravoltas.",
                    generos = listOf("Mistério", "Ficção Policial")
                ),
                LivroSeed(
                    titulo = "Longa Caminhada até a Liberdade",
                    autor = "Nelson Mandela",
                    anoPublicacao = 1994,
                    sinopse = "Nesta inspiradora autobiografia, Nelson Mandela narra sua jornada desde a infância rural até sua luta incansável contra o regime do apartheid. O livro revela os sacrifícios, " +
                            "a resiliência e a esperança que o guiaram até a presidência de uma nova África do Sul.",
                    generos = listOf("Biografia")
                ),
                LivroSeed(
                    titulo = "Sapiens: Uma Breve História da Humanidade",
                    autor = "Yuval Noah Harari",
                    anoPublicacao = 2011,
                    sinopse = "Yuval Noah Harari explora a história da espécie humana, desde os primeiros caçadores-coletores até as revoluções agrícola, científica e tecnológica. Uma reflexão provocativa " +
                            "sobre como construímos sociedades, crenças e sistemas econômicos que moldam o mundo atual.",
                    generos = listOf("Não-ficção")
                ),
                LivroSeed(
                    titulo = "Guerra e Paz",
                    autor = "Liev Tolstói",
                    anoPublicacao = 1869,
                    sinopse = "Em meio às guerras napoleônicas, as vidas de Pierre, André e Natasha se entrelaçam em uma saga monumental sobre amor, perda, ambição e redenção. 'Guerra e Paz' oferece uma " +
                            "análise profunda das forças históricas e do espírito humano.",
                    generos = listOf("Histórico", "Romance", "Ficção")
                ),
                LivroSeed(
                    titulo = "Cem Sonetos de Amor",
                    autor = "Pablo Neruda",
                    anoPublicacao = 1959,
                    sinopse = "Pablo Neruda expressa de forma magistral a profundidade do amor através de cem sonetos dedicados à sua musa, Matilde Urrutia. Cada poema transborda paixão, nostalgia e " +
                            "reverência pela beleza da vida e do sentimento humano.",
                    generos = listOf("Poesia")
                ),
                LivroSeed(
                    titulo = "O Poder do Hábito",
                    autor = "Charles Duhigg",
                    anoPublicacao = 2012,
                    sinopse = "Charles Duhigg explora o mecanismo dos hábitos, mostrando como eles se formam, como moldam nossas vidas e como podem ser transformados. Com exemplos reais, o autor revela " +
                            "estratégias práticas para construir mudanças duradouras.",
                    generos = listOf("Autoajuda")
                ),
                LivroSeed(
                    titulo = "Garota Exemplar",
                    autor = "Gillian Flynn",
                    anoPublicacao = 2012,
                    sinopse = "Amy Dunne desaparece misteriosamente no dia do seu quinto aniversário de casamento, e seu marido Nick se vê no centro de uma investigação policial intensa. À medida que a mídia " +
                            "e a opinião pública se voltam contra ele, segredos obscuros do relacionamento do casal vêm à tona, revelando uma teia de manipulações.",
                    generos = listOf("Suspense", "Mistério", "Ficção Policial")
                )
            )

            // Passo 1: Criar livros e generos
            livros.forEach { seed ->
                // Encontra ou cria o autor
                val autorEntity = AutorEntity
                    .find { Autores.nome eq seed.autor }
                    .firstOrNull()
                    ?: AutorEntity.new { nome = seed.autor }

                // Cria o livro
                val livroEntity = LivroEntity.new {
                    titulo = seed.titulo
                    autor = autorEntity
                    anoPublicacao = seed.anoPublicacao
                    sinopse = seed.sinopse
                }

                // Adiciona gêneros
                seed.generos.forEach { generoNome ->
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

// Classe auxiliar para representar os dados do livro
data class LivroSeed(
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val sinopse: String,
    val generos: List<String>
)