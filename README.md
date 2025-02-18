# Sistema de biblioteca com Springboot e MySQL

Este projeto é uma API RESTful de uma biblioteca desenvolvida com **Spring Boot 3.4.1**, **Java 17**, **MySQL** e **Maven** na disciplina de Programacao Orientada a Objetos, para gerenciar livros e emprestimos.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.1**
- **Maven**
- **MySQL Driver**
- **Spring Web**
- **Spring Boot DevTools**
- **PostMan**

## Requisitos

- **Java 17**: Certifique-se de que o Java 17 está instalado. Para verificar a versão:
  ```bash
  java -version
  
## Instalação

#### 1. Clone o repositório:
   ```bash
   git clone https://github.com/MariPinas/ProjetoFinalPOO.git
   ```

#### 2. Configure o banco de dados:
 Na linha 19 do arquivo `src/main/java/com/example/Biblioteca/Banco/Conexao.java`:
```java
this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "1234");
```
Altere pelo nome de seu schema no banco, em "root" e "1234", altere para o seu usuário e senha.

   -PS: Não é necessário o arquivo de Conexao.java, pois o spring já gerencia essa conexão com o banco, mas criamos para o aprendizado de como fazer a conexão com o banco, você pode adicionar em
   `src/main/resources/application.properties`
   as linhas:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/meuschema
   spring.datasource.username=seuusuario
   spring.datasource.password=suasenha
   ```
- Se você quer usar as propriedades do application.properties para gerenciar a conexão, então comente ou exclua a classe Conexao.java que faz a conexão manual com o banco.
- O Spring Boot vai gerenciar a conexão para você automaticamente com base nas configurações no application.properties.
  
Obs.:(Não esqueça de criar o schema com o mesmo nome que configurou)

#### 3. Compilar e rodar o projeto

#### Usando IntelliJ IDEA:
- Se estiver usando o IntelliJ, basta clicar no botão **Run** para compilar e executar o projeto diretamente pela interface da IDE.

#### Usando o terminal:
- Se preferir rodar via terminal, utilize o Maven:
```bash
mvn clean install
mvn spring-boot:run
```

## Uso

A API tem os seguintes endpoints:

Livro
- **GetAll /livros**: Retorna todos os livros.
- **GetById /livros/{id}**: Retorna livro pelo seu ID.
- **POST /livros**: Adiciona um novo livro.
- **PUT /livros/{id}**: Atualiza um livro existente.
- **DELETE /livros/{id}**: Exclui um livro.
  
Empréstimo
- **GetAll /emprestimos**: Retorna todos os emprestimos.
- **GetById /emprestimos/{id}**: Retorna empréstimo pelo seu ID.
- **POST /emprestimos**: Adiciona um novo empréstimo.
- **PUT /emprestimos/{id}**: Atualiza um empréstimo existente.
- **DELETE /emprestimos/{id}**: Exclui um empréstimo.

ItemEmprestimo
- **GET /itens-emprestimo/emprestimo/{id}**: Retorna os livros que estao em um empréstimo.
- **GET /itens-emprestimo/all**: Retorna todos os itens-empréstimo.
