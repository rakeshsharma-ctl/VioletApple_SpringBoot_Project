-- Drop table if exists
DROP TABLE IF EXISTS Fruit;

-- Create table
CREATE TABLE Fruit (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);