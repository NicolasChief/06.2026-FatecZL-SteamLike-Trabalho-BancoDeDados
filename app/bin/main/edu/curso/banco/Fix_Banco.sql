-- Script para corrigir a estrutura do banco Jogo
-- Execute este script no SQL Server Management Studio

USE Jogo
GO

-- Remover todas as constraints e tabelas dependentes
BEGIN
    -- Dropar Desenvolvedora_Jogo
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Desenvolvedora_Jogo')
        DROP TABLE Desenvolvedora_Jogo
    GO
    
    -- Dropar Publicadora_Jogo
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Publicadora_Jogo')
        DROP TABLE Publicadora_Jogo
    GO
    
    -- Dropar Genero_Jogo
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Genero_Jogo')
        DROP TABLE Genero_Jogo
    GO
    
    -- Dropar Jogo_Biblioteca
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Jogo_Biblioteca')
        DROP TABLE Jogo_Biblioteca
    GO
    
    -- Dropar ItemCompra
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ItemCompra')
        DROP TABLE ItemCompra
    GO
    
    -- Dropar Compra
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Compra')
        DROP TABLE Compra
    GO
    
    -- Dropar Jogo
    IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Jogo')
        DROP TABLE Jogo
    GO
END

-- Recriar tabela Jogo com IDENTITY
CREATE TABLE Jogo (
    ID INT IDENTITY(1,1) PRIMARY KEY CHECK(ID > 0),
    Nome VARCHAR(50),
    dataLancamento DATE,
    preco DECIMAL(6,2),
    espacoArmazenamento DECIMAL(5,2),
    descricaojogo VARCHAR(255),
    descricaoRequisitos VARCHAR(255)
)
GO

-- Recriar tabelas de relacionamento
CREATE TABLE Desenvolvedora_Jogo (
    DesenvolvedoraID INT,
    JogoID INT,
    PRIMARY KEY (DesenvolvedoraID, JogoID),
    FOREIGN KEY (DesenvolvedoraID) REFERENCES Desenvolvedora(ID),
    FOREIGN KEY (JogoID) REFERENCES Jogo(ID)
)
GO

CREATE TABLE Publicadora_Jogo (
    PublicadoraID INT,
    JogoID INT,
    PRIMARY KEY (PublicadoraID, JogoID),
    FOREIGN KEY (PublicadoraID) REFERENCES Publicadora(ID),
    FOREIGN KEY (JogoID) REFERENCES Jogo(ID)
)
GO

CREATE TABLE Genero_Jogo (
    GeneroID INT,
    JogoID INT,
    PRIMARY KEY (GeneroID, JogoID),
    FOREIGN KEY (GeneroID) REFERENCES Genero(ID),
    FOREIGN KEY (JogoID) REFERENCES Jogo(ID)
)
GO

CREATE TABLE Jogo_Biblioteca (
    ID INT UNIQUE,
    Usuariocod INT,
    FOREIGN KEY(Usuariocod) REFERENCES Usuario(cod)
)
GO

CREATE TABLE ItemCompra (
    ID INT,
    JogoID INT,
    CompraID INT,
    quantidade INT,
    precoUni DECIMAL(10,2),
    PRIMARY KEY(ID, JogoID, CompraID),
    FOREIGN KEY(JogoID) REFERENCES Jogo(ID),
    FOREIGN KEY(CompraID) REFERENCES Compra(ID)
)
GO

CREATE TABLE Compra (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    dataCompra DATE,
    statusPedido VARCHAR(30),
    valorTotal DECIMAL(10,2),
    Usuariocod INT,
    FOREIGN KEY(Usuariocod) REFERENCES Usuario(cod)
)
GO

-- Verificar as tabelas criadas
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME IN ('Jogo', 'Desenvolvedora', 'Desenvolvedora_Jogo', 'Publicadora', 'Publicadora_Jogo')
GO

-- Mostrar estrutura da tabela Jogo
EXEC sp_help 'Jogo'
GO

