# Sistema de Plano de Cortes para Marmoraria

Aplicacao web para gestao de clientes, chapas, pedidos e geracao de planos de corte para marmoraria. O projeto foi desenvolvido com Spring Boot, Thymeleaf e JPA, com suporte a banco MySQL e H2.

## Funcionalidades

- Dashboard com totais de clientes, chapas, pedidos e pedidos em andamento.
- Cadastro, edicao, busca e remocao de clientes.
- Cadastro, edicao, filtro e remocao de chapas por material.
- Cadastro e acompanhamento de pedidos.
- Inclusao e remocao de pecas em pedidos.
- Atualizacao de status dos pedidos.
- Geracao de plano de corte a partir de um pedido e uma chapa disponivel.
- Visualizacao grafica das pecas posicionadas na chapa.
- Calculo de aproveitamento da chapa e pecas nao encaixadas.

## Tecnologias

- Java 21
- Spring Boot 3.2.3
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Bean Validation
- Lombok
- Maven
- MySQL
- H2 Database

## Requisitos

- JDK 21 ou superior
- Maven 3.9 ou superior
- MySQL, caso use a configuracao padrao atual do projeto

## Como Executar

Clone o repositorio, entre na pasta do projeto e execute:

```bash
mvn spring-boot:run
```

A aplicacao ficara disponivel em:

```text
http://localhost:8080
```

## Configuracao do Banco de Dados

As configuracoes ficam em:

```text
src/main/resources/application.properties
```

Atualmente o projeto esta configurado para usar MySQL em:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marmoraria?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Antes de executar em outra maquina, ajuste `spring.datasource.username` e `spring.datasource.password` conforme seu ambiente local.

Tambem ha uma configuracao de H2 comentada no arquivo. Para rodar sem MySQL, descomente as propriedades do H2 e comente as propriedades do MySQL.

Exemplo:

```properties
spring.datasource.url=jdbc:h2:mem:marmoraria;DB_CLOSE_DELAY=-1;MODE=MySQL
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Rotas Principais

| Rota | Descricao |
| --- | --- |
| `/` | Dashboard |
| `/clientes` | Lista e busca de clientes |
| `/clientes/novo` | Cadastro de cliente |
| `/chapas` | Lista e filtro de chapas |
| `/chapas/novo` | Cadastro de chapa |
| `/pedidos` | Lista e filtro de pedidos |
| `/pedidos/novo` | Cadastro de pedido |
| `/pedidos/{id}` | Detalhes do pedido, itens e planos |
| `/planos/{id}` | Visualizacao do plano de corte |

## Estrutura do Projeto

```text
src/main/java/com/marmoraria
|-- algorithm      # Algoritmo de guilhotina para posicionamento das pecas
|-- controller     # Controllers MVC
|-- entity         # Entidades JPA
|-- repository     # Repositorios Spring Data JPA
|-- service        # Regras de negocio
`-- MarmorariaApplication.java

src/main/resources
|-- static/css     # Estilos da aplicacao
|-- templates      # Telas Thymeleaf
`-- application.properties
```

## Algoritmo de Corte

A geracao do plano usa um algoritmo de guilhotina localizado em:

```text
src/main/java/com/marmoraria/algorithm/AlgoritmoGuilhotina.java
```

O algoritmo ordena as pecas por area, tenta encaixa-las nos espacos livres da chapa e permite rotacao quando isso ajuda no encaixe. Ao final, registra:

- pecas encaixadas;
- pecas nao encaixadas;
- posicao `x` e `y` de cada peca;
- dimensao final da peca;
- percentual de aproveitamento da chapa.

## Build

Para gerar o pacote da aplicacao:

```bash
mvn clean package
```

O artefato sera gerado na pasta:

```text
target/
```

## Observacoes

- O Hibernate esta configurado com `spring.jpa.hibernate.ddl-auto=update`, entao as tabelas sao criadas/atualizadas automaticamente conforme as entidades.
- Para ambientes de producao, evite manter credenciais reais no `application.properties`. Prefira variaveis de ambiente ou perfis separados.
- A pasta `target/` contem arquivos gerados pelo Maven e normalmente nao precisa ser versionada.
