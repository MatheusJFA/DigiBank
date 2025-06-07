# Arquitetura da Aplicação Digibank

## Visão Geral

A Digibank é uma aplicação de vitrine para desenvolvedores em Java com Spring Boot que simula operações bancárias, programa de pontos e investimentos. Esta aplicação servirá como um exemplo completo de como desenvolver um sistema bancário digital utilizando as melhores práticas e recursos do Spring Boot.

## Objetivos

1. Demonstrar o uso do Spring Boot em um cenário real de aplicação bancária
2. Implementar operações bancárias comuns (transferências, depósitos, saques)
3. Criar um sistema de programa de pontos integrado às transações bancárias
4. Simular operações de investimentos com diferentes tipos de produtos financeiros
5. Aplicar boas práticas de desenvolvimento, segurança e arquitetura

## Arquitetura Técnica

A aplicação seguirá uma arquitetura em camadas, com separação clara de responsabilidades:

### Camadas da Aplicação

1. **Camada de Apresentação (API REST)**
    - Controllers para exposição dos endpoints
    - DTOs (Data Transfer Objects) para transferência de dados
    - Validação de entrada
    - Tratamento de exceções

2. **Camada de Serviço**
    - Lógica de negócio
    - Orquestração de operações
    - Validações de regras de negócio
    - Transações

3. **Camada de Persistência**
    - Repositórios Spring Data JPA
    - Entidades JPA
    - Mapeamento objeto-relacional

4. **Camada de Configuração**
    - Configuração de segurança
    - Configuração de banco de dados
    - Configuração de propriedades da aplicação

### Tecnologias Utilizadas

- **Spring Boot**: Framework base da aplicação
- **Spring Data JPA**: Para persistência de dados
- **Spring Security**: Para autenticação e autorização
- **H2 Database**: Banco de dados em memória para desenvolvimento
- **Lombok**: Para redução de código boilerplate
- **Swagger/OpenAPI**: Para documentação da API
- **JUnit e Mockito**: Para testes

## Módulos da Aplicação

### 1. Módulo de Autenticação e Usuários

#### Entidades
- **Usuario**: Armazena informações do usuário
- **Perfil**: Define os perfis de acesso (CLIENTE, GERENTE, ADMIN)

#### Funcionalidades
- Registro de novos usuários
- Autenticação via JWT
- Gerenciamento de perfis e permissões

### 2. Módulo de Contas Bancárias

#### Entidades
- **Conta**: Informações da conta bancária
- **TipoConta**: Enum para tipos de conta (CORRENTE, POUPANCA, SALARIO)
- **Transacao**: Registro de transações na conta

#### Funcionalidades
- Criação de contas
- Consulta de saldo e extrato
- Depósitos e saques
- Transferências entre contas

### 3. Módulo de Programa de Pontos

#### Entidades
- **ProgramaPontos**: Informações do programa de pontos do cliente
- **Pontuacao**: Registro de pontos ganhos ou resgatados
- **CategoriaTransacao**: Classificação das transações para cálculo de pontos

#### Funcionalidades
- Acúmulo de pontos baseado em transações
- Consulta de saldo de pontos
- Resgate de pontos
- Catálogo de benefícios

### 4. Módulo de Investimentos

#### Entidades
- **CarteiraInvestimentos**: Carteira de investimentos do cliente
- **Investimento**: Registro de investimentos realizados
- **ProdutoInvestimento**: Produtos disponíveis para investimento
- **TipoInvestimento**: Enum para tipos de investimento (RENDA_FIXA, RENDA_VARIAVEL)

#### Funcionalidades
- Consulta de produtos de investimento disponíveis
- Simulação de rendimentos
- Aplicação em investimentos
- Resgate de investimentos
- Consulta de carteira de investimentos

## Diagrama de Entidades

```
+----------------+       +----------------+       +----------------+
|    Usuario     |       |     Conta      |       | ProgramaPontos |
+----------------+       +----------------+       +----------------+
| id             |<----->| id             |<----->| id             |
| nome           |       | numero         |       | saldoPontos    |
| email          |       | saldo          |       | dataAdesao     |
| senha          |       | tipoConta      |       | usuarioId      |
| perfis         |       | usuarioId      |       +----------------+
+----------------+       +----------------+              ^
        ^                       ^                        |
        |                       |                        |
        |                       |                        |
        v                       v                        v
+----------------+      +----------------+       +----------------+
|     Perfil     |      |   Transacao    |       |   Pontuacao    |
+----------------+      +----------------+       +----------------+
| id             |      | id             |       | id             |
| nome           |      | valor          |       | valor          |
+----------------+      | tipo           |       | descricao      |
                        | data           |       | data           |
                        | contaId        |       | programaPontosId|
                        | categoria      |       +----------------+
                        +----------------+
                                ^
                                |
                                |
                                v
                        +--------------------+
                        | CategoriaTransacao |
                        +--------------------+
                        | id                 |
                        | nome               |
                        | multiplicadorPontos|
                        +--------------------+

+---------------------+         +----------------+       +-------------------+
|CarteiraInvestimentos|         |  Investimento  |       |ProdutoInvestimento|
+---------------------+         +----------------+       +-------------------+
| id                   | <----> | id             |       | id               |
| saldoTotal           |        | valor          |<----->| nome             |
| usuarioId            |        | dataAplicacao  |       | descricao        |
+----------------------+        | dataResgate    |       | rentabilidade    |
                                | carteiraId     |       | tipoInvestimento |
                                | produtoId      |       | prazoMinimo      |
                                +----------------+       | risco            |
                                                         | taxaAdministracao|
                                                         +------------------+
```

## Endpoints da API

### Autenticação
- `POST /api/auth/register` - Registro de novo usuário
- `POST /api/auth/login` - Login de usuário

### Contas
- `GET /api/contas` - Listar contas do usuário
- `GET /api/contas/{id}` - Detalhes da conta
- `POST /api/contas` - Criar nova conta
- `GET /api/contas/{id}/extrato` - Extrato da conta
- `POST /api/contas/{id}/deposito` - Realizar depósito
- `POST /api/contas/{id}/saque` - Realizar saque
- `POST /api/contas/transferencia` - Realizar transferência

### Programa de Pontos
- `GET /api/pontos` - Consultar saldo de pontos
- `GET /api/pontos/extrato` - Extrato de pontos
- `GET /api/pontos/catalogo` - Catálogo de benefícios
- `POST /api/pontos/resgate` - Resgatar pontos

### Investimentos
- `GET /api/investimentos/produtos` - Listar produtos de investimento
- `GET /api/investimentos/carteira` - Consultar carteira de investimentos
- `POST /api/investimentos/aplicar` - Aplicar em investimento
- `POST /api/investimentos/resgatar` - Resgatar investimento
- `GET /api/investimentos/simulacao` - Simular rendimento

## Segurança

A aplicação implementará segurança em vários níveis:

1. **Autenticação**: JWT (JSON Web Token) para autenticação stateless
2. **Autorização**: Controle de acesso baseado em perfis
3. **Validação de Dados**: Validação de entrada em todas as requisições
4. **Proteção contra Ataques Comuns**: CSRF, XSS, SQL Injection
5. **Auditoria**: Log de todas as operações sensíveis

## Considerações de Implementação

1. **Transações**: Uso de `@Transactional` para garantir atomicidade das operações
2. **Validação**: Uso de Bean Validation para validação de entrada
3. **Tratamento de Exceções**: Implementação de `@ControllerAdvice` para tratamento centralizado
4. **Documentação**: Uso de Swagger/OpenAPI para documentação da API
5. **Testes**: Cobertura de testes unitários e de integração

## Próximos Passos

1. Configuração inicial do projeto Spring Boot
2. Implementação das entidades e repositórios
3. Desenvolvimento dos serviços de negócio
4. Criação dos endpoints REST
5. Implementação da segurança
6. Testes e documentação

