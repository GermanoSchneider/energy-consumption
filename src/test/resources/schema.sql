DROP TABLE T_ELECTRONIC IF EXISTS;
DROP TABLE T_CONSUMPTION IF EXISTS;

CREATE TABLE t_electronic (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    power DECIMAL(10, 2) NOT NULL,
    status VARCHAR(5) NOT NULL
);

--CREATE TABLE t_consumption (
--    id INT AUTO_INCREMENT PRIMARY KEY,
--    kilowatts DECIMAL(10, 2) NOT NULL,
--    creation_date DATE NOT NULL,
--    electronic_id INT NOT NULL,
--    FOREIGN KEY (electronic_id) REFERENCES t_electronic(id)
--);