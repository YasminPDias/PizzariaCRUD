DROP DATABASE IF EXISTS pizzaria;

CREATE DATABASE IF NOT EXISTS pizzaria;

USE pizzaria;

CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo ENUM('M', 'F'),
    email VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS pizza(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    flavor TEXT NOT NULL,
    descricao TEXT NOT NULL,
    size ENUM('P', 'M', 'G'),
    valor DOUBLE NOT NULL,
    border ENUM('S', 'N')
);


CREATE TABLE IF NOT EXISTS orders(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    observation TEXT,
    pizza_id INT NOT NULL,
    orders_date DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(pizza_id) REFERENCES pizza(id)
    
);
CREATE TABLE IF NOT EXISTS admin_logins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);


INSERT INTO users VALUES
(DEFAULT, "Emerson Carvalho", "M", "emerson@mail.com"),
(DEFAULT, "Luiza Carvalho", "M", "lu@mail.com"),
(DEFAULT, "Denise Carvalho", "M", "de@mail.com"),
(DEFAULT, "Noé Carvalho", "M", "noe@mail.com"),
(DEFAULT, "Rosânia Carvalho", "M", "ro@mail.com");

INSERT INTO pizza (flavor, descricao, size, valor, border) VALUES
('Calabresa', 'Molho de tomate, mussarela, calabresa fatiada e cebola roxa', 'M', 39.90, 'S'),
('Mussarela', 'Molho de tomate, mussarela e orégano', 'P', 29.90, 'N'),
('Frango com Catupiry', 'Molho de tomate, frango desfiado e catupiry', 'G', 52.00, 'S'),
('Portuguesa', 'Molho de tomate, mussarela, presunto, ovo, cebola e pimentão', 'M', 44.50, 'N'),
('Quatro Queijos', 'Molho de tomate, mussarela, provolone, parmesão e gorgonzola', 'G', 55.00, 'S'),
('Marguerita', 'Molho de tomate, mussarela, tomate e manjericão fresco', 'P', 32.00, 'N'),
('Pepperoni', 'Molho de tomate, mussarela e fatias de pepperoni', 'M', 46.90, 'S'),
('Vegetariana', 'Molho de tomate, mussarela, pimentão, cebola, milho, ervilha e tomate', 'G', 49.90, 'N'),
('Bacon com Cheddar', 'Molho de tomate, mussarela, bacon e cheddar cremoso', 'M', 47.50, 'S'),
('Chocolate', 'Pizza doce com chocolate ao leite derretido e granulado', 'P', 34.00, 'N');

INSERT INTO orders VALUES
(DEFAULT, "Sem Cebola", 3, CURDATE(), 1),
(DEFAULT, "Sem Azeitona", 2, CURDATE(), 2),
(DEFAULT, "Sem Mussarela", 5, CURDATE(), 3),
(DEFAULT, "", 1, CURDATE(), 4),
(DEFAULT, "Sem Cebola", 4, CURDATE(), 5);



