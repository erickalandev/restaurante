# API de Gestão de Restaurante

API REST desenvolvida em Java com Spring Boot para gerenciamento de restaurantes, usuários e itens do cardápio.

## Visão geral

A aplicação permite:
- cadastrar, consultar, atualizar e excluir usuários clientes;
- cadastrar, consultar, atualizar e excluir restaurantes;
- cadastrar, consultar e remover itens do cardápio vinculados a um restaurante;
- validar regras de negócio e persistir os dados com JPA/MySQL;
- documentar os endpoints com Swagger/OpenAPI.

## Arquitetura

O projeto segue uma estrutura em camadas:

- `domain`: entidades e regras de negócio centrais;
- `application`: DTOs, gateways e use cases;
- `infraestrutura`: controllers, repositories, datasource e persistência;
- `presenter`: conversão entre domínio, entidades e DTOs;
- `exception`: tratamento global de erros;
- `utils`: utilitários de validação, lógica e logs;
- `config`: configuração da documentação Swagger.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- MySQL
- H2 (para testes)
- Maven
- SpringDoc OpenAPI (Swagger)

## Requisitos

- JDK 21
- Maven
- MySQL em execução

## Variáveis de ambiente

Antes de iniciar a aplicação, configure as variáveis abaixo:

```bash
export DATABASE_URL="localhost:3306/restaurante"
export DATABASE_USERNAME="seu_usuario"
export DATABASE_PASSWORD="sua_senha"
```

No Windows PowerShell:

```powershell
$env:DATABASE_URL = "localhost:3306/restaurante"
$env:DATABASE_USERNAME = "seu_usuario"
$env:DATABASE_PASSWORD = "sua_senha"
```

## Executando o projeto

Clone o repositório e execute:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

A aplicação será iniciada na porta:

```text
http://localhost:8080
```

Com o contexto da API:

```text
http://localhost:8080/api/v1
```

## Documentação Swagger

A documentação da API fica disponível em:

- Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/v1/v3/api-docs`

## Endpoints da API

### Usuários

- `POST /api/v1/usuario/consultar`
  - Consulta usuários por filtro.
- `POST /api/v1/usuario/cadastrar-cliente`
  - Cadastra um novo cliente.
- `PUT /api/v1/usuario/atualizar`
  - Atualiza um usuário existente.
- `DELETE /api/v1/usuario/deletar`
  - Remove um usuário pelo CPF informado no header `cpf`.

### Restaurantes

- `POST /api/v1/restaurante/consultar`
  - Consulta restaurantes por filtro.
- `POST /api/v1/restaurante/cadastrar`
  - Cadastra um novo restaurante.
- `PUT /api/v1/restaurante/atualizar`
  - Atualiza um restaurante existente.
  - Headers obrigatórios: `nomeRestaurante`, `donoRestaurante`
- `DELETE /api/v1/restaurante/deletar`
  - Remove um restaurante.
  - Headers obrigatórios: `nomeRestaurante`, `donoRestaurante`

### Itens do cardápio

- `POST /api/v1/itens-cardapio/cadastrar`
  - Cadastra itens para um restaurante.
  - Headers obrigatórios: `nomeRestaurante`, `donoRestaurante`
- `GET /api/v1/itens-cardapio/consultar`
  - Consulta itens de um restaurante.
  - Headers obrigatórios: `nomeRestaurante`, `donoRestaurante`
- `DELETE /api/v1/itens-cardapio/deletar`
  - Remove um item do cardápio pelo `codigoItemCardapio`.
- `PUT /api/v1/itens-cardapio/atualizar`
  - Atualiza um item existente.

## Estrutura de pastas

```text
src/
├── main/
│   ├── java/
│   │   └── com/tech/challenge/fiap/gestao/restaurante/
│   │       ├── application/
│   │       ├── config/
│   │       ├── domain/
│   │       ├── exception/
│   │       ├── infraestrutura/
│   │       ├── presenter/
│   │       ├── utils/
│   │       └── RestauranteApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/
```

## Observações importantes

- O projeto foi desenvolvido em Java 21; utilizar uma versão inferior pode causar falhas de compilação.
- Em produção/local real, a aplicação depende de MySQL configurado nas variáveis de ambiente.
- A documentação interativa no Swagger facilita testes e integração com clientes externos.

## Contribuição

Para contribuir com o projeto:

1. Crie uma branch para a sua alteração;
2. Faça a implementação;
3. Execute os testes;
4. Abra um pull request com a descrição da mudança.

## Licença

Este projeto foi desenvolvido como parte de atividade acadêmica/tecnológica e pode ser adaptado conforme necessidade do time ou da instituição.
