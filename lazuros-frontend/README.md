# Lazaros Frontend

Aplicação Angular 8 para gerenciamento de Usuários e Perfis, consumindo a API do lazuros-backend.

## Tecnologias Utilizadas

- **Angular**: 8.x
- **Angular Material**: 8.x - Componentes UI
- **TypeScript**: ~3.5.3
- **RxJS**: ~6.4.0

## Funcionalidades

### Gerenciamento de Perfis
- Listagem de perfis com tabela Material
- Criação de novos perfis via dialog
- Edição de perfis existentes
- Exclusão de perfis
- Validação de formulários (mínimo 5 caracteres)

### Gerenciamento de Usuários
- Listagem de usuários com seus perfis
- Criação de novos usuários com seleção múltipla de perfis
- Edição de usuários existentes
- Exclusão de usuários
- Validação de formulários (nome mínimo 10 caracteres)
- Exibição de perfis em chips

## Estrutura do Projeto

```
src/
├── app/
│   ├── models/                    # Interfaces e modelos
│   │   ├── profile.model.ts
│   │   └── user.model.ts
│   ├── services/                  # Serviços HTTP
│   │   ├── profile.service.ts
│   │   └── user.service.ts
│   ├── modules/
│   │   ├── profiles/              # Módulo de Perfis
│   │   │   ├── components/
│   │   │   │   ├── profile-list/
│   │   │   │   └── profile-form/
│   │   │   └── profiles.module.ts
│   │   └── users/                 # Módulo de Usuários
│   │       ├── components/
│   │       │   ├── user-list/
│   │       │   └── user-form/
│   │       └── users.module.ts
│   └── shared/
│       └── material/              # Módulo compartilhado do Material
│           └── material.module.ts
├── environments/
│   ├── environment.ts             # Configuração de desenvolvimento
│   └── environment.prod.ts        # Configuração de produção
└── styles.scss                    # Estilos globais
```

## Pré-requisitos

- **Node.js**: versão 10.x, 12.x, 14.x, ou 16.x (Angular 8 não é compatível com Node 17+)
  - **Importante**: Se você tiver Node.js 17 ou superior, use uma das opções abaixo:
    - Instale o [nvm](https://github.com/nvm-sh/nvm) e use `nvm install 16 && nvm use 16`
    - Ou execute com `NODE_OPTIONS=--openssl-legacy-provider` (workaround temporário)
- npm ou yarn
- Angular CLI 8.x

## Configuração

### 1. Instalar Dependências

```bash
npm install --legacy-peer-deps
```

### 2. Configurar URL da API

O arquivo `src/environments/environment.ts` já está configurado para apontar para:

```typescript
apiUrl: 'http://localhost:8080/api'
```

Se a API estiver em outra porta ou host, ajuste este valor.

### 3. Executar a Aplicação

```bash
npm start
# ou
ng serve

# Se estiver usando Node.js 17+, use:
NODE_OPTIONS=--openssl-legacy-provider npm start
# ou
NODE_OPTIONS=--openssl-legacy-provider ng serve
```

A aplicação estará disponível em `http://localhost:4200`

## Build para Produção

```bash
npm run build
# ou
ng build --prod

# Se estiver usando Node.js 17+, use:
NODE_OPTIONS=--openssl-legacy-provider npm run build
# ou
NODE_OPTIONS=--openssl-legacy-provider ng build --prod
```

Os arquivos compilados estarão em `dist/lazuros-frontend/`

## Rotas da Aplicação

- `/` - Redireciona para `/users`
- `/users` - Listagem e gerenciamento de usuários
- `/profiles` - Listagem e gerenciamento de perfis

## Componentes Angular Material Utilizados

- **MatToolbar** - Barra de navegação
- **MatButton** - Botões
- **MatIcon** - Ícones
- **MatCard** - Cards de conteúdo
- **MatTable** - Tabelas de dados
- **MatFormField** - Campos de formulário
- **MatInput** - Campos de texto
- **MatSelect** - Seleção múltipla
- **MatDialog** - Diálogos modais
- **MatSnackBar** - Notificações
- **MatProgressSpinner** - Indicadores de carregamento
- **MatChips** - Tags/chips para perfis

## Validações Implementadas

### Perfis
- Descrição: obrigatória, mínimo 5 caracteres

### Usuários
- Nome: obrigatório, mínimo 10 caracteres
- Perfis: obrigatório, mínimo 1 perfil selecionado

## Integração com a API

Os serviços consomem os seguintes endpoints:

### ProfileService
- `GET /api/profiles` - Lista todos os perfis
- `GET /api/profiles/:id` - Busca perfil por ID
- `POST /api/profiles` - Cria novo perfil
- `PUT /api/profiles/:id` - Atualiza perfil
- `DELETE /api/profiles/:id` - Exclui perfil

### UserService
- `GET /api/users` - Lista todos os usuários
- `GET /api/users/:id` - Busca usuário por ID
- `POST /api/users` - Cria novo usuário
- `PUT /api/users/:id` - Atualiza usuário
- `DELETE /api/users/:id` - Exclui usuário

## Features Adicionais

- **Lazy Loading** - Módulos carregados sob demanda
- **Reactive Forms** - Formulários reativos com validação
- **Material Design** - Interface moderna e responsiva
- **Feedback Visual** - Snackbars para sucesso/erro
- **Loading States** - Spinners durante carregamento
- **Empty States** - Mensagens quando não há dados
- **Confirmação de Exclusão** - Dialog de confirmação antes de excluir

## Scripts Disponíveis

```bash
# Desenvolvimento
npm start

# Build de produção
npm run build

# Testes
npm test

# Linting
npm run lint
```

## Autor

Guilherme Zuriel

## Licença

Este projeto faz parte de um teste técnico para a Lazuros.
