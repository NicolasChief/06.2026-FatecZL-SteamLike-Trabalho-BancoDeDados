--Drop database Jogo

create database Jogo
go
use Jogo
go

Create table jogo(
ID int IDENTITY(1,1) Check(ID>0),
Nome varchar(50),
dataLancamento date,
preco decimal(6,2),
espacoArmazenamento decimal(5,2),
descricaojogo varchar(255),
descricaoRequisitos varchar(255),

PRIMARY KEY(ID) 
)
go

CREATE TABLE Usuario(
    cod int IDENTITY(1,1),
    Nome varchar(50),
    datanasc date ,
    email varchar(100),
    senha varchar(20),
    telefone varchar(11) check(len(telefone)=10 or len(telefone) = 11),
    saldo decimal(7, 2) DEFAULT(500),
    primary key(cod)
)
go

CREATE TABLE Biblioteca(
ID  int unique , 
Usuariocod int,

PRIMARY KEY(ID),
FOREIGN KEY(Usuariocod) REFERENCES Usuario(cod)
)
GO

Create table  Jogo_Biblioteca(
JogoID int ,
BibliotecaID int ,
dataAdicao date

PRIMARY KEY(JogoID, BibliotecaID),

FOREIGN KEY(JogoID) REFERENCES Jogo(ID),
FOREIGN KEY(BibliotecaID) REFERENCES Biblioteca(ID)

)
go 

CREATE TABLE Desenvolvedora(
    ID int IDENTITY(1,1),
    Nome varchar(100),
    CNPJ varchar(20),
    Email varchar(100),
    Senha varchar(50),
    Telefone varchar(20),

    PRIMARY KEY(ID)
)
GO

create table Desenvolvedora_Jogo (
DesenvolvedoraID int,
JogoID int

Primary key(DesenvolvedoraID,JogoID),
FOREIGN KEY (DesenvolvedoraID) references Desenvolvedora(ID),
FOREIGN KEY (JogoID) References Jogo(ID)
)
go


GO
 create table Publicadora (
 ID int,
 Nome varchar(100)

 Primary key (ID)
)
GO

Create table Publicadora_Jogo(
PublicadoraID int,
JogoID int

Primary key (PublicadoraID,JogoID),
Foreign key (PublicadoraID) references Publicadora(ID),
Foreign key (JogoID) references Jogo(ID)
)
go

Create table Genero (
ID int,
Nome varchar(20)

Primary key(ID)
)
go

Create table Genero_Jogo(
GeneroID Int,
JogoID int

Primary key(GeneroID,JogoID)
Foreign key (GeneroID) references Genero(ID),
FOREIGN KEY (JogoID) references Jogo(ID)
)
go

CREATE TABLE Compra(
    ID int PRIMARY KEY,
    dataCompra date,
    statusPedido varchar(30),
    valorTotal decimal(10,2),
    Usuariocod int,

    FOREIGN KEY(Usuariocod)
        REFERENCES Usuario(cod)
)
go

CREATE TABLE ItemCompra(
    ID int,
    JogoID int,
    CompraID int,
    quantidade int,
    precoUni decimal(10,2),
    PRIMARY KEY(ID,JogoID,CompraID)
)

INSERT INTO Genero (ID, Nome) VALUES
(1, 'Ação'),
(2, 'Aventura'),
(3, 'RPG'),
(4, 'Estratégia'),
(5, 'Esporte')

INSERT INTO Publicadora (ID, Nome) VALUES
(1, 'EA Games'),
(2, 'Ubisoft'),
(3, 'CD Projekt'),
(4, 'Valve')

INSERT INTO Desenvolvedora (Nome, CNPJ, Email, Senha, Telefone) VALUES
('BioWare',        '12.345.678/0001-90', 'contato@bioware.com',   'senha123', '11987654321'),
('CD Projekt RED', '98.765.432/0001-10', 'contato@cdprojekt.com', 'senha456', '11912345678'),
('Respawn',        '55.444.333/0001-22', 'contato@respawn.com',   'senha789', '11934567890')

INSERT INTO Jogo (Nome, dataLancamento, preco, espacoArmazenamento, descricaojogo, descricaoRequisitos) VALUES
('The Witcher 3',  '2015-05-19', 79.90, 50.00, 'RPG de mundo aberto', 'i7, 16GB RAM, GTX 970'),
('Cyberpunk 2077', '2020-12-10', 99.90, 70.00, 'RPG cyberpunk futurista', 'i7-9700, 12GB RAM, RTX 2060'),
('Apex Legends',   '2019-02-04',  0.00, 22.00, 'Battle royale grátis', 'i5, 8GB RAM, GTX 970'),
('FIFA 23',        '2022-09-27', 59.90, 50.00, 'Simulador de futebol', 'i5, 8GB RAM, GTX 970'),
('Far Cry 6',      '2021-10-07', 89.90, 60.00, 'FPS de mundo aberto', 'Ryzen 5, 8GB RAM, RX 570')

INSERT INTO Genero_Jogo (GeneroID, JogoID) VALUES
(3, 1), 
(3, 2), 
(1, 2), 
(1, 3), 
(5, 4), 
(1, 5), 
(2, 5) 

INSERT INTO Desenvolvedora_Jogo (DesenvolvedoraID, JogoID) VALUES
(2, 1), 
(2, 2), 
(3, 3), 
(1, 4), 
(1, 5)  

INSERT INTO Publicadora_Jogo (PublicadoraID, JogoID) VALUES
(3, 1), 
(3, 2), 
(4, 3), 
(1, 4),
(2, 5)  

INSERT INTO Usuario (Nome, datanasc, email, senha, telefone, saldo) VALUES
('Lucas Mendes',   '1995-03-14', 'lucas@email.com',   'abc123', '11987001122', DEFAULT),
('Ana Paula',      '2000-07-22', 'ana@email.com',      'xyz789', '1132001234',  DEFAULT),
('Rafael Costa',   '1990-11-05', 'rafael@email.com',  'pass001', '11999887766', 200.00),
('Fernanda Lima',  '1998-01-30', 'fernanda@email.com','pass002', '11955443322', 800.00)

INSERT INTO Biblioteca (ID, Usuariocod) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4)

INSERT INTO Jogo_Biblioteca (JogoID, BibliotecaID, dataAdicao) VALUES
(1, 1, '2023-01-10'),
(2, 1, '2023-03-05'),
(3, 2, '2022-11-20'),
(1, 3, '2021-06-15'),
(4, 4, '2023-07-01'),
(5, 4, '2023-08-14')

INSERT INTO Compra (ID, dataCompra, statusPedido, valorTotal, Usuariocod) VALUES
(1, '2023-01-09',  'Concluído', 79.90, 1),
(2, '2023-03-04',  'Concluído', 99.90, 1),
(3, '2022-11-19',  'Concluído',  0.00, 2),
(4, '2023-07-01',  'Concluído', 59.90, 4),
(5, '2023-08-13',  'Concluído', 89.90, 4)

INSERT INTO ItemCompra (ID, JogoID, CompraID, quantidade, precoUni) VALUES
(1, 1, 1, 1, 79.90),
(2, 2, 2, 1, 99.90),
(3, 3, 3, 1,  0.00),
(4, 4, 4, 1, 59.90),
(5, 5, 5, 1, 89.90)

-- 1. Total de jogos por gênero, apenas gêneros com mais de 1 jogo

SELECT g.Nome AS genero, COUNT(gj.JogoID) AS totalJogos
FROM Genero g
INNER JOIN Genero_Jogo gj ON g.ID = gj.GeneroID
GROUP BY g.Nome
HAVING COUNT(gj.JogoID) > 1

-- 2. Média, maior e menor preço dos jogos, excluindo gratuitos

SELECT 
    AVG(preco)  AS precoMedio,
    MAX(preco)  AS precoMaximo,
    MIN(preco)  AS precoMinimo
FROM Jogo
WHERE preco > 0

-- 3. Saldo dos usuários classificado em faixas

SELECT nome,
    saldo,
    CASE
        WHEN saldo >= 700 THEN 'Alto'
        WHEN saldo >= 300 THEN 'Médio'
        ELSE 'Baixo'
    END AS faixaSaldo
FROM Usuario
ORDER BY saldo DESC

-- 4. Jogos lançados nos últimos 10 anos com espaço acima da média

SELECT Nome, dataLancamento, espacoArmazenamento
FROM Jogo
WHERE dataLancamento >= DATEADD(YEAR, -10, GETDATE())
  AND espacoArmazenamento > (SELECT AVG(espacoArmazenamento) FROM Jogo)
ORDER BY espacoArmazenamento DESC

-- 5. Total gasto por usuário em compras concluídas

SELECT Usuariocod, 
    COUNT(ID)        AS totalCompras,
    SUM(valorTotal)  AS totalGasto,
    AVG(valorTotal)  AS ticketMedio
FROM Compra
WHERE statusPedido = 'Concluído'
GROUP BY Usuariocod
ORDER BY totalGasto DESC

-- 6. Quantidade de jogos por desenvolvedora, com nome da desenvolvedora

SELECT d.Nome AS desenvolvedora, COUNT(dj.JogoID) AS jogosDesenvolvidos
FROM Desenvolvedora d
INNER JOIN Desenvolvedora_Jogo dj ON d.ID = dj.DesenvolvedoraID
GROUP BY d.Nome
ORDER BY jogosDesenvolvidos DESC

-- 7. Todos os jogos com seus gêneros e desenvolvedoras

SELECT 
    j.Nome          AS jogo,
    g.Nome          AS genero,
    d.Nome          AS desenvolvedora
FROM Jogo j
INNER JOIN Genero_Jogo  gj ON j.ID = gj.JogoID
INNER JOIN Genero        g ON g.ID = gj.GeneroID
INNER JOIN Desenvolvedora_Jogo dj ON j.ID = dj.JogoID
INNER JOIN Desenvolvedora d ON d.ID = dj.DesenvolvedoraID
ORDER BY j.Nome

-- 8. Biblioteca de cada usuário com os jogos que possui
SELECT 
    u.Nome      AS usuario,
    j.Nome      AS jogo,
    jb.dataAdicao
FROM Usuario u
INNER JOIN Biblioteca     b  ON u.cod  = b.Usuariocod
INNER JOIN Jogo_Biblioteca jb ON b.ID  = jb.BibliotecaID
INNER JOIN Jogo            j  ON j.ID  = jb.JogoID
ORDER BY u.Nome, jb.dataAdicao

-- 9. Histórico de compras detalhado por usuário 
SELECT 
    u.Nome          AS usuario,
    c.dataCompra,
    j.Nome          AS jogo,
    ic.quantidade,
    ic.precoUni,
    c.statusPedido
FROM Usuario u
INNER JOIN Compra     c  ON u.cod  = c.Usuariocod
INNER JOIN ItemCompra ic ON c.ID   = ic.CompraID
INNER JOIN Jogo       j  ON j.ID   = ic.JogoID
ORDER BY u.Nome, c.dataCompra

-- 10. Jogos com publicadora e gênero

SELECT 
    j.Nome      AS jogo,
    j.preco,
    p.Nome      AS publicadora,
    g.Nome      AS genero
FROM Jogo j
LEFT JOIN Publicadora_Jogo pj ON j.ID = pj.JogoID
LEFT JOIN Publicadora       p ON p.ID = pj.PublicadoraID
LEFT JOIN Genero_Jogo       gj ON j.ID = gj.JogoID
LEFT JOIN Genero             g ON g.ID = gj.GeneroID
ORDER BY j.Nome

-- 11. Usuários e seus jogos comprados vs jogos na biblioteca

SELECT 
    u.Nome          AS usuario,
    j.Nome          AS jogo,
    CASE 
        WHEN jb.JogoID IS NOT NULL THEN 'Sim' 
        ELSE 'Não' 
    END             AS naBiblioteca,
    c.dataCompra
FROM Usuario u
INNER JOIN Compra      c   ON u.cod = c.Usuariocod
INNER JOIN ItemCompra  ic  ON c.ID  = ic.CompraID
INNER JOIN Jogo        j   ON j.ID  = ic.JogoID
LEFT JOIN  Biblioteca  b   ON u.cod = b.Usuariocod
LEFT JOIN  Jogo_Biblioteca jb ON b.ID = jb.BibliotecaID AND jb.JogoID = j.ID
ORDER BY u.Nome, j.Nome

-- 12. Desenvolvedoras que publicam seus próprios jogos

SELECT 
    d.Nome  AS desenvolvedora,
    j.Nome  AS jogo,
    p.Nome  AS publicadora
FROM Desenvolvedora d
INNER JOIN Desenvolvedora_Jogo dj ON d.ID = dj.DesenvolvedoraID
INNER JOIN Jogo                 j ON j.ID = dj.JogoID
INNER JOIN Publicadora_Jogo    pj ON j.ID = pj.JogoID
INNER JOIN Publicadora          p ON p.ID = pj.PublicadoraID
WHERE d.Nome = p.Nome