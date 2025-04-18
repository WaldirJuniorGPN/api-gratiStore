# gratiStore API

API responsável pelo cálculo de gratificações de atendentes com base em desempenho e ranking.

## 🚀 Tecnologias principais

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- MySQL
- H2 (para testes)
- Lombok
- Docker e Docker Compose
- Swagger (springdoc-openapi)
- Apache POI (leitura de planilhas XLSX)

## 📦 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## 🛠️ Como rodar o projeto

### 1. Clone os repositórios

```bash
git clone https://github.com/WaldirJuniorGPN/api-gratiStore.git
git clone https://github.com/WaldirJuniorGPN/front-gratiStore
cd api-gratiStore
```

**Importante:** os dois repositórios precisam estar na mesma pasta para que o build funcione corretamente.

### 2. Suba os containers

Execute o seguinte comando na raiz do projeto `api-gratiStore`:

```bash
docker-compose up --build
```

O Docker Compose irá subir 3 containers:

- `mysql-ms`: container do MySQL (porta 3307)
- `api-gratiStore`: back-end da aplicação (porta 8080)
- `front-gratiStore`: front-end da aplicação (porta 5500)

> ⚠️ **Importante:** o container `api-gratiStore` depende do `mysql-ms`. Certifique-se de que o banco de dados esteja pronto antes do Spring Boot tentar iniciar. Caso contrário, o back-end poderá falhar na conexão com o banco. O `docker-compose` já cuida da ordem de inicialização com a diretiva `depends_on`, mas em ambientes mais lentos, pode ser necessário aguardar manualmente a inicialização completa do MySQL.

### 3. Acesse a aplicação

- Front-end: [http://localhost:5500](http://localhost:5500)
- API Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧪 Rodando os testes

Os testes utilizam o banco de dados em memória H2.

```bash
./mvnw test
```

---

Se tiver qualquer dúvida ou sugestão, fique à vontade para abrir uma issue ou contribuir! 😄

