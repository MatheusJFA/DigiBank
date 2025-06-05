# 📌 Banco Vitrine Técnica — Issue List

Organizado por épicos e milestones para guiar o desenvolvimento do sistema monolítico com arquitetura limpa, incluindo os módulos de banco digital, corretora de investimentos e programa de pontos.

---

## 🗂️ EPIC 1: Infraestrutura base e fundação do projeto

### 🎯 Milestone: Inicialização e preparação
- [ ] `#1` Criar repositório no GitHub com nome `banco-vitrine`
- [ ] `#2` Gerar projeto base via Spring Initializr
- [ ] `#3` Criar estrutura de diretórios com base na Clean Architecture
- [ ] `#4` Configurar `pom.xml` com todas as dependências
- [ ] `#5` Criar `README.md` com visão geral, arquitetura e tecnologias
- [ ] `#6` Criar `docker-compose.yml` com PostgreSQL, Redis, Kafka, Prometheus, Grafana
- [ ] `#7` Criar `application.yml` com perfis dev, test e prod
- [ ] `#8` Configurar CI com GitHub Actions (`build`, `test`, `jacoco`, `docker`)

---

## 🗂️ EPIC 2: Banco Digital

### 🎯 Milestone: Abertura de conta e usuários
- [ ] `#9` Criar entidades `Usuario`, `Conta`, `TipoConta` e afins no módulo `domain` 
- [ ] `#10` Criar interface `ContaRepository` no `domain`
- [ ] `#11` Criar caso de uso `AbrirContaUseCase` no `application`
- [ ] `#12` Implementar `ContaRepositoryImpl` usando Spring Data JPA
- [ ] `#13` Criar controller REST `/contas` com endpoint POST
- [ ] `#14` Escrever testes unitários para o caso de uso
- [ ] `#15` Escrever testes de integração com Testcontainers

### 🎯 Milestone: Transações bancárias
- [ ] `#16` Criar entidades `Transacao`, `TipoTransacao` e afins
- [ ] `#17` Implementar `RealizarTransacaoUseCase` com validações de negócio
- [ ] `#18` Criar REST `/transacoes`
- [ ] `#19` Publicar evento Kafka `TransacaoEfetuadaEvent`
- [ ] `#20` Criar testes (unitários e integração)

---

## 🗂️ EPIC 3: Corretora de Investimentos

### 🎯 Milestone: Simulação de ativos e ordens
- [ ] `#21` Criar entidades `Ativo`, `Ordem`, `TipoOrdem`, `Carteira` e afins
- [ ] `#22` Implementar `EmitirOrdemDeCompraUseCase` e `EmitirOrdemDeVendaUseCase`
- [ ] `#23` Criar REST `/ordens` com endpoints para emissão e consulta
- [ ] `#24` Produzir/Consumir eventos de ordens com Kafka
- [ ] `#25` Criar executor fictício de ordens e mecanismo de liquidação
- [ ] `#26` Atualizar saldo e carteira do cliente após execução
- [ ] `#27` Criar testes unitários e de integração

---

## 🗂️ EPIC 4: Programa de Pontos

### 🎯 Milestone: Geração e uso de pontos
- [ ] `#28` Consumir evento `TransacaoEfetuadaEvent` para gerar pontos
- [ ] `#29` Criar regras de cálculo de pontos por tipo de transação
- [ ] `#30` Criar entidades `Ponto`, `ProdutoResgatavel`, `Resgate`
- [ ] `#31` Criar REST `/pontos` e `/resgates`
- [ ] `#32` Implementar `ResgatarProdutoUseCase`
- [ ] `#33` Escrever testes (regra de pontos e resgates)

---

## 🗂️ EPIC 5: Observabilidade e segurança

### 🎯 Milestone: Monitoramento e autenticação
- [ ] `#34` Implementar autenticação com JWT (clientes, admins, parceiros)
- [ ] `#35` Criar login/token endpoint
- [ ] `#36` Expor métricas com Actuator + Prometheus
- [ ] `#37` Criar dashboards no Grafana
- [ ] `#38` Implementar logging estruturado com MDC e traceId

---

## 🗂️ EPIC 6: Finalização e documentação

### 🎯 Milestone: Final polish
- [ ] `#39` Gerar documentação Swagger/OpenAPI
- [ ] `#40` Criar documentação visual com o Modelo C4 completo
- [ ] `#41` Gravar vídeo explicando arquitetura, decisões e trade-offs
- [ ] `#42` Criar `CONTRIBUTING.md` com padrões de contribuição, testes e CI
- [ ] `#43` Criar README completo com prints, diagramas e orientações de uso

---

