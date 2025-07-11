🚀 Gestor de Pedidos - Microsserviços

Este projeto é a evolução do Gestor de Pedidos Alfred https://github.com/mateusp-rezende/GestorDePedidosAlfred, um sistema simples com o objetivo inicial de registrar produtos e clientes para gerar pedidos, notas em PDF e relatórios de vendas. O foco sempre foi criar uma ferramenta para auxiliar microempreendedores na contabilidade e análise geral do negócio.

Inspirado pelo bootcamp da DIO e pelo Desafio Técnico proposto, o projeto foi reimaginado e reconstruído com uma arquitetura moderna de microsserviços. Essa nova abordagem, utilizando Spring Boot e Spring Cloud, não apenas o torna mais próximo de uma aplicação real, mas também cumpre os requisitos do desafio, demonstrando conceitos de resiliência, escalabilidade e comunicação entre serviços.

O objetivo final é criar uma ferramenta completa com as funções: de criação de pedidos, emissão de relatorios das vendas csv enviados todo fim de mes , com uma interface simples e organizada desenvolvida em React.

📋 Descrição do Desafio

O projeto segue a arquitetura e os requisitos técnicos propostos no Desafio Técnico de Microsserviços da DIO em parceria com a NTT DATA, conforme ilustrado abaixo:
<img width="3200" height="1800" alt="image" src="https://github.com/user-attachments/assets/9f6180cf-a730-4b83-a6a7-18a05828c938" />


🏛️ Arquitetura do Projeto

O sistema é composto por quatro serviços principais que trabalham em conjunto: um Eureka Server para descoberta de serviços,
um API Gateway como ponto de entrada único, um Serviço de Produtos para gerenciar o catálogo e um Serviço de Pedidos para gerenciar as vendas e clientes.

A imagem abaixo ilustra o fluxo de comunicação entre os componentes:
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/bdc00460-29e8-44a9-aa1d-b11caec8607a" />

💻 Tecnologias Utilizadas

Linguagem: Java 17

Framework Principal: Spring Boot 3.3.1

Arquitetura:

Spring Cloud 2023.0.2

Service Discovery: Netflix Eureka Server

API Gateway: Spring Cloud Gateway

Comunicação: REST APIs & OpenFeign

Persistência de Dados:

Spring Data JPA

Hibernate

Banco de Dados: MySQL

Build e Dependências: Maven

Utilitários: Lombok

⚙️ Como Executar Localmente
Siga os passos abaixo para rodar a aplicação completa na sua máquina.

Pré-requisitos
JDK 17 ou superior

Maven 3.8 ou superior

MySQL Server rodando

Uma IDE de sua preferência (IntelliJ, VS Code, etc.)

1. Preparação do Banco de Dados
Certifique-se de que seu servidor MySQL esteja em execução. Crie os dois bancos de dados necessários para a aplicação:

-CREATE DATABASE produtos_db;
-CREATE DATABASE pedidos_db;

2. Configuração
Verifique os arquivos application.properties dentro de produto-service e pedido-service e ajuste as credenciais do banco de dados (spring.datasource.username e spring.datasource.password) se necessário.

3. Ordem de Execução
 Execute cada aplicação Spring Boot na seguinte sequência:

1- eureka-server
2- produto-service 
3- pedido-service 
4- api-gateway

Aguarde cerca de 30 segundos após iniciar cada serviço para garantir que o registro no Eureka seja concluído antes de iniciar o próximo.

4. Verificação
Acesse http://localhost:8761 para ver o painel do Eureka e confirmar que PRODUTO-SERVICE e PEDIDO-SERVICE estão registrados.
<img width="1331" height="532" alt="image" src="https://github.com/user-attachments/assets/f9c7743d-20e0-4882-9801-7a89367f0f0e" />


Todas as requisições da API devem ser feitas através do API Gateway, que estará rodando na porta definida em sua configuração (ex: http://localhost:8700).
