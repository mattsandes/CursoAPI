# Person API

Essa API é o meu primeiro projeto com Java 21 + Spring Boot 3.2.0 onde ela um CRUD basico para cadastro de pessoas. Essa API esta sendo desenvolvida durante meu curso sobre Srping Boot e Java.

## Tecnologias usadas

- Spring Boot 3.2.0
- Java 21
- Flyway para migrations
- PostgreSQL 
- Maven
- Postman

## Como usar

Para usar recomendo usar o Postman para consumir os recursos da API com a URL ```http://localhost:8080/person``` usando o tipo de requisição (GET, POST, DELETE, PUT).
É recomendado que você tenha o Postgres instalado ou com um container rodando no Docker.

## Recursos

Os dados podem ser retornados de três maneiras **JSON, XML e YAML**. Para isso, você pode ir em Header no Postman e para cada formato insira o seguinte:

|Key | Value |
|----|-------|
|Accept | Application/${formato}|

- **Json** - para obter os dados no fromato Json
- **Xml** - para obter os dados no formato xml
- **x-yaml** - para obter os dados no formato YAML

## Avisos

Esse projeto ainda esta em desenvolvimento.

## Updates

Essa Api usa o padrão de projetos **VO** na sua arquitetura. No inicio, estava usando o **Dozzer** para fazer o mapeamen
to entidades para VO, porem, mediante aglguns erros que viamos enfrentando ao longo da implementação, decidimos usar o
**Model Mapper** para fazer essa conversão.