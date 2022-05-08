CREATE TABLE aluguel (
	id bigserial NOT NULL PRIMARY KEY,
	data_ini DATE NOT NULL,
	data_fim DATE,
	valor DECIMAL(10,2) NOT NULL,
	id_inquilino bigserial NOT NULL,
	id_kitnet bigserial NOT NULL,
	FOREIGN KEY (id_inquilino) REFERENCES inquilino(id),
	FOREIGN KEY (id_kitnet) REFERENCES kitnet(id)
);