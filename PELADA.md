# GoalieFinder — Tasks do Backend (Java + MySQL)

> **Observação importante:** o documento original previa Supabase (Auth + PostgreSQL). Como o backend será em **Java + MySQL**, a autenticação, autorização e upload de arquivos passam a ser responsabilidade do próprio backend (não existe mais "Supabase Auth"). Stack sugerida abaixo.

## Stack sugerida
- **Java 17** + **Spring Boot 3**
- **Spring Data JPA** + Hibernate
- **MySQL 8**
- **Spring Security** + **JWT** (access + refresh token)
- **OAuth2 Client** (login com Google)
- **Flyway** para migrations
- **Maven** ou **Gradle**
- **springdoc-openapi** (Swagger) para documentação
- **JUnit 5 + Mockito + Testcontainers** para testes
- **Docker / Docker Compose** (app + MySQL)
- Storage de imagens: S3 (ou compatível, ex. MinIO) — fotos de perfil

---

## Epic 1 — Setup do Projeto
- [ ] Criar projeto Spring Boot (Maven/Gradle) com dependências base
- [ ] Configurar conexão com MySQL (`application.yml` por profile: dev/prod)
- [ ] Configurar Flyway (estrutura `db/migration`)
- [ ] Definir estrutura de pacotes: `controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `security`, `exception`, `config`
- [ ] Configurar Docker Compose (app + MySQL + adminer/phpmyadmin)
- [ ] Configurar CORS para o domínio do frontend (Next.js)
- [ ] Configurar Swagger/OpenAPI
- [ ] Configurar logging (Logback) e níveis por ambiente
- [ ] Configurar tratamento global de erros (`@ControllerAdvice`)

## Epic 2 — Modelagem do Banco (MySQL)
- [ ] Tabela `users` (id, email, password_hash, provider [LOCAL/GOOGLE], role, status, created_at)
- [ ] Tabela `profiles` (dados comuns: nome, telefone, cidade, estado, foto, user_type)
- [ ] Tabela `goalkeeper_profiles` (idade, altura, pé preferido, experiência, bio, pode_viajar, distância_max_km, preço_esperado)
- [ ] Tabela `availability` (user_id, dia_da_semana) — disponibilidade do goleiro
- [ ] Tabela `goalkeeper_match_preferences` (user_id, tipo_partida) — 5v5/7v7/11v11
- [ ] Tabela `matches` (organizer_id, título, data, hora, campo, endereço, cidade, preço, tipo_partida, tipo_campo, nível, descrição, status)
- [ ] Tabela `applications` (match_id, goalkeeper_id, status [PENDING/ACCEPTED/REJECTED], created_at)
- [ ] Tabela `notifications` (user_id, tipo, mensagem, lida, created_at)
- [ ] Tabela `reviews` (match_id, author_id, target_id, rating, comentário, created_at)
- [ ] Tabela `admin_logs` (ação, alvo, admin_id, created_at) — auditoria
- [ ] Definir relacionamentos/FKs e regras de cascade
- [ ] Criar migrations Flyway versionadas (V1, V2, V3…)
- [ ] Criar índices em colunas de busca/filtro (city, date, match_type, status, skill_level)

## Epic 3 — Autenticação e Autorização
- [ ] Entidade `User` com roles: `ORGANIZER`, `GOALKEEPER`, `ADMIN`
- [ ] Endpoint de cadastro com email + senha
- [ ] Hash de senha com BCrypt
- [ ] Endpoint de login → retorna JWT (access + refresh token)
- [ ] Endpoint de refresh token
- [ ] Endpoint de logout (revogar/blacklist refresh token)
- [ ] Integração com login Google (Spring Security OAuth2 Client)
- [ ] Filtro JWT para validar token nas requisições
- [ ] Configuração do Spring Security (rotas públicas vs. protegidas por role)
- [ ] Endpoint "completar cadastro" pós-signup (nome completo, telefone, cidade, estado, tipo de usuário)
- [ ] Validações de entrada (email, senha forte, telefone, etc.)
- [ ] Recuperação de senha (esqueci minha senha) — opcional, mas recomendado

## Epic 4 — Perfis de Usuário
**Organizador**
- [ ] CRUD do perfil básico do organizador

**Goleiro**
- [ ] CRUD do perfil do goleiro (nome, foto, idade, cidade, altura, pé preferido, experiência, bio)
- [ ] Upload de foto de perfil (S3/MinIO + validação de tipo/tamanho de arquivo)
- [ ] CRUD de disponibilidade semanal
- [ ] CRUD de preferências de tipo de partida (5v5/7v7/11v11)
- [ ] Toggle "pode viajar" + distância máxima (km)
- [ ] Campo opcional de expectativa de preço
- [ ] Endpoint de perfil público do goleiro (rating médio, nº de partidas, reviews)

## Epic 5 — Partidas (Matches)
- [ ] Endpoint criar partida (somente organizador)
- [ ] Validações: título, data, hora, campo, endereço, cidade, preço, tipo de partida, tipo de campo, nível, descrição (máx. 500 caracteres)
- [ ] Endpoint listar partidas públicas (feed) com paginação para infinite scroll
- [ ] Endpoint detalhe de uma partida
- [ ] Endpoint editar partida (somente o organizador dono)
- [ ] Endpoint deletar partida
- [ ] Endpoint "fechar candidaturas"
- [ ] Endpoint listar partidas do organizador logado (dashboard)
- [ ] Enum de status da partida: `OPEN`, `CLOSED`, `CONFIRMED`, `CANCELLED`, `COMPLETED`
- [ ] Endpoint cancelar partida → dispara notificações para candidatos

## Epic 6 — Candidaturas (Applications)
- [ ] Endpoint goleiro se candidatar a uma partida
- [ ] Regra: impedir candidatura duplicada na mesma partida
- [ ] Endpoint organizador listar candidatos de uma partida
- [ ] Endpoint aceitar candidato → transação: muda partida para `CONFIRMED` + fecha/rejeita automaticamente as demais candidaturas
- [ ] Endpoint rejeitar candidato
- [ ] Endpoint histórico de candidaturas do goleiro logado

## Epic 7 — Notificações
- [ ] Entidade/serviço de notificação (tipo, mensagem, lida/não lida, destinatário)
- [ ] Disparo: nova candidatura recebida (organizador)
- [ ] Disparo: candidatura aceita (goleiro)
- [ ] Disparo: candidatura rejeitada (goleiro)
- [ ] Disparo: partida cancelada (candidatos/goleiro confirmado)
- [ ] Endpoint listar notificações do usuário logado
- [ ] Endpoint marcar notificação como lida
- [ ] (Opcional) WebSocket/SSE para notificações em tempo real
- [ ] (Opcional) Envio de e-mail/push para eventos importantes

## Epic 8 — Busca e Filtros
- [ ] Endpoint de busca com filtros combináveis: cidade, data, tipo de partida, faixa de preço, tipo de campo, nível
- [ ] Barra de busca (texto livre em título/cidade/campo)
- [ ] Paginação e ordenação (mais recentes, mais próximas, menor preço, etc.)
- [ ] Otimizar queries com índices compostos

## Epic 9 — Avaliações (Reviews)
- [ ] Endpoint criar avaliação pós-partida (1–5 estrelas + comentário opcional)
- [ ] Regra: avaliação só liberada após status `COMPLETED`
- [ ] Cálculo de rating médio no perfil (trigger/serviço)
- [ ] Contagem de partidas concluídas por usuário
- [ ] Endpoint listar reviews recebidas por um usuário

## Epic 10 — Painel Admin
- [ ] Endpoint listar todos os usuários (paginado, com filtros)
- [ ] Endpoint listar todas as partidas
- [ ] Endpoint remover conteúdo inadequado (partida/review)
- [ ] Endpoint suspender usuário
- [ ] Endpoint deletar usuário
- [ ] Proteção de rotas por role `ADMIN`
- [ ] Registro de log de ações administrativas (auditoria)

## Epic 11 — Infraestrutura e Qualidade
- [ ] DTOs de entrada/saída separados das entidades + Bean Validation
- [ ] Mapper entre entidade e DTO (MapStruct, opcional)
- [ ] Testes unitários dos services
- [ ] Testes de integração dos controllers (Testcontainers + MySQL)
- [ ] Documentação completa via Swagger/OpenAPI
- [ ] Pipeline de CI/CD (build, testes, lint)
- [ ] Gestão de variáveis de ambiente/secrets
- [ ] Health checks (Spring Actuator)
- [ ] Rate limiting em endpoints sensíveis (login, cadastro) — opcional

## Epic 12 — Integração com o Frontend (Next.js)
- [ ] Publicar contrato de API (OpenAPI/Swagger JSON) para o time de frontend
- [ ] Padronizar formato de resposta de erro (código, mensagem, detalhes)
- [ ] Endpoint de upload de avatar/imagem
- [ ] Garantir CORS liberado para o domínio do frontend em produção e dev

---

### Sugestão de ordem de execução (sprints)
1. Epic 1 + Epic 2 (setup + banco)
2. Epic 3 (auth) + Epic 4 (perfis)
3. Epic 5 (partidas) + Epic 6 (candidaturas)
4. Epic 7 (notificações) + Epic 8 (busca/filtros)
5. Epic 9 (reviews) + Epic 10 (admin)
6. Epic 11 + Epic 12 (qualidade e integração final)
