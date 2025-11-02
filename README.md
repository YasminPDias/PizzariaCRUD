# Pizzaria CRUD - Sistema de Gerenciamento

Um sistema de desktop (CRUD) para gerenciamento de uma pizzaria, desenvolvido em Java Swing. O projeto permite o gerenciamento completo de Clientes, Pizzas e Pedidos, além de possuir um sistema de autenticação seguro para os operadores do sistema.

## Funcionalidades Principais

O sistema é dividido em dois módulos principais: autenticação e gerenciamento.

### Sistema de Autenticação
Um sistema de login separado para os "operadores" ou "administradores" do sistema, com as seguintes funções:
* **Tela de Login:** Autenticação de operadores cadastrados.
* **Cadastro de Operador:** Permite criar um novo login (nome, email, senha).
* **Recuperação de Senha:** Um fluxo de "Esqueci a senha" que valida o email antes de permitir a redefinição.
* **Segurança:** As senhas são armazenadas no banco de dados usando hash **SHA-256**.

### Módulo de Gerenciamento (CRUD)
Após o login, o operador tem acesso às seguintes telas:
* **Gerenciar Clientes:** CRUD completo (Adicionar, Editar, Excluir, Listar) para os clientes da pizzaria.
* **Gerenciar Pizzas:** CRUD completo para os sabores e tipos de pizzas (preço, tamanho, borda, etc.).
* **Gerenciar Pedidos:** CRUD completo para os pedidos, permitindo vincular clientes e pizzas.
* **Filtro de Pedidos:** A tela de pedidos permite filtrar a lista por cliente.

## Tecnologias e Padrões

* **Linguagem:** Java 21
* **Interface Gráfica (UI):** Java Swing
* **Banco de Dados:** MySQL
* **Conexão:** JDBC (com o driver MySQL Connector/J)
* **Padrões de Projeto:**
    * **MVC (Model-View-Controller):** A lógica é separada da interface.
    * **DAO (Data Access Object):** A lógica de banco de dados é isolada em classes DAO (ex: `MySQLUserDAO`, `MySQLAdminUserDAO`).
    * **Factory:** Utiliza o padrão Factory (`DAOFactory`) para instanciar os DAOs.
