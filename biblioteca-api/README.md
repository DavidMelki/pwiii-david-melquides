# Biblioteca API - Spring Boot + PostgreSQL

API REST para gerenciar o acervo de uma biblioteca: cadastro, consulta, edição e remoção de livros, com busca por título/autor, filtro por gênero e controle de disponibilidade (emprestado/devolvido).

## Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL em execução local (ou ajuste as variáveis de ambiente abaixo)

## Banco de dados

```sql
CREATE DATABASE biblioteca_db;
```

A tabela `livros` é criada automaticamente na primeira execução (`ddl-auto=update`).

As configurações podem ser sobrescritas por variáveis de ambiente:

| Variável       | Padrão                                      |
|----------------|---------------------------------------------|
| `DB_URL`       | `jdbc:postgresql://localhost:5432/biblioteca_db` |
| `DB_USER`      | `postgres`                                  |
| `DB_PASSWORD`  | `postgres`                                  |
| `PORT`         | `8080`                                      |
| `CORS_ORIGENS` | `*`                                         |
| `SHOW_SQL`     | `false`                                     |

## Executando

```bash
mvn spring-boot:run
```

- API: `http://localhost:8080/api/livros`
- Documentação Swagger: `http://localhost:8080/swagger`
- Front-end: abra `front-end/index.html` no navegador

## Endpoints

| Método | Rota                                | Descrição                                     |
|--------|-------------------------------------|-----------------------------------------------|
| GET    | `/api/livros`                       | Lista livros (filtros opcionais `busca` e `genero`) |
| GET    | `/api/livros/generos`               | Lista os gêneros aceitos                      |
| GET    | `/api/livros/{id}`                  | Busca um livro pelo id                        |
| POST   | `/api/livros`                       | Cadastra um livro                             |
| PUT    | `/api/livros/{id}`                  | Atualiza um livro                             |
| PATCH  | `/api/livros/{id}/disponibilidade`  | Marca como disponível/emprestado              |
| DELETE | `/api/livros/{id}`                  | Remove um livro                               |

Gêneros aceitos: `FICCAO`, `ROMANCE`, `FANTASIA`, `TECNOLOGIA`, `HISTORIA`, `BIOGRAFIA`, `CIENCIA`, `OUTROS`.

### Exemplo de corpo (POST/PUT)

```json
{
  "titulo": "O Hobbit",
  "autor": "J.R.R. Tolkien",
  "isbn": "978-85-359-0277-5",
  "anoPublicacao": 1937,
  "genero": "FANTASIA",
  "disponivel": true
}
```

### Exemplos com curl

```bash
# Cadastrar
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"O Hobbit","autor":"J.R.R. Tolkien","isbn":"978-85-359-0277-5","anoPublicacao":1937,"genero":"FANTASIA"}'

# Listar tudo / buscar / filtrar
curl http://localhost:8080/api/livros
curl "http://localhost:8080/api/livros?busca=tolkien"
curl "http://localhost:8080/api/livros?busca=hobbit&genero=FANTASIA"

# Buscar por id
curl http://localhost:8080/api/livros/1

# Atualizar
curl -X PUT http://localhost:8080/api/livros/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"O Hobbit","autor":"J.R.R. Tolkien","isbn":"978-85-359-0277-5","anoPublicacao":1937,"genero":"FANTASIA","disponivel":true}'

# Marcar como emprestado
curl -X PATCH http://localhost:8080/api/livros/1/disponibilidade \
  -H "Content-Type: application/json" \
  -d '{"disponivel":false}'

# Remover
curl -X DELETE http://localhost:8080/api/livros/1
```

### Respostas de erro

| Situação                          | Status |
|-----------------------------------|--------|
| Dados inválidos / JSON malformado | 400    |
| Livro não encontrado              | 404    |
| ISBN já cadastrado                | 409    |

Formato do corpo de erro:

```json
{
  "timestamp": "2026-09-30T10:54:00",
  "status": 400,
  "mensagem": "Dados inválidos na requisição",
  "detalhes": { "titulo": "O título é obrigatório" }
}
```

## Estrutura do projeto

```
src/main/java/com/biblioteca/api/
├── BibliotecaApplication.java
├── config/CorsConfig.java
├── controller/LivroController.java
├── dto/                     # LivroRequest, LivroResponse, DisponibilidadeRequest
├── exception/               # exceções de domínio + handler global
├── model/                   # entidade Livro e enum Genero
├── repository/LivroRepository.java
└── service/LivroService.java
```

## Decisões de projeto

- A entidade não é exposta diretamente: entrada e saída usam DTOs (`record`).
- O ISBN é normalizado (sem hífens/espaços) e é único no banco.
- Consultas com filtros opcionais são resolvidas na camada de serviço, evitando parâmetros nulos em JPQL.
