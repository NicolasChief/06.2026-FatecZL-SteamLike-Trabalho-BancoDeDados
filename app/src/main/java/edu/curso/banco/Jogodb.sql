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
cod int,
Nome varchar(50),
datanasc date ,
email varchar(100),
senha varchar(20),
telefone varchar(11) check(len(telefone)=10 or len(telefone) = 11),
saldo decimal(7, 2) DEFAULT(500)

primary key(cod)

)
go

CREATE TABLE Biblioteca(
ID  int unique , 
Usuariocod int,

Foreign key(Usuariocod) references Usuario(cod)
)
GO

Create table  Jogo_Biblioteca(
JogoID int ,
BibliotecaID int ,
dataAdicao date

primary key(BibliotecaID),
FOREIGN KEY(JogoID) REFERENCES Jogo(ID),

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