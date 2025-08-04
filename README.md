---

### 🌐 [🚀 Acesse o Front-end do Projeto](https://alfred-gerenciador.vercel.app/)

---

# 🚀 Gestor de Pedidos - Microsserviços

Este projeto é a evolução do [Gestor de Pedidos Alfred](https://github.com/mateusp-rezende/GestorDePedidosAlfred), um sistema simples com o objetivo de registrar produtos e clientes para gerar pedidos, notas em PDF e relatórios de vendas. O foco sempre foi criar uma ferramenta para auxiliar microempreendedores na contabilidade e análise geral do negócio.

Inspirado pelo bootcamp da DIO e pelo Desafio Técnico proposto, o projeto foi reimaginado e reconstruído com uma arquitetura moderna de microsserviços. Essa nova abordagem, utilizando Spring Boot e Spring Cloud, torna o sistema mais próximo de uma aplicação real e cumpre os requisitos do desafio, demonstrando conceitos de resiliência, escalabilidade e comunicação entre serviços.

**Objetivo Final:** Criar uma ferramenta completa com as funcionalidades de:

* Criação de pedidos
* Emissão de relatórios mensais em CSV
* Interface simples e organizada em React

---

## 📋 Descrição do Desafio

O projeto segue a arquitetura e os requisitos técnicos propostos no Desafio Técnico de Microsserviços da DIO em parceria com a NTT DATA.

<img width="3200" height="1800" alt="image" src="https://github.com/user-attachments/assets/599e346c-1b85-428d-bcba-f792033ddad5" />


---

## 🏛️ Arquitetura do Projeto

O sistema é composto por quatro serviços principais:

* **Eureka Server**: Descoberta de serviços
* **API Gateway**: Ponto de entrada único
* **Produto-Service**: Gerencia o catálogo de produtos
* **Pedido-Service**: Gerencia vendas e dados dos clientes

<img width="480" height="480" alt="image" src="https://github.com/user-attachments/assets/4a0ccdd3-576b-46f1-8bf9-774469a452de" />

---
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/b5d85002-2b16-4a95-b40e-42f0fa2aadda" />




---

## 💻 Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.3.1
* **Arquitetura:** Spring Cloud 2023.0.2
* **Service Discovery:** Netflix Eureka Server
* **API Gateway:** Spring Cloud Gateway
* **Comunicação:** REST APIs e OpenFeign
* **Persistência de Dados:**

  * Spring Data JPA
  * Hibernate
* **Banco de Dados:** MySQL
* **Build:** Maven
* **Outros:** Lombok

---

## ⚙️ Como Executar Localmente

### 1. Pré-requisitos

* JDK 17 ou superior
* Maven 3.8 ou superior
* MySQL Server em execução
* IDE de sua preferência (IntelliJ, VS Code, etc.)

### 2. Crie os bancos de dados

```sql
CREATE DATABASE produtos_db;
CREATE DATABASE pedidos_db;
```

### 3. Configure as credenciais

Altere os arquivos `application.properties` de `produto-service` e `pedido-service` com seu `spring.datasource.username` e `spring.datasource.password`.

### 4. Ordem de execução dos serviços

1. eureka-server
2. produto-service
3. pedido-service
4. api-gateway

> Aguarde cerca de 30 segundos entre o start de cada serviço para garantir o registro no Eureka.

### 5. Verificação

Acesse [http://localhost:8761](http://localhost:8761) para ver o painel do Eureka e confirmar o registro de `PRODUTO-SERVICE` e `PEDIDO-SERVICE`.

> Todas as requisições devem ser feitas através do API Gateway: [http://localhost:8700](http://localhost:8700)

---

## 📦 Exemplos de Requisições da API

### ✍️ Criar um Produto

**POST** `http://localhost:8700/produtos`

```json
{
  "nome": "X-Tudo Especial da Casa",
  "tipo": "PRODUTO",
  "unidadeMedida": "UN",
  "valor": 35.00,
  "precoDeCusto": 18.50,
  "descricao": "Pão, hambúrguer de 180g, queijo, presunto, ovo, bacon, alface, tomate, milho e batata palha."
}
```

### 👤 Criar um Cliente

**POST** `http://localhost:8700/clientes`

```json
{
  "nome": "Bruna Silva",
  "telefone": "(62) 98888-7777",
  "email": "bruna.silva@email.com",
  "endereco": "Avenida Anhanguera, 500",
  "cpfOuCnpj": "444.555.666-77"
}
```

### 📅 Criar um Pedido

**POST** `http://localhost:8700/pedidos`

> É necessário cadastrar primeiro clientes e produtos para obter os IDs.

```json
{
  "dataEntrega": "2025-07-12",
  "cliente": {
    "id": "ID_DO_CLIENTE_CADASTRADO"
  },
  "itens": [
    {
      "produtoId": "ID_DO_PRODUTO_CADASTRADO",
      "quantidade": 2
    }
  ]
}
```

---


---

Desenvolvido por [Mateus de Paula Rezende](https://github.com/mateusp-rezende)
