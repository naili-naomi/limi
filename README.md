# 📚 limi

**Limi** é um projeto de desenvolvimento de sistemas que consiste em uma plataforma de catálogo de livros com sistema de reviews. Inspirado por plataformas como **Skoob** e **Goodreads**, o objetivo é oferecer uma experiência mais simples e objetiva para usuários que desejam explorar livros e compartilhar suas opiniões.


## Ideia 

Criar um site de catálogo de livros com reviews, oferecendo funcionalidades básicas de descoberta, avaliação e cadastro de livros. Buscamos uma abordagem mais leve e direta em comparação com plataformas populares.


## Descrição

O sistema é dividido em duas experiências principais:

### Usuário Logado
- Pode adicionar e editar **reviews** de livros;
- Pode **adicionar livros** que ainda não estão no catálogo;
- Pode editar informações dos livros que adicionou;
- Pode ver todas as reviews e interagir com o conteúdo.

### Usuário Convidado (não logado)
- Pode **navegar** pelo catálogo de livros;
- Pode **pesquisar** títulos, autores ou gêneros;
- Pode ver **detalhes** dos livros;
- Pode **ler as reviews** deixadas por usuários logados.

### Reviews
- Cada review possui uma **nota de 1 a 5** estrelas;
- Acompanhada de um **comentário textual**;
- Relacionada a um usuário e a um livro específico.

## Estrutura

- **Linguagem de Programação/Back-end:** Kotlin + Ktor
- **Banco de Dados:** SQLite
- **Front-end:** HTML, CSS, JavaScript (React + Vite)
  
## Disclaimer de Uso de Inteligência Artificial

Este projeto utiliza recursos de Inteligência Artificial (IA) para fornecer algumas de suas funcionalidades. O uso das funcionalidades baseadas em IA está sujeito aos termos de uso e políticas de privacidade das plataformas e serviços envolvidos.
