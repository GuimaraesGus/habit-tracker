# 📘 Habit Tracker API

API RESTful para gerenciamento de hábitos pessoais, permitindo criação, leitura, atualização e exclusão de registros. Desenvolvido com foco em boas práticas, testes automatizados, documentação clara via diagramas e Swagger, arquitetura limpa e integração com banco de dados PostgreSQL via Docker.

---

## 🚀 Funcionalidades

- ✅ Criar novos hábitos
- 🔍 Listar todos os hábitos
- 🔎 Buscar hábito por ID
- ✏️ Atualizar hábito existente
- ❌ Remover hábito por ID
- 📁 Exportar hábitos modificados para arquivo `.json`

---

## 🛠 Tecnologias utilizadas

| Tecnologia            | Finalidade                                                        |
|-----------------------|-------------------------------------------------------------------|
| **Java 17**           | Linguagem principal do projeto                                    |
| **Spring Boot**       | Framework para desenvolvimento rápido de APIs REST                |
| **Spring Web**        | Módulo para construção dos endpoints REST                         |
| **Spring Data JPA**   | Acesso a banco de dados de forma simples e com ORM                |
| **PostgreSQL**        | Banco de dados relacional utilizado no projeto                    |
| **Docker**            | Container para o banco de dados                                   |
| **Lombok**            | Reduz a verbosidade do código eliminando getters/setters manuais  |
| **MapStruct**         | Realiza mapeamento entre DTOs e entidades automaticamente         |
| **JUnit 5**           | Framework para criação de testes unitários                        |
| **Mockito**           | Framework para mock de dependências nos testes                    |
| **Springdoc OpenAPI** | Geração automática da documentação Swagger UI                     |
| **Maven**             | Gerenciador de dependências e build                               |
| **PlantUML**          | Desenho e renderização dos diagramas e desenho de solução         |

---

## 📂 Estrutura do Projeto

```
habit-tracker/
│
├── docker/                        # Scripts para inicialização do banco
│   └── init/
│       ├── 01-init-schema.sql
│       └── 02-init-data.sql
│
├── docs/                          # PUML dos diagramas de sequência e do desenho da solução
│   └── images/                    # PNG da documentação
│
├── export/                        # Json exportado
│
└── src/
│   ├── main/
│   │   ├── java/com/api/habittracker/
│   │   │   ├── controller/        # Controladores REST
│   │   │   ├── dto/               # Objetos de transferência de dados (DTOs)
│   │   │   ├── entity/            # Entidades JPA
│   │   │   ├── exception/         # Tratamento global de erros
│   │   │   ├── mapper/            # Mapeamento entre DTOs e entidades com MapStruct
│   │   │   ├── repository/        # Interfaces de acesso ao banco (Spring Data)
│   │   │   ├── service/           # Regras de negócio
│   │   │   └── config/            # Swagger & Jackson config
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/api/habittracker/
│           ├── controller/        # Testes de API
│           └── service/           # Testes unitários
│
└── pom.xml                        # Configuração do Maven
```

---

## 📦 Instalação e Execução

### Pré-requisitos
- Java 17
- Maven 3.8+
- Docker + Docker Compose

### Executando localmente

#### Subir PostgreSQL via Docker
```bash
docker-compose up -d
```
#### Compilar e Rodar a API

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔗 Documentação da API

### Swagger UI

Após iniciar o projeto, acesse a documentação interativa em:

📄 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

> Inclui exemplos, testes de chamadas e especificações OpenAPI

---

## 🚀 Endpoints principais

| Método | Endpoint          | Descrição                  |
|--------|-------------------|----------------------------|
| GET    | /habits           | Lista todos os hábitos     |
| GET    | /habits/{id}      | Busca hábito por ID        |
| POST   | /habits           | Cria novo hábito           |
| PUT    | /habits/{id}      | Atualiza hábito existente  |
| DELETE | /habits/{id}      | Remove hábito por ID       |

---

## 📁 Exportação de Dados

Sempre que um hábito é criado ou atualizado com sucesso, a aplicação exporta todos os hábitos para o arquivo:

```
habit-tracker/export/habits-export-{timestamp-atual}.json
```

---

## 🧪 Testes

Execute os testes unitários com:

```bash
mvn test
```

Os testes incluem:
- Verificação de regras de negócio nos serviços
- Mock de dependências com Mockito
- Cobertura para criação, busca, atualização e exclusão de hábitos

---

## ⚠️ Tratamento de Erros

A API possui tratamento global de exceções via `GlobalExceptionHandler`, retornando mensagens amigáveis em caso de erro de negócio ou falhas do banco.