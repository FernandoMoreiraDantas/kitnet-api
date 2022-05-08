CREATE TABLE usuario (
	codigo bigserial NOT NULL PRIMARY KEY,
	cpf VARCHAR(11) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

INSERT INTO usuario (codigo,cpf,nome,senha) values(1,'88157679168','Fernando Moreira Dantas','88157679168');
INSERT INTO usuario (codigo,cpf,nome,senha) values(2,'88157679168','André Luiz Loiola Vaz','88157679168');