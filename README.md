# ☕ Code & Coffee

> Sistema de gerenciamento de cafeteria para desenvolvedores

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-green)
![Kafka](https://img.shields.io/badge/Kafka-4.1-black)
![Redis](https://img.shields.io/badge/Redis-7-red)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

---

## 🚀 Sobre o Projeto

O **Code & Coffee** é um sistema de gerenciamento de cafeteria desenvolvido
com foco em boas práticas de engenharia de software.

> *"Where code meets coffee"*

---

## 🏗️ Arquitetura

O projeto segue **Arquitetura Hexagonal (Ports & Adapters)** combinada com
princípios de **Domain-Driven Design (DDD)**.
src/

├── domain/              # Regras de negócio

│   ├── model/           # Entidades e Value Objects

│   ├── exception/       # Exceções do domínio

│   └── port/

│       ├── in/          # Casos de uso (interfaces)

│       └── out/         # Repositórios e publishers (interfaces)

│

├── application/         # Implementação dos casos de uso

│   └── usecase/

│

└── infrastructure/      # Detalhes técnicos

├── persistence/     # JPA + PostgreSQL

├── messaging/       # Kafka

├── web/             # Controllers REST

└── config/          # Configurações Spring

---

## 🛠️ Stack

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 4 | Framework backend |
| PostgreSQL 16 | Banco de dados principal |
| Redis 7 | Cache do cardápio |
| Apache Kafka | Mensageria e eventos |
| Flyway | Migrations do banco |
| Docker | Containerização |
| Swagger/OpenAPI | Documentação da API |

---

## ✅ Funcionalidades

- [x] Realizar pedido com múltiplos itens
- [x] Ciclo de vida do pedido (State Pattern)
- [x] Cache do cardápio com Redis (TTL 30min)
- [x] Eventos de domínio via Kafka
- [x] Paginação no cardápio
- [x] Validações com @Valid
- [x] Documentação automática com Swagger
- [x] Migrations versionadas com Flyway
- [x] Logs estruturados em JSON (produção)
- [x] Perfis dev/prod separados
- [ ] Programa de fidelidade (em breve)
- [ ] Reserva de salas coworking (em breve)
- [ ] Pagamento PIX (em breve)

---

## 🔄 Fluxo do Pedido
CRIADO → AGUARDANDO_PAGAMENTO → PAGO → ACEITO → EM_PREPARO → PRONTO → ENTREGUE

↓

RECUSADO

CRIADO/AGUARDANDO_PAGAMENTO → CANCELADO

---

## 🚀 Como rodar

### Pré-requisitos
- Docker e Docker Compose instalados

### Subir tudo com Docker

```bash
# Clonar o repositório
git clone https://github.com/Jairo-Junior-dev/cafeteria.git
cd cafeteria

# Compilar e buildar a imagem
./mvnw package -DskipTests
docker build -t cafeteria:latest .

# Subir todos os containers
docker compose up -d
```

A aplicação estará disponível em `http://localhost:8080`

### Rodar localmente (dev)

```bash
# Subir só as dependências
docker compose up -d postgres redis kafka zookeeper

# Rodar a aplicação
./mvnw spring-boot:run
```

---

## 📖 Documentação da API

Acesse o Swagger em:
http://localhost:8080/swagger-ui/index.html

### Endpoints principais

#### Pedidos
POST   /pedidos              → realizar pedido

GET    /pedidos/{id}         → buscar pedido

PUT    /pedidos/{id}/status  → atualizar status

#### Cardápio
POST   /cardapio             → adicionar produto

GET    /cardapio             → listar produtos (paginado)

GET    /cardapio/{id}        → buscar produto

---

## 🧪 Testes

```bash
# Rodar todos os testes
./mvnw test

# Resultado esperado
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
```

---

## 🐳 Containers

| Container | Imagem | Porta |
|---|---|---|
| cafeteria-app | cafeteria:latest | 8080 |
| cafeteria-db | postgres:16 | 5432 |
| cafeteria-redis | redis:7 | 6379 |
| cafeteria-kafka | confluentinc/cp-kafka:7.5.0 | 9092 |
| cafeteria-zookeeper | confluentinc/cp-zookeeper:7.5.0 | 2181 |

---

## 👨‍💻 Autor

**Jairo Junior**

[![GitHub](https://img.shields.io/badge/GitHub-Jairo--Junior--dev-black)](https://github.com/Jairo-Junior-dev)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Jairo--Junior-blue)](https://www.linkedin.com/in/jairo-junior-ab270234a)

---

> Projeto desenvolvido como parte do roadmap de estudos rumo ao nível sênior em engenharia de software.
