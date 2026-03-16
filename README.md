# Lazaros Teste Técnico

Sistema completo de gerenciamento de Usuários e Perfis com backend em Spring Boot e frontend em Angular 8.

## Estrutura do Projeto

```
lazures-teste-tecnico/
├── lazuros-backend/        # API REST em Spring Boot
└── lazuros-frontend/       # Aplicação Angular 8
```

## Tecnologias

### Backend
- **Spring Boot** 4.0.3
- **Spring Modulith** 1.3.4
- **PostgreSQL** (produção)
- **H2 Database** (testes)
- **Java** 17

### Frontend
- **Angular** 8.x
- **Angular Material** 8.x
- **TypeScript** 3.5.3
- **RxJS** 6.4.0

## Pré-requisitos

### Para o Backend
- Java 17 ou superior
- Maven 3.8+
- PostgreSQL 12+ (ou Docker)

### Para o Frontend
- **Node.js 16.x ou inferior** (Angular 8 não é compatível com Node 17+)
  - Recomendado: Node.js 16.x
  - Se tiver Node.js 17+, veja as instruções de workaround no README do frontend
- npm ou yarn

## Instalação e Execução

### 1. Backend (Spring Boot)

```bash
# Navegar para o diretório do backend
cd lazuros-backend

# Criar banco de dados PostgreSQL (se não existir)
createdb lazuros

# Executar a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

**Endpoints principais:**
- `GET/POST /api/profiles` - Gerenciar perfis
- `GET/POST /api/users` - Gerenciar usuários

Veja mais detalhes no [README do backend](lazuros-backend/README.md)

### 2. Frontend (Angular)

```bash
# Navegar para o diretório do frontend
cd lazuros-frontend

# Instalar dependências
npm install --legacy-peer-deps

# Executar a aplicação
npm start

# Se estiver usando Node.js 17+
npm run start:legacy
```

A aplicação estará disponível em `http://localhost:4200`

Veja mais detalhes no [README do frontend](lazuros-frontend/README.md)

## Autor

Guilherme Zuriel

## Licença

Este projeto faz parte de um teste técnico para a Lazaros.
