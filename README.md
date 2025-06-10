# Sistema de Controle de Estoque

## Autores
- **Alex Pereira Corte** — GU3042014  
- **Otavio Augusto Azevedo Marçal** — GU304226X

## 📌 Objetivo

Desenvolver uma API RESTful para controle de estoque de produtos de uma empresa, com suporte a múltiplos usuários, autenticação JWT e funcionalidades administrativas, como movimentações de estoque e geração de relatórios.

## Público-Alvo

Equipes internas de gestão de estoque, técnicos responsáveis pelo controle de inventário e desenvolvedores que desejam integrar o backend a sistemas web ou mobile.


## Funcionalidades

| Módulo  			| Funcionalidade |
| **Autenticação** 	| Cadastro de usuários, login com JWT, controle de acesso por perfil (USER/ADMIN) |
| **Categorias** 	| CRUD completo de categorias (apenas ADMIN) |
| **Produtos** 		| CRUD de produtos com validação de preço/quantidade, vínculo com categoria |
| **Movimentações** | Registro de entradas/saídas, validações de estoque, histórico por produto |
| **Relatórios** 	| Filtro por data, produtos com baixo estoque, resumo de movimentações |
| **Testes** 		| Testes unitários (serviços), testes funcionais (controladores) |
| **Swagger** 		| Documentação automática via SpringDoc OpenAPI |
| **Segurança** 	| JWT com filtro personalizado e papéis de acesso |


## Como Executar Localmente

### Pré-requisitos

- Java 17+
- Maven 3.8+
- IDE de sua preferência (VSCode, IntelliJ, etc.)

### Rodando o Projeto

```bash

# Clone o repositório
git clone https://github.com/seu-usuario/estoque.git
cd estoque

# Build do projeto
./mvnw clean install

# Rodar a aplicação
./mvnw spring-boot:run


Como Autenticar (JWT)
Acesse POST /auth/register para criar um novo usuário.

Em seguida, faça login via POST /auth/login.

Guarde o token retornado.

Nos endpoints protegidos, envie o token no header:

http
Copiar
Editar
Authorization: Bearer SEU_TOKEN
Documentação da API (Swagger)
Após rodar o projeto, acesse:

bash
Copiar
Editar
http://localhost:8080/swagger-ui.html
Você verá todos os endpoints, parâmetros, respostas e status codes.

Modelo de Dados
User → possui nome, e-mail, senha criptografada, e papel (USER/ADMIN).

Categoria → nome e descrição.

Produto → nome, descrição, quantidade, preço e categoria.

Movimentação → tipo (entrada/saída), produto, quantidade, data e usuário.

Enum TipoMovimentacao → ENTRADA, SAIDA.

Validações aplicadas:

Quantidade e preço não podem ser negativos.

Saída não pode ser maior que estoque atual.

E-mails devem ser únicos.

Autenticação e Autorização
ADMIN: acesso total a categorias, produtos, movimentações e usuários.

USER: acesso a listagens e movimentações de produtos.

Todas as rotas protegidas requerem JWT válido.

Testes Implementados
MovimentacaoServiceTest.java: testa a lógica de movimentações.

ProdutoControllerTest.java: valida endpoints REST.

Cobertura de cenários positivos e negativos.
