# Sistema de Hospedagem

Raquel Cristina Pereira dos Santos
Projeto completo com frontend HTML/CSS/JS, backend Java Spring Boot e banco MySQL.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- HTML, CSS e JavaScript

## Funcionalidades implementadas

- Cadastro de clientes
- Cadastro de residências
- Cadastro de quartos individual, duplo e família
- Cálculo automático de diárias
- Regras de berço
- Validação de capacidade de hóspedes
- Controle de disponibilidade por período
- Reserva/aluguel
- Cancelamento de aluguel
- Histórico por cliente
- Pagamento com PIX, cartão de crédito, cartão de débito e dinheiro
- Padrão Strategy para tarifas
- Padrão Strategy para pagamentos
- Singleton via componente Spring `GerenciadorTarifas`
- Exceções personalizadas

## Como rodar o banco

No MySQL, crie o banco:

```sql
CREATE DATABASE IF NOT EXISTS sistema_hospedagem;
```

Ou execute o arquivo:

```bash
mysql -u root -p < schema_mysql.sql
```

O arquivo `backend/src/main/resources/application.properties` está configurado para:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

Troque a senha se o seu MySQL usar outra.

## Como rodar o backend

Entre na pasta do backend:

Rode o arquivo SistemaHospedagemApplication.java em "RUN"

A API ficará em:

```text
http://localhost:8080
```

## Como abrir o frontend

Abra o arquivo:

```text
frontend/index.html
```

Você pode abrir diretamente no navegador.

## Endpoints principais

### Clientes

```http
GET /clientes
POST /clientes
GET /clientes/{id}
DELETE /clientes/{id}
```

### Residências

```http
GET /residencias
POST /residencias
GET /residencias/{id}
DELETE /residencias/{id}
```

### Quartos

```http
GET /quartos
POST /quartos/individual?residenciaId=1
POST /quartos/duplo?residenciaId=1
POST /quartos/familia?residenciaId=1
DELETE /quartos/{id}
```

### Aluguéis

```http
GET /alugueis
POST /alugueis?clienteId=1&residenciaId=1&quartoId=1&tarifa=PADRAO
GET /alugueis/cliente/{clienteId}
PATCH /alugueis/{id}/cancelar
```

### Pagamentos

```http
GET /pagamentos
POST /pagamentos/aluguel/{aluguelId}?formaPagamento=PIX
```

## Tarifas disponíveis

- PADRAO
- ALTA_TEMPORADA
- BAIXA_TEMPORADA
- CLIENTE_FREQUENTE

## Formas de pagamento disponíveis

- PIX
- CARTAO_CREDITO
- CARTAO_DEBITO
- DINHEIRO

## Testes unitarios

Os testes JUnit foram incluidos em `backend/src/test/java/com/hospedagem`.

Eles cobrem:

- calculo de diarias;
- validacao de datas invalidas;
- calculo de diaria por tipo de quarto;
- limite de hospedes;
- regra de berco em quarto individual;
- disponibilidade do quarto;
- cancelamento de aluguel;
- historico por cliente;
- filtro por tipo de quarto;
- pagamento por PIX;
- estrategias de tarifa.

Para rodar os testes, entre na pasta `backend` e execute:

```bash
mvn test
```

Para gerar relatorio HTML do Surefire:

```bash
mvn surefire-report:report
```

O relatorio normalmente fica em:

```text
target/site/surefire-report.html
```
