
CREATE DATABASE Horario;

USE Horario;

CREATE TABLE EE(
    idEE int NOT NULL auto_increment,
    nombre varchar(255) NOT NULL,
    profesor varchar(255) NOT NULL,
    primary key (idEE)
);

CREATE TABLE Clase(
    idClase int NOT NULL auto_increment,
    idEE int NOT NULL,
    dia varchar(15) NOT NULL,
    horaInicio time NOT NULL,
    horaFin time NOT NULL,
    salon varchar(50) NOT NULL,
    nota text,
    primary key (idClase, idEE)
);

ALTER TABLE Clase ADD CONSTRAINT
    fk_clase foreign key (idEE)
    REFERENCES EE (idEE);