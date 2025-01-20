# Projeto - TaskEase

## Sobre o Projeto
Este é um projeto de lista de tarefas desenvolvido como parte do teste seletivo para a InfoCorp.

## Clonando o Repositório
Para começar, clone o repositório usando o comando:
```
git clone https://github.com/IuryAlmeidaDev/Projeto_TaskEase
```

## Construindo o Projeto
Para construir o projeto, execute o seguinte comando:
```
mvnw clean install
```

## Executando a Aplicação
Para executar a aplicação localmente, use o comando:
```
mvnw spring-boot:run
```
A aplicação estará disponível em:
- [http://localhost:8080](http://localhost:8080)

## Testando a Aplicação
Você pode testar a aplicação de duas maneiras:
1. **Localmente**
2. **Diretamente na hospedagem**

### Testando Localmente
#### Executando a Aplicação Localmente:
Siga os passos acima para clonar, construir e executar a aplicação localmente.

#### Acessando a Documentação Swagger:
- Acesse a documentação Swagger em:
  - [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
  - Use para visualizar e testar as rotas disponíveis.

#### Acessando o Banco de Dados H2:
- Acesse o console do banco de dados H2 em:
  - [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- Credenciais:
  - **JDBC URL**: `jdbc:h2:mem:todolist`
  - **Usuário**: `admin`
  - **Senha**: `admin`

### Testando na Hospedagem
A aplicação está hospedada na Render e pode ser acessada através dos seguintes links:

#### Aplicação Hospedada:
- [Aplicação Hospedada](https://projeto-taskease.onrender.com)

#### Documentação Swagger:
- [Documentação Swagger](https://projeto-taskease.onrender.com/swagger-ui/index.html#/)

#### Banco de Dados H2:
- Acesse o console do banco de dados H2 em:
  - [H2 Console](https://projeto-taskease.onrender.com/h2-console)
- Credenciais:
  - **JDBC URL**: `jdbc:h2:mem:todolist`
  - **Usuário**: `admin`
  - **Senha**: `admin`

## Testando com Postman
Você pode usar o Postman para testar as rotas da aplicação. Siga os passos abaixo:

### Importando a Documentação Swagger no Postman:
1. Acesse a documentação Swagger em:
   - [https://projeto-taskease.onrender.com/swagger-ui/index.html#/](https://projeto-taskease.onrender.com/swagger-ui/index.html#/)
2. Clique em **"Export"** e selecione **"Postman Collection"**.
3. Importe a coleção no Postman.

### Executando as Requisições:
- Use as rotas disponíveis na coleção importada para testar a aplicação.

## Testes
Para executar os testes, use o comando:
```
mvnw test
```

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir **issues** e **pull requests**.

## Licença
Este projeto está licenciado sob a Licença Apache 2.0.
- Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

