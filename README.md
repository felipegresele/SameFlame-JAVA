
# 🔥 SafeFlame - Solução Inteligente de Monitoramento contra Incêndios

SafeFlame é uma aplicação completa desenvolvida com o objetivo de mitigar os impactos de eventos extremos relacionados a incêndios. A solução conta com três APIs principais integradas, autenticação segura com JWT e uma estrutura organizada que segue as boas práticas de desenvolvimento com **Spring Boot**.

---

## 👥 Integrantes

| Nome                       | RM       |
|----------------------------|----------|
| Felipe Horta Gresele       | RM556955 |
| Arthur Cardoso Carinhanha  | RM550615 |
| João Henrique Dias         | RM556221 |

---

## 📌 Descrição da Arquitetura da Solução

A aplicação é composta por três APIs principais: **Usuário**, **Endereço** e **Denúncia**.

### 1. 🧑 API de Usuário

Responsável por:

- Cadastro de novos usuários no sistema (`POST /usuarios`)
- Listagem de todos os usuários (`GET /usuarios`)
- Atualização dos dados de um usuário (`PUT /usuarios/{id}`)
- Exclusão de um usuário (`DELETE /usuarios/{id}`)
- Busca de usuário por ID ou e-mail (`GET /usuarios/{id}`)

O usuário é a entidade base da aplicação e possui relacionamento com a API de Endereço e Denúncia.

---

### 2. 📍 API de Endereço

Responsável por:

- Cadastro de endereços vinculados a um usuário (`POST /enderecos`)
- Listagem, edição e remoção de endereços

Quando o usuário se cadastra, ele pode também cadastrar um **endereço**, que é salvo com um **ID próprio**, mas possui **relacionamento com o usuário e com a denúncia**.

---

### 3. 🚨 API de Denúncia

Responsável por:

- Cadastro de denúncias vinculadas ao usuário logado (`POST /denuncias`)
- Listagem, edição e exclusão de denúncias

A denúncia possui campos como:

- Nome do alerta
- Descrição
- (Outros campos adicionais conforme necessidade)

Essas denúncias são registradas tanto no **back-end** quanto no **front-end**, sendo acessíveis apenas para usuários autenticados.

---

## 🔐 Autenticação

A aplicação utiliza **JWT (Bearer Token)** para autenticação e autorização.

### Fluxo:

1. O usuário se cadastra com e-mail e senha via `POST /usuarios`
2. Depois, realiza login via `POST /auth/login`
3. Ao logar, recebe um **token JWT**
4. Para acessar rotas protegidas (ex: denúncias), o token deve ser enviado no **header**:
   ```
   Authorization: Bearer <seu_token_jwt>
   ```

### Testes no Postman:

- Basta colar o token no campo "Authorization"
- Depois, é possível acessar normalmente os endpoints protegidos

### Integração com o Front-end:

- O front-end consome a API
- Após o login, o token JWT é armazenado e utilizado automaticamente nas requisições seguintes
- Com isso, o usuário logado pode acessar o painel, cadastrar denúncias, editar dados, etc.

---

## 📁 Endpoints Principais

> Exemplos gerais (ajustar conforme a implementação):

#### Usuário

```
POST    /usuarios
GET     /usuarios
GET     /usuarios/{id}
PUT     /usuarios/{id}
DELETE  /usuarios/{id}
```

#### Endereço

```
POST    /enderecos
GET     /enderecos
PUT     /enderecos/{id}
DELETE  /enderecos/{id}
```

#### Denúncia

```
POST    /denuncias
GET     /denuncias
PUT     /denuncias/{id}
DELETE  /denuncias/{id}
```

#### Login

```
POST    /login
```

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot (`spring.application.name=safeflame`)
- Spring Data JPA
- Spring Security + JWT
- Bean Validation
- Swagger (OpenAPI)
- Banco de Dados Oracle
- Maven
- Deploy em Nuvem (ex: Render ou Railway)
- GitHub

---

## ⚙️ Configuração do `application.properties`

```properties
spring.application.name=safeflame

# Oracle Database Configuration
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.username=RM556955
spring.datasource.password=150405

# JPA/Hibernate para Oracle
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Server
server.port=8080

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
```

---

## 🌍 Deploy e Acesso

- 🔗 **Repositório Backend (GitHub)**: https://github.com/felipegresele/SameFlame-JAVA
- 🔗 **Link da API em Produção (Deploy)**: [adicione aqui]
- 🔗 **Swagger - Documentação da API**: http://localhost:8080/swagger-ui/index.html
- 🎥 **Vídeo Demonstração da Solução (até 10 min)**: https://www.youtube.com/watch?v=PTNOFwL2eQE

---

## 🧪 Instruções para Teste Local

1. Clone o projeto:
```bash
git clone https://github.com/felipegresele/SameFlame-JAVA.git
```

2. Certifique-se de ter acesso ao Oracle Database fornecido pela FIAP.

3. Rode a aplicação:
```bash
./mvn spring-boot:run
```

4. Acesse a documentação Swagger:
```
http://localhost:8080/swagger-ui/index.html
```

---


1. Clone o projeto da parte de mobile caso queria testar com o emulador também:
```bash
git clone https://github.com/felipegresele/SameFlame-MOBILE.git

IMPORTANTE: Necessário ter o Android Studio instalado para conseguir executar
```


## 📊 Avaliação da Entrega

- ✅ **Cumprimento dos requisitos técnicos e boas práticas** (70 pts)
- ✅ **Viabilidade e Inovação da Solução** (10 pts)
- ✅ **Documentação clara e apresentação funcional** (20 pts)

---

## 💡 Inovação

A SafeFlame é pensada para ser usada em situações reais, envio de alertas em tempo real e comunicação com sistemas públicos de emergência. A estrutura modular da API permite escalabilidade e fácil adaptação para diversas realidades de uso.

---
