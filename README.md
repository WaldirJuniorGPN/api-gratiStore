# gratiStore API

API responsável pelo cálculo de gratificações de atendentes com base em desempenho e ranking.

Este projeto compõe meu portfólio e foi desenvolvido com foco em boas práticas, testes automatizados e facilidade de execução por meio de containers.

## 🚀 Tecnologias principais

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- MySQL
- Testcontainers + MySQL (integração de testes)
- Lombok
- Docker e Docker Compose
- Swagger (springdoc-openapi)
- Apache POI (leitura de planilhas XLSX)

## 📦 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Git](https://git-scm.com/)

## 🛠️ Como rodar o projeto

### 1. Clone os repositórios

```bash
git clone https://github.com/WaldirJuniorGPN/api-gratiStore.git
git clone https://github.com/WaldirJuniorGPN/front-gratiStore
cd api-gratiStore
```

> Os dois repositórios devem estar na mesma pasta para que o build funcione corretamente.

### 2. Suba os containers

Execute o seguinte comando na raiz do projeto `api-gratiStore`:

```bash
docker-compose up --build
```

O Docker Compose irá subir três containers:

- `mysql-ms`: banco de dados MySQL (porta 3307)
- `api-gratiStore`: back-end da aplicação (porta 8080)
- `front-gratiStore`: front-end da aplicação (porta 5500)

### 3. Acesse a aplicação

- Front-end: [http://localhost:5500](http://localhost:5500)
- API Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧪 Testes

Os testes utilizam **Testcontainers** com um banco MySQL efêmero, aproximando o ambiente de desenvolvimento do cenário real. Para executá-los, certifique-se de que o Docker esteja em funcionamento e rode:

```bash
./mvnw test
```

## 🤝 Contribuição

Sinta-se à vontade para abrir issues ou enviar pull requests. Toda ajuda é bem-vinda!

---

Caso tenha dúvidas ou sugestões, estou à disposição. 😄
