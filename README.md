
# 📘 Habit Tracker API

API RESTful para gerenciamento de hábitos pessoais. Permite criar, listar, atualizar e deletar hábitos, com suporte a observabilidade, documentação via Swagger, testes e integração com banco de dados PostgreSQL via Docker.

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL (via Docker)
- Lombok
- MapStruct
- Swagger/OpenAPI (springdoc)
- PlantUML
- JUnit 5 + Mockito (testes unitários)
- Maven

---

## 📁 Estrutura do Projeto

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
    ├── main/
    │   ├── java/com/example/habittracker/
    │   │   ├── controller/        # HabitController
    │   │   ├── dto/               # HabitDTO, HabitCreateDTO
    │   │   ├── entity/            # Habit.java
    │   │   ├── exception/         # GlobalExceptionHandler
    │   │   ├── mapper/            # HabitMapper
    │   │   ├── repository/        # HabitRepository
    │   │   ├── service/           # HabitService + export
    │   │   └── config/            # Swagger & Jackson config
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/com/example/habittracker/
            ├── controller/        # Testes de API
            └── service/           # Testes unitários
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### Subir PostgreSQL

```bash
docker-compose up -d
```

### Compilar e Rodar a API

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🌐 Documentação Swagger

Após o start da aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Testes

### Testes Unitários

```bash
mvn test
```

---

## 🧪 Endpoints principais

| Método | Endpoint          | Descrição                  |
|--------|-------------------|----------------------------|
| GET    | /habits           | Lista todos os hábitos     |
| GET    | /habits/{id}      | Busca hábito por ID        |
| POST   | /habits           | Cria novo hábito           |
| PUT    | /habits/{id}      | Atualiza hábito existente  |
| DELETE | /habits/{id}      | Remove hábito por ID       |

---

## ⚠️ Tratamento de Erros

A API possui tratamento global de exceções via `GlobalExceptionHandler`, retornando mensagens amigáveis em caso de erro de negócio ou falhas do banco.
