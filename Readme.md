# 💼 Digibank - Vitrine Técnica

Este projeto é uma vitrine técnica de um sistema bancário completo, simulando um ambiente com **Banco Digital**, **Corretora de Investimentos** e **Programa de Pontos**, construído com foco em Clean Architecture, modularização e boas práticas de engenharia de software.

---

## 🧱 Arquitetura

O sistema utiliza **Clean Architecture** e está dividido em quatro camadas principais:

- **domain**: Entidades, interfaces e regras de negócio puras.
- **application**: Casos de uso e lógica de orquestração.
- **infrastructure**: Banco de dados, Kafka, Redis, autenticação e persistência.
- **presentation**: Camada REST e autenticação JWT.

---

## ⚙️ Tecnologias

### Backend
- **Java 17**
- **Spring Boot**
- **Maven**
- **Spring Data JPA**
- **PostgreSQL**
- **Redis**
- **Apache Kafka**
- **Spring Security (JWT)**
- **Testcontainers**
- **Lombok**
- **Swagger/OpenAPI**
---

## 📁 Estrutura do Projeto

```bash
. Modulo (bank, broker, points, shared)
├── domain
│   └── model, repository, enums
├── application
│   └── usecases, dto, services
├── infrastructure
│   └── repositories
├── presentation
│   └── controller, filter, exception, presenters
│
├── shared
│   └── configurations, kafka, redis...
├── docker-compose.yml
├── application.yml
├── pom.xml
└── README.md
```

---

## 🧪 Testes

- **Testes Unitários**: JUnit + Mockito
- **Testes de Integração**: Spring Boot Test + Testcontainers
- **Cobertura**: Jacoco

---

## 🔐 Autenticação e Segurança

- **JWT** com filtro customizado
- Integração com `SecurityContextHolder` para auditoria automática
- Usuários autenticados têm acesso às suas transações, contas e ordens

---

## 🔄 Kafka Events

- `TransacaoEfetuadaEvent`
- `OrdemExecutadaEvent`
- `PontosGeradosEvent`

---

## 📦 Como Rodar

### 1. Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### 2. Subir infraestrutura

```bash
docker-compose up -d
```

### 3. Build e execução

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔄 CI/CD - GitHub Actions

O projeto possui pipeline automatizado que executa:

- Build Maven
- Execução de testes
- Análise de cobertura
- Docker build/push

---

## 🔍 Documentação

Após subir o app:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

---

## 🧠 Diagrama C4

O projeto é documentado em 4 níveis (C4):

- **Contexto**
- **Contêiner**
- **Componentes**
- **Código**

Os diagramas estão na pasta `/docs/diagrams`.

---

## 🎯 Objetivo

Este projeto visa demonstrar:

- Alto padrão de organização e modularização
- Uso de padrões (DDD, SOLID, TDD)
- Integração realista com infraestrutura (Kafka, Redis, PostgreSQL)
- Estratégias de auditoria, autenticação e mensageria

---

## 🗂️ Licença

Este projeto é open-source, livre para uso e adaptação. Ideal para entrevistas, provas técnicas ou como base para sistemas reais.

---