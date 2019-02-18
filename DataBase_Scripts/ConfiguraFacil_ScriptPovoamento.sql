Use ConfiguraFacil;

INSERT INTO Funcionario
	(Username, Nome, Password)
    VALUES
		('a1234', 'Beatriz Mendes', 'a1234'),
		('admin', 'Miguel Teixeira', 'admin'),
		('f1234', 'André Monteiro', 'f1234'),
		('f77045', 'Ricardo Pereira', 'f77045'),
        ('f32954', 'Raquel Dias', 'f32954'),
        ('f79987', 'Ana Guimaraes', 'f79987'),
        ('f77161', 'Maria Ines Alves', 'f77161');	
		
SELECT * FROM Funcionario;

INSERT INTO Pacote
	(idPacote, Designacao, Categoria, Preco)
    VALUES
		(1, 'Base 1', 'Base', 15000.0),
		(2, 'Base 2', 'Base', 21000.0),
		(3, 'Sport', 'Opcional', 1560.0),
		(4, 'Confort', 'Opcional', 2320.0);

SELECT * FROM Pacote;

INSERT INTO Componente
	(idComponente, Designacao, Preco, Categoria, Stock, Pacote_id)
    VALUES
	 	(1, '250d', 15000, 'Motor', 4, 1),
	 	(2, '300d', 20000, 'Motor', 2, 2),
	 	(3, 'Preto', 400, 'Pintura', 3, 1),
	 	(4, 'Branco', 400, 'Pintura', 5, 2),
	 	(5, 'Cinzento Mate', 1200, 'Pintura', 10, NULL),
	 	(6, 'Azul Brilhante', 1700, 'Pintura', 8, NULL),
	 	(7, 'Jantes 17\"', 650, 'Jantes', 15, 1),
	 	(8, 'Jantes 18\"', 750, 'Jantes', 4, 2),
	 	(9, 'Jantes Desportivas 19\"', 1200, 'Jantes', 2, 3),
	 	(10, 'Estofos em Tecido', 350, 'Estofos', 3, 1),
	 	(11, 'Estofos em Pele', 500, 'Estofos', 5, 4),
	 	(12, 'Estofos em Pele Artico', 650, 'Estofos', 2, 2),
	 	(13, 'Teto de abrir', 1100, 'Detalhes Interiores', 5, 4),
	 	(14, 'Vidros Escurecidos', 400, 'Detalhes Exteriores', 2, 3),
		(15, 'Aquecimento Auxiliar', 1300, 'Detalhes Interiores', 3, 4),
	 	(16, 'Ar Condicionado Automatico', 750, 'Detalhes Interiores', 19, NULL),
	 	(17, 'Head-up Display', 1500, 'Detalhes Interiores', 7, NULL),
	 	(18, 'Volante Multifuncoes', 350, 'Detalhes Interiores', 2, 3),
	 	(19, 'Gancho Para Reboque', 850, 'Detalhes Exteriores', 8, NULL),
	 	(20, 'Alarme', 200, 'Detalhes Exteriores', 6, NULL);

SELECT * FROM Componente;

INSERT INTO ComponenteObrigatorio
	(idCompObrig, Componente_id)
    VALUES
		(8,5),
		(17,18);
		

SELECT * FROM ComponenteObrigatorio;

SELECT CO.* FROM ComponenteObrigatorio CO inner join Componente C on CO.Componente_id=C.idComponente
where C.idComponente = 2;

INSERT INTO ComponenteIncompativel
	(idCompIncomp, Componente_idComponente)
    VALUES
		(4,3),
		(5,3),
		(6,3),
		(3,4),
		(5,4),
		(6,4),
		(3,5),
		(4,5),
		(6,5),
		(3,6),
		(4,6),
		(5,6),
		(8,7),
		(9,7),
		(7,8),
		(9,8),
		(7,9),
		(8,9),
		(11,10),
		(12,10),
		(10,11),
		(12,11),
		(10,12),
		(11,12),
		(14,13),		
		(13,14),
		(16,15),
		(15,16);


SELECT * FROM ComponenteIncompativel;

SELECT * FROM ComponenteIncompativel CI inner join Componente C on CI.Componente_idComponente=C.idComponente
where C.idComponente = 2;



