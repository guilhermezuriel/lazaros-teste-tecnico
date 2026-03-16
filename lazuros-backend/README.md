# Lazaros Backend

Sistema backend desenvolvido com Spring Boot implementando uma arquitetura modular seguindo princípios de Clean Architecture e Domain-Driven Design (DDD), utilizando Spring Modulith para garantir limites claros entre módulos.

## Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- [Estrutura de Módulos](#estrutura-de-módulos)
- [Estrutura de Pacotes](#estrutura-de-pacotes)
- [Endpoints da API](#endpoints-da-api)
- [Casos de Uso](#casos-de-uso)
- [Modelo de Dados](#modelo-de-dados)
- [Testes](#testes)
- [Configuração e Execução](#configuração-e-execução)
- [Executar com Docker](#executar-com-docker)
- [Decisões Arquiteturais](#decisões-arquiteturais)
- [Possíveis Evoluções](#possíveis-evoluções)

## Tecnologias Utilizadas

| Categoria | Tecnologia | Versão |
|-----------|-----------|--------|
| Framework | Spring Boot | 4.0.3 |
| Arquitetura Modular | Spring Modulith | 1.3.4 |
| Web | Spring Web MVC | - |
| Monitoramento | Spring Boot Actuator | - |
| Banco de Dados (Prod) | PostgreSQL | 15 |
| Banco de Dados (Test) | H2 Database | Latest |
| ORM | Spring Data JDBC | - |
| Build Tool | Maven | - |
| Java | OpenJDK | 17 |
| Testes | JUnit 5 + AssertJ + Mockito | - |
| Utilitários | Lombok | Latest |
| Containerização | Docker + Docker Compose | Latest |

## Arquitetura do Sistema

### Clean Architecture + Spring Modulith

O projeto implementa uma **Arquitetura Limpa (Clean Architecture)** combinada com **Spring Modulith** para criar um monólito modular com limites bem definidos entre os módulos.

#### Princípios Aplicados

1. **Separação de Responsabilidades**: Cada camada tem uma responsabilidade específica
2. **Inversão de Dependência**: As dependências apontam para dentro (infrastructure → application → domain)
3. **Independência de Framework**: A lógica de negócio não depende de frameworks externos
4. **Testabilidade**: Cada camada pode ser testada isoladamente
5. **Limites de Módulos**: Spring Modulith garante que as dependências entre módulos sejam respeitadas

### Diagrama de Camadas

```
┌─────────────────────────────────────────────────────────┐
│                    Infrastructure Layer                  │
│  (Controllers, Repositories, Config, Data Initializers)  │
└────────────────────────┬────────────────────────────────┘
                         │
                         ↓
┌─────────────────────────────────────────────────────────┐
│                    Application Layer                     │
│         (Use Cases, Commands, Results, DTOs)             │
└────────────────────────┬────────────────────────────────┘
                         │
                         ↓
┌─────────────────────────────────────────────────────────┐
│                      Domain Layer                        │
│    (Entities, Value Objects, Repository Interfaces,      │
│             Business Rules, Domain Exceptions)           │
└─────────────────────────────────────────────────────────┘
```

## Estrutura de Módulos

O sistema é composto por **2 módulos principais**:

### 1. Profile Module (Módulo de Perfis)

**Pacote**: `com.guilhermezuriel.lazurosbackend.profile`

**Responsabilidade**: Gerenciar perfis/roles de usuários do sistema

**Dependências**: Nenhuma (módulo independente)

**API Pública**: `ProfileService`

### 2. User Module (Módulo de Usuários)

**Pacote**: `com.guilhermezuriel.lazurosbackend.user`

**Responsabilidade**: Gerenciar usuários e suas associações com perfis

**Dependências**: Profile Module (declarada explicitamente)

**API Pública**: `UserService`

### Dependências entre Módulos

```
┌─────────────────┐
│  User Module    │
│                 │
│  @ApplicationModule(allowedDependencies = {"profile"})
└────────┬────────┘
         │ depende de
         ↓
┌─────────────────┐
│  Profile Module │
│                 │
│  @ApplicationModule
└─────────────────┘
```

**Nota**: A dependência do User Module para o Profile Module é explícita e validada em tempo de compilação pelo Spring Modulith através de testes de arquitetura.

## Estrutura de Pacotes

Cada módulo segue uma estrutura de pacotes padronizada baseada em Clean Architecture:

```
module/
│
├── domain/                          # Camada de Domínio
│   ├── Entity.java                  # Entidade de domínio com regras de negócio
│   ├── EntityId.java                # Value Object para identidade
│   ├── EntityRepository.java        # Interface do repositório (porta)
│   └── exception/                   # Exceções de domínio
│       ├── InvalidEntityException.java
│       └── ...
│
├── application/                     # Camada de Aplicação
│   ├── usecase/                     # Casos de uso
│   │   ├── CreateEntityUseCase.java
│   │   ├── GetEntityUseCase.java
│   │   ├── ListEntitiesUseCase.java
│   │   ├── UpdateEntityUseCase.java
│   │   └── DeleteEntityUseCase.java
│   └── dto/                         # Data Transfer Objects
│       ├── CreateEntityCommand.java
│       ├── UpdateEntityCommand.java
│       └── EntityResult.java
│
├── infrastructure/                  # Camada de Infraestrutura
│   ├── web/                         # Adaptador Web (REST API)
│   │   ├── EntityController.java
│   │   ├── EntityRequest.java
│   │   ├── EntityResponse.java
│   │   └── EntityExceptionHandler.java
│   ├── persistence/                 # Adaptador de Persistência
│   │   ├── EntityRepositoryAdapter.java
│   │   ├── JdbcEntityRepository.java
│   │   └── EntityJdbcEntity.java
│   ├── config/                      # Configurações do módulo
│   │   └── EntityBeanConfiguration.java
│   └── seed/                        # Inicialização de dados
│       └── EntityDataInitializer.java
│
├── EntityService.java               # API pública do módulo (façade)
├── EntityResult.java                # DTO de resultado compartilhado
├── EntityId.java                    # Value Object compartilhado
├── EntityNotFoundException.java     # Exceção de nível de módulo
└── package-info.java                # Declaração do módulo (@ApplicationModule)
```

### Descrição das Camadas

#### Domain Layer (Camada de Domínio)

Contém a lógica de negócio pura, independente de frameworks:

- **Entities**: Classes de domínio com regras de negócio encapsuladas
- **Value Objects**: Objetos imutáveis que representam conceitos do domínio (ex: IDs)
- **Repository Interfaces**: Contratos (portas) para persistência
- **Domain Exceptions**: Exceções específicas do domínio

**Características**:
- Sem dependências externas (exceto Java core)
- Regras de validação no domínio
- Métodos factory (ex: `create()`, `update()`)
- Encapsulamento total

#### Application Layer (Camada de Aplicação)

Orquestra a lógica de negócio através de casos de uso:

- **Use Cases**: Implementam um caso de uso específico do sistema
- **Commands**: Dados de entrada para os casos de uso
- **Results/DTOs**: Dados de saída dos casos de uso

**Características**:
- Coordena operações entre entidades
- Valida regras que envolvem múltiplas entidades
- Transforma dados entre camadas
- Package-private (não são expostos fora do módulo)

#### Infrastructure Layer (Camada de Infraestrutura)

Implementa adaptadores para tecnologias específicas:

- **Web**: Controllers REST, DTOs de request/response, exception handlers
- **Persistence**: Implementações dos repositórios, entidades JDBC
- **Config**: Configuração de beans e dependências
- **Seed**: Inicialização de dados para desenvolvimento

**Características**:
- Depende de frameworks (Spring, JDBC, etc.)
- Implementa as portas definidas no domínio
- Package-private (exceto configurações)
- Isolada do domínio

## Endpoints da API

### Profile Endpoints

**Base URL**: `/api/profiles`

| Método | Endpoint | Descrição | Request Body | Response | Status |
|--------|----------|-----------|--------------|----------|--------|
| POST | `/api/profiles` | Cria um novo perfil | `ProfileRequest` | `ProfileResponse` | 201 |
| GET | `/api/profiles` | Lista todos os perfis | - | `List<ProfileResponse>` | 200 |
| GET | `/api/profiles/{id}` | Busca perfil por ID | - | `ProfileResponse` | 200/404 |
| PUT | `/api/profiles/{id}` | Atualiza um perfil | `ProfileRequest` | `ProfileResponse` | 200/404 |
| DELETE | `/api/profiles/{id}` | Deleta um perfil | - | - | 204/404 |

#### ProfileRequest
```json
{
  "descricao": "string (mínimo 5 caracteres)"
}
```

#### ProfileResponse
```json
{
  "id": "uuid",
  "descricao": "string"
}
```

### User Endpoints

**Base URL**: `/api/users`

| Método | Endpoint | Descrição | Request Body | Response | Status |
|--------|----------|-----------|--------------|----------|--------|
| POST | `/api/users` | Cria um novo usuário | `UserRequest` | `UserResponse` | 201 |
| GET | `/api/users` | Lista todos os usuários | - | `List<UserResponse>` | 200 |
| GET | `/api/users/{id}` | Busca usuário por ID | - | `UserResponse` | 200/404 |
| PUT | `/api/users/{id}` | Atualiza um usuário | `UserRequest` | `UserResponse` | 200/404 |
| DELETE | `/api/users/{id}` | Deleta um usuário | - | - | 204/404 |

#### UserRequest
```json
{
  "nome": "string (mínimo 10 caracteres)",
  "perfis": ["uuid", "uuid", ...]
}
```

#### UserResponse
```json
{
  "id": "uuid",
  "nome": "string",
  "perfis": [
    {
      "id": "uuid",
      "descricao": "string"
    }
  ]
}
```

### Códigos de Status HTTP

| Status | Descrição | Quando Ocorre |
|--------|-----------|---------------|
| 200 OK | Sucesso | GET, PUT bem-sucedidos |
| 201 Created | Criado | POST bem-sucedido |
| 204 No Content | Sem conteúdo | DELETE bem-sucedido |
| 400 Bad Request | Requisição inválida | Dados de domínio inválidos |
| 404 Not Found | Não encontrado | Entidade não existe |
| 422 Unprocessable Entity | Entidade não processável | Erro genérico de negócio |
| 500 Internal Server Error | Erro interno | Erro não tratado |

## Casos de Uso

### Profile Module

#### 1. CreateProfileUseCase

**Descrição**: Cria um ou mais perfis no sistema

**Input**: `CreateProfileCommand`
- `descricao`: String (mínimo 5 caracteres)

**Output**: `ProfileResult`

**Regras de Negócio**:
- Descrição não pode ser nula ou vazia
- Descrição deve ter no mínimo 5 caracteres
- Suporta criação em lote (múltiplos perfis)

**Exceções**:
- `InvalidProfileException`: Quando a validação falha

---

#### 2. GetProfileUseCase

**Descrição**: Busca um perfil por ID

**Input**: `ProfileId`

**Output**: `ProfileResult`

**Exceções**:
- `ProfileNotFoundException`: Quando o perfil não existe

---

#### 3. ListProfilesUseCase

**Descrição**: Lista todos os perfis cadastrados

**Input**: Nenhum

**Output**: `List<ProfileResult>`

---

#### 4. UpdateProfileUseCase

**Descrição**: Atualiza a descrição de um perfil existente

**Input**: 
- `ProfileId`
- `UpdateProfileCommand`

**Output**: `ProfileResult`

**Regras de Negócio**:
- Mesmas validações do create
- Perfil deve existir

**Exceções**:
- `ProfileNotFoundException`: Quando o perfil não existe
- `InvalidProfileException`: Quando a validação falha

---

#### 5. DeleteProfileUseCase

**Descrição**: Remove um perfil do sistema

**Input**: `ProfileId`

**Output**: `void`

**Regras de Negócio**:
- Perfil não pode estar associado a nenhum usuário (constraint do banco)

**Exceções**:
- `ProfileNotFoundException`: Quando o perfil não existe
- Database constraint violation: Quando o perfil está em uso

---

### User Module

#### 1. CreateUserUseCase

**Descrição**: Cria um novo usuário com perfis associados

**Input**: `CreateUserCommand`
- `nome`: String (mínimo 10 caracteres)
- `perfis`: List<UUID> (mínimo 1 perfil)

**Output**: `UserResult`

**Regras de Negócio**:
- Nome não pode ser nulo ou vazio
- Nome deve ter no mínimo 10 caracteres
- Usuário deve ter pelo menos 1 perfil
- Todos os perfis informados devem existir

**Dependências entre Módulos**:
- Chama `ProfileService.existsAllById()` para validar perfis
- Chama `ProfileService.findAllById()` para popular dados dos perfis

**Exceções**:
- `InvalidUserException`: Quando a validação falha
- `IllegalArgumentException`: Quando perfis não existem

---

#### 2. GetUserUseCase

**Descrição**: Busca um usuário por ID com seus perfis

**Input**: `UserId`

**Output**: `UserResult` (com perfis populados)

**Dependências entre Módulos**:
- Chama `ProfileService.findAllById()` para popular perfis

**Exceções**:
- `UserNotFoundException`: Quando o usuário não existe

---

#### 3. ListUsersUseCase

**Descrição**: Lista todos os usuários com seus perfis

**Input**: Nenhum

**Output**: `List<UserResult>` (com perfis populados)

**Dependências entre Módulos**:
- Chama `ProfileService.findAllById()` para cada usuário

---

#### 4. UpdateUserUseCase

**Descrição**: Atualiza dados de um usuário existente

**Input**: 
- `UserId`
- `UpdateUserCommand`

**Output**: `UserResult`

**Regras de Negócio**:
- Suporta atualização parcial (campos nulos são ignorados)
- Mesmas validações do create
- Usuário deve existir

**Dependências entre Módulos**:
- Chama `ProfileService.existsAllById()` se perfis foram alterados
- Chama `ProfileService.findAllById()` para popular dados

**Exceções**:
- `UserNotFoundException`: Quando o usuário não existe
- `InvalidUserException`: Quando a validação falha
- `IllegalArgumentException`: Quando perfis não existem

---

#### 5. DeleteUserUseCase

**Descrição**: Remove um usuário do sistema

**Input**: `UserId`

**Output**: `void`

**Regras de Negócio**:
- Remove automaticamente associações com perfis (cascade)

**Exceções**:
- `UserNotFoundException`: Quando o usuário não existe

## Modelo de Dados

### Diagrama de Entidades

```
┌─────────────────────┐           ┌─────────────────────┐
│      profiles       │           │       users         │
├─────────────────────┤           ├─────────────────────┤
│ id (UUID) PK        │           │ id (UUID) PK        │
│ descricao (VARCHAR) │           │ nome (VARCHAR)      │
└──────────┬──────────┘           └──────────┬──────────┘
           │                                  │
           │                                  │
           │        ┌──────────────────┐     │
           └────────│  user_profiles   │─────┘
                    ├──────────────────┤
                    │ user_id (UUID) FK│
                    │ profile_id (UUID) FK│
                    └──────────────────┘
                    Composite PK: (user_id, profile_id)
```

### Schema SQL

```sql
-- Tabela de Perfis
CREATE TABLE profiles (
    id        UUID         NOT NULL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    CONSTRAINT chk_descricao_min_length 
        CHECK (LENGTH(TRIM(descricao)) >= 5)
);

-- Tabela de Usuários
CREATE TABLE users (
    id   UUID         NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT chk_nome_min_length 
        CHECK (LENGTH(TRIM(nome)) >= 10)
);

-- Tabela de Relacionamento Muitos-para-Muitos
CREATE TABLE user_profiles (
    user_id    UUID NOT NULL 
        REFERENCES users(id) ON DELETE CASCADE,
    profile_id UUID NOT NULL 
        REFERENCES profiles(id) ON DELETE RESTRICT,
    PRIMARY KEY (user_id, profile_id)
);
```

### Características do Banco de Dados

#### Constraints e Validações

1. **Validação de Descrição do Perfil**
   - Não pode ser nula
   - Comprimento mínimo de 5 caracteres (após trim)
   - Validada no banco E no domínio

2. **Validação de Nome do Usuário**
   - Não pode ser nulo
   - Comprimento mínimo de 10 caracteres (após trim)
   - Validada no banco E no domínio

3. **Integridade Referencial**
   - `ON DELETE CASCADE`: Deletar usuário remove suas associações de perfil
   - `ON DELETE RESTRICT`: Não é possível deletar perfil se está associado a usuários

#### Spring Data JDBC

**Por que JDBC e não JPA?**

- **Simplicidade**: Sem lazy loading, sem proxies, sem cache de primeiro nível
- **Controle**: Mapeamento explícito, queries previsíveis
- **Performance**: Menos overhead que Hibernate
- **Transparência**: Sabemos exatamente quando há acesso ao banco

**Padrão de Mapeamento**:

```java
// Entidade de Domínio (domain/)
public class User {
    private final UserId id;
    private final String nome;
    private final List<ProfileId> perfis;
}

// Entidade JDBC (infrastructure/persistence/)
public class UserJdbcEntity implements Persistable<UUID> {
    private UUID id;
    private String nome;
    // Relacionamentos tratados separadamente
}

// Adapter (infrastructure/persistence/)
public class UserRepositoryAdapter implements UserRepository {
    // Converte entre User e UserJdbcEntity
}
```

### Dados Iniciais (Seed)

#### Perfis Criados
1. Administrador
2. Usuário Padrão
3. Gerente de Projetos
4. Analista de Sistemas
5. Suporte Técnico

#### Usuários Criados
1. Alice Ferreira da Silva
2. Bruno Costa Mendes
3. Carla Rodrigues Alves
4. Daniel Santos Oliveira
5. Eduardo Lima Pereira

**Nota**: Cada usuário recebe aleatoriamente entre 1 e 3 perfis durante a inicialização.

## Testes

### Estrutura de Testes

```
src/test/java/
│
├── ArchitectureTest.java                      # Testes de arquitetura (Modulith)
├── LazurosBackendApplicationTests.java        # Teste de inicialização
│
├── profile/
│   ├── domain/
│   │   └── ProfileTest.java                   # 9 testes de domínio
│   └── application/
│       └── CreateProfileUseCaseTest.java      # 2 testes de caso de uso
│
└── user/
    ├── domain/
    │   └── UserTest.java                      # 12 testes de domínio
    └── application/
        └── CreateUserUseCaseTest.java         # 4 testes de caso de uso
```


## Configuração e Execução

### Pré-requisitos

#### Desenvolvimento Local
- **Java 17** ou superior
- **Maven 3.8+**
- **PostgreSQL 12+**

#### Com Docker (Recomendado)
- **Docker** 20.10+
- **Docker Compose** 2.0+

### Configuração do Banco de Dados

#### Opção 1: PostgreSQL Local

```bash
# Criar banco de dados
createdb lazuros

# Ou via psql
psql -U postgres
CREATE DATABASE lazuros;
```

#### Opção 2: PostgreSQL com Docker

```bash
docker run --name lazuros-postgres \
  -e POSTGRES_DB=lazuros \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:15-alpine
```

### Configuração da Aplicação

O arquivo `src/main/resources/application.yml` contém:

```yaml
spring:
  application:
    name: lazuros-backend
  
  datasource:
    url: jdbc:postgresql://localhost:5432/lazuros
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  
  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
```

**Variáveis de Ambiente** (opcional):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/lazuros
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

### Executar a Aplicação

#### Com Maven

```bash
# Desenvolvimento
./mvnw spring-boot:run

# Build e execução
./mvnw clean package
java -jar target/lazuros-backend-0.0.1-SNAPSHOT.jar
```

#### Com IDE

1. Importar projeto como Maven Project
2. Executar classe `LazurosBackendApplication`
3. Acessar: http://localhost:8080

### Verificar Funcionamento

```bash
# Health check (Spring Boot Actuator)
curl http://localhost:8080/actuator/health

# Listar perfis
curl http://localhost:8080/api/profiles

# Listar usuários
curl http://localhost:8080/api/users
```

### Dados Iniciais

Ao iniciar, a aplicação automaticamente:
1. Cria o schema do banco de dados (`schema.sql`)
2. Insere 5 perfis padrão
3. Insere 5 usuários de exemplo

---

## Executar com Docker

### Opção 1: Docker Compose (Recomendado)

A maneira mais fácil de rodar a aplicação é usando Docker Compose, que sobe tanto o PostgreSQL quanto a aplicação:

```bash
# Subir todos os serviços (build + run)
docker-compose up --build

# Ou em background
docker-compose up -d --build

# Ver logs
docker-compose logs -f app

# Parar os serviços
docker-compose down

# Parar e remover volumes (limpa o banco)
docker-compose down -v
```

A aplicação estará disponível em: `http://localhost:8080`

### Opção 2: Apenas Build da Imagem

Se você quiser apenas construir a imagem Docker da aplicação:

```bash
# Build da imagem
docker build -t lazuros-backend:latest .

# Executar (assumindo PostgreSQL já rodando)
docker run -d \
  --name lazuros-backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/lazuros \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  lazuros-backend:latest
```

### Opção 3: Somente o Banco de Dados

Se você quiser rodar apenas o PostgreSQL com Docker e a aplicação localmente:

```bash
# Subir apenas o PostgreSQL
docker-compose up -d postgres

# Executar a aplicação localmente
./mvnw spring-boot:run
```

### Arquitetura do Docker

#### Dockerfile Multi-Stage

O Dockerfile usa uma estratégia **multi-stage build** para otimizar o tamanho da imagem:

**Stage 1 - Build**: Usa `eclipse-temurin:17-jdk-alpine` para compilar o projeto
- Copia dependências e código fonte
- Executa o build Maven
- Gera o JAR da aplicação

**Stage 2 - Runtime**: Usa `eclipse-temurin:17-jre-alpine` (apenas JRE)
- Copia apenas o JAR compilado
- Imagem final muito menor (~200MB vs ~500MB)
- Usuário não-root para segurança
- Healthcheck configurado

**Benefícios**:
- Imagem de produção leve
- Sem ferramentas de build na imagem final
- Mais segura (menos superfície de ataque)
- Startup mais rápido

#### Docker Compose

O arquivo `docker-compose.yml` define:

**Serviço PostgreSQL**:
- Imagem: `postgres:15-alpine`
- Porta: 5432
- Volume persistente para dados
- Healthcheck configurado

**Serviço App**:
- Build da aplicação Spring Boot
- Porta: 8080
- Depende do PostgreSQL (aguarda healthcheck)
- Variáveis de ambiente configuradas
- Healthcheck via actuator

**Network**:
- Bridge network isolada (`lazuros-network`)
- Comunicação interna entre containers
- Resolução de nomes automática

**Volumes**:
- `postgres_data`: Persiste dados do PostgreSQL
- Dados sobrevivem ao `docker-compose down`

### Variáveis de Ambiente

Você pode customizar a configuração através de variáveis de ambiente:

```bash
# Criar arquivo .env na raiz do projeto
cat > .env << EOF
POSTGRES_DB=lazuros
POSTGRES_USER=postgres
POSTGRES_PASSWORD=minhaSenhaSegura123
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/lazuros
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=minhaSenhaSegura123
EOF

# Docker Compose vai usar automaticamente
docker-compose up -d
```

### Healthchecks

Ambos os containers têm healthchecks configurados:

**PostgreSQL**:
```bash
pg_isready -U postgres -d lazuros
```

**Aplicação**:
```bash
curl -f http://localhost:8080/actuator/health
```

Você pode verificar o status:
```bash
docker-compose ps
```

### Logs e Debugging

```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Logs apenas da aplicação
docker-compose logs -f app

# Logs apenas do PostgreSQL
docker-compose logs -f postgres

# Últimas 100 linhas
docker-compose logs --tail=100 app

# Executar comandos dentro do container
docker-compose exec app sh
docker-compose exec postgres psql -U postgres -d lazuros
```

### Comandos Úteis

```bash
# Rebuild apenas da aplicação
docker-compose up -d --build app

# Reiniciar apenas a aplicação
docker-compose restart app

# Ver recursos utilizados
docker stats

# Inspecionar network
docker network inspect lazuros-backend_lazuros-network

# Inspecionar volume
docker volume inspect lazuros-backend_postgres_data

# Backup do banco
docker-compose exec postgres pg_dump -U postgres lazuros > backup.sql

# Restore do banco
docker-compose exec -T postgres psql -U postgres lazuros < backup.sql
```

### Troubleshooting

**Problema: Aplicação não conecta no PostgreSQL**
```bash
# Verificar se o PostgreSQL está healthy
docker-compose ps

# Ver logs do PostgreSQL
docker-compose logs postgres

# Verificar se a aplicação está aguardando o banco
docker-compose logs app | grep -i "waiting"
```

**Problema: Porta 8080 já está em uso**
```bash
# Alterar a porta no docker-compose.yml
ports:
  - "8081:8080"  # Usa 8081 no host
```

**Problema: Banco de dados com dados antigos**
```bash
# Remover volumes e recriar
docker-compose down -v
docker-compose up -d --build
```

**Problema: Build falha por falta de memória**
```bash
# Aumentar memória do Docker (Docker Desktop)
# Settings > Resources > Memory > Aumentar para 4GB+

# Ou usar build local e depois copiar JAR
./mvnw clean package -DskipTests
docker-compose up -d
```

---

## Contato e Contribuição

**Autor**: Guilherme Zuriel  
**Email**: guilhermezuriel@example.com  
**Projeto**: Teste Técnico Lazuros

---
