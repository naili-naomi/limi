# 📚 limi

**Limi** é um projeto de desenvolvimento de sistemas que consiste em uma plataforma de catálogo de livros com sistema de reviews. Inspirado por plataformas como **Skoob** e **Goodreads**, o objetivo[...]

## Ideia 

Criar um site de catálogo de livros com reviews, oferecendo funcionalidades básicas de descoberta, avaliação e cadastro de livros. Buscamos uma abordagem mais leve e direta em comparação com pla[...]

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
  
## Como Rodar o Projeto

Para executar a aplicação completa, você precisará iniciar o back-end e o front-end em terminais separados.

---

## ⚠️ Pendências antes de rodar

Antes de rodar o projeto, é necessário ajustar algumas variáveis e caminhos:

1. **Caminho do Java**  
   No arquivo `/limi/limi-backend/gradle.properties`, altere o caminho do Java instalado na sua máquina.  
   Você pode encontrar o caminho rodando `echo $JAVA_HOME`.

   ```properties
   org.gradle.java.home=/usr/lib/jvm/seu-java
   ```

2. **Caminho do banco de dados**  
   No arquivo `/limi/limi-backend/src/main/kotlin/com/limi/Application.kt`, altere o caminho do banco `catalogo.db` para o da sua máquina local.

   ```kotlin
   url = "jdbc:sqlite:seu/caminho/limi/catalogo.db",
   ```

3. **Variáveis para "Esqueci minha senha"**  
   Para a função de recuperação de senha, exporte as seguintes variáveis no seu terminal:

   ```bash
   export GMAIL_PASSWORD="engfrlnjmpbgdbnu"
   export GMAIL_USERNAME="adm.limi1234@gmail.com"
   ```

---

### 1. Rodando o Back-end (Kotlin + Ktor)

O back-end é responsável por servir a API e se conectar ao banco de dados.

1. **Navegue até a pasta do back-end:**
    ```bash
    cd limi-backend
    ```

2. **Execute o servidor:**  
   Utilize o Gradle Wrapper para iniciar a aplicação.

   - No Linux ou macOS:
     ```bash
     ./gradlew run
     ```
   - No Windows:
     ```bash
     .\gradlew.bat run
     ```

O servidor estará rodando em `http://localhost:8080`.

### 2. Rodando o Front-end (React + Vite)

O front-end é a interface com a qual o usuário interage.

1. **Navegue até a pasta do front-end em outro terminal:**
    ```bash
    cd limi-frontend
    ```

2. **Instale as dependências:**  
   Se for a primeira vez rodando, instale os pacotes necessários:
    ```bash
    npm install
    ```

3. **Inicie o servidor de desenvolvimento:**
    ```bash
    npm run dev
    ```

A aplicação estará acessível em `http://localhost:5173` (ou em outra porta, se a 5173 estiver ocupada).

## Disclaimer de Uso de Inteligência Artificial

Este projeto utiliza recursos de Inteligência Artificial (IA) para fornecer algumas de suas funcionalidades. O uso das funcionalidades baseadas em IA está sujeito aos termos de uso e políticas de p[...]
