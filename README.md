# Person API

Essa API é o meu primeiro projeto com Java 21 + Spring Boot 3.2.0 onde ela um CRUD basico para cadastro de pessoas. Essa API esta sendo desenvolvida durante meu curso sobre Srping Boot e Java.

## Tecnologias usadas

---

- **Spring Boot 3.2.0** 🍃
- **Java 21** ☕
- **Flyway** 🪽
- **PostgreSQL** 🐘
- **Maven** Ⓜ️
- **Postman** 👨🏻‍🚀
- **JUnit 5 e Mockito** ✅
- **REST Assured** 🌐
- **Swagger**

## Como usar

---
Para usar recomendo usar o Postman para consumir os recursos da API com a URL ``http://localhost:8080/api/person/v1/`` usando o tipo de requisição (GET, POST, DELETE, PUT).
É recomendado que você tenha o Postgres instalado ou com um container rodando no Docker.

## Recursos

---
Os dados podem ser retornados de três maneiras **JSON, XML e YAML**. Para isso, você pode ir em Header no Postman e para cada formato insira o seguinte:

| Key        | Value                  |
|------------|------------------------|
| **Accept** | Application/${formato} |

---
### Formatos
- **Json** - para obter os dados no fromato Json
- **Xml** - para obter os dados no formato xml
- **x-yaml** - para obter os dados no formato YAML

---
## Avisos

Esse projeto ainda esta em desenvolvimento.

---
## Updates

---
### Mudança do DozerMapper para o ModelMapper:
Essa Api usa o padrão de projetos **VO** na sua arquitetura. No inicio, estava usando o **Dozzer** para fazer o mapeamen
to entidades para VO, porem, mediante aglguns erros que viamos enfrentando ao longo da implementação, decidimos usar o
**Model Mapper** para fazer essa conversão.

### Adicionado a dependencia do Swagger

A Nesse estagio de desenvolvimento da Api, ela agora possui a integração com a ferramenta de documentação **Swagger**.
 Para acessar a api integrada com o Swagger, basta acessar a url: ```http://localhost:8080/swagger-ui/index.html```
equanto a aplicação estiver subindo.

### Sistema de autenticação

Apesar de ainda bem limitado, essa api conta com um sistema de autenticação que funciona da seguinte forma:

1. Quando a aplicação subir, você vera algo assim no console da aplicação:

> My hash result1 {BCryptPasswordEncoder}$2a$10$tsEUYOxGJw3dwzbMXK8ou.qJJL94VMgBny7R588hp3WrSzqpof2Iu

> Isso essa sequencia de caracteres que você ve depois do fechamento das chaves, é o hash de uma senha que foi gravada no banco de dados. Isso acontece que, por questões de segurança e por obedecer ao padrão stateless das aplicações REST, não se armazena nada que no banco de dados que o cliente nos passa.

2. Quando voce ver isso va ate o banco de dados e dentro do banco de dados da aplicação, rode o seguinte script:

```update users
set password = '$2a$10$MqahAE2wxplPiFkgwvplc.U0APgV7TjWfRmCz1hIAQBnyeRAud7RK'
where id = 1;
```

Isso vai atualizar o hash da senha para o hash que foi gerado pela aplicação

3. Depois é ir no endpoint de autenticação e usar o username e a senha que estao disponiveis na aplicação.

### Adicionado uplaod e download para a API

**Como subir arquivos e baixa-los:**

1. Suba a aplicação e va ate o endpoint ```/api/file/v1/uploadFile``` e submeta algum arquivo. Caso queira, submeta mais de um.
> **Nota:**
> O limite maximo de arquivos que podem ser enviados é de apenas 215 por request. Voce pode ver mais sobre no application.yml

Ao final da sua requisição, você vai receber um payload dessa forma:
```
{
        "fileName": "Picsart Photo Studio.jpeg",
        "fileDownloadUri": "http://localhost:8080/api/file/v1/downloadFile/Picsart%20Photo%20Studio.jpeg",
        "fileType": "image/jpeg",
        "fileSize": 63007
}
```

2. Copie apartir do arquivo que o conteúdo apartir de downloadFile/ e cole na url que você setou no postman
e pronto, seu download esta feito ;)

### Adicioinada paginação na API

**Como usar paginação na API:**

1. Após subir a aplicação, abra o Postman e va ate a aba Params
2. Nas keys coloque os parametros **limit**, **page**, **direction**.
> **Notas:**
> - **limit:** Se refere a quantidade de objetos que serão retornados
> - **page:** Se refere ao numero da pagina que você vai estar vendo
> - **direction:** Se fere a forma a qual os objetos serão dispostos na pagina pode ser usadas as opçoes
> ***asc*** para a disposição ascendente e ***desc*** para descendente.
> Caso nenhum valor relacionado a orientação seja adicionado, por padrão, 
> a aplicação os dispora de maneira descendente.