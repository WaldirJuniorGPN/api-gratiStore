# API - GratiStore

Este é o repositório do projeto **api-gratiStore**, uma API responsável por calcular e gerenciar as gratificações e bônus dos funcionários de uma papelaria. O projeto é desenvolvido em Java, utilizando o Spring Boot e conta com integração a um banco de dados MySQL, configurado para ser executado via Docker.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.5**
- **Maven**
- **MySQL** (via Docker Compose)
- **JPA** (Java Persistence API)
- **H2 Database** (para testes em ambiente local)
- **Lombok** (para simplificação de código)
- **Docker e Docker Compose** (para gerenciamento de containers)

## Funcionalidades

A API `api-gratiStore` foi projetada para atender às seguintes funcionalidades:

- Cálculo de gratificações e bônus de funcionários
- Gestão dos dados de gratificação de acordo com as metas e o desempenho
- Persistência dos dados em um banco de dados MySQL

## Estrutura do Projeto

O projeto utiliza o Maven para gerenciamento de dependências e construção, com as principais dependências sendo:

- `spring-boot-starter-data-jpa`: para a integração com o banco de dados e persistência de dados.
- `spring-boot-starter-validation`: para validação de dados de entrada.
- `spring-boot-starter-web`: para expor os endpoints REST da API.
- `mysql-connector-j`: driver para conexão com o banco de dados MySQL.
- `h2`: banco de dados em memória utilizado para testes.
- `lombok`: biblioteca que simplifica o código, reduzindo o uso de getters, setters e outros métodos boilerplate.
  
## Configuração

1. **Pré-requisitos**:
   - Docker e Docker Compose instalados
   - Java 17 instalado

2. **Clonando o repositório**:
   ```bash
   git clone https://github.com/WaldirJuniorGPN/api-gratiStore.git
   cd api-gratiStore
   ```

3. **Configuração do Banco de Dados**:
   O projeto utiliza um banco de dados MySQL. O arquivo `docker-compose.yml` define um container para o MySQL. Certifique-se de que as variáveis de ambiente do MySQL estejam configuradas corretamente.

4. **Executando o Docker Compose**:
   Para subir o container do MySQL, execute:
   ```bash
   docker-compose up -d
   ```

5. **Executando o Projeto**:
   Com o banco de dados MySQL em execução, você pode rodar o projeto com o seguinte comando:
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Testando a API**:
   A API estará disponível na URL `http://localhost:8080`. Os endpoints para cálculo e visualização das gratificações estarão documentados e acessíveis nesta porta.

## Desenvolvimento e Testes

Para rodar os testes, execute:
```bash
./mvnw test
```

## Contribuição

Contribuições são bem-vindas! Para contribuir, faça um fork do projeto, crie uma nova branch, implemente as mudanças e abra um pull request.

