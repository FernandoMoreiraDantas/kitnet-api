CREATE TABLE usuario (
	codigo bigserial NOT NULL PRIMARY KEY,
	cpf VARCHAR(11) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	fone VARCHAR(11) NOT NULL,
	senha VARCHAR(150) NOT NULL,
	admin BOOLEAN NOT NULL
	
);

INSERT INTO usuario (cpf,nome,fone,senha,admin) values ('88157679168','Fernando Moreira Dantas','62994326262','88157679168',true);