CREATE DATABASE GerenciadorReservas
GO
USE GerenciadorReservas
GO
CREATE TABLE Recurso(
idRecurso		INT				NOT NULL IDENTITY(2001,1),
nome			VARCHAR(40)		NOT NULL,
descricao		VARCHAR(255)	NOT NULL,
manutencao		BIT				NOT NULL,
PRIMARY KEY (idRecurso)
)
GO
CREATE TABLE Sala(
idSala			INT				NOT NULL IDENTITY(1001,1),
nome			VARCHAR(40)		NOT NULL,
capacidade		INT				NOT NULL,
PRIMARY KEY (idSala)
)
GO
CREATE TABLE TipoUsuario(
idTipo			INT				NOT NULL IDENTITY(101,1),
nome			VARCHAR(40)		NOT NULL,
horasPermitidas TIME(2)				NOT NULL,
PRIMARY KEY (idTipo)
)
GO
CREATE TABLE Usuario(
idUsuario		INT				NOT NULL IDENTITY(1,1),
tipoUsuarioId   INT				NOT NULL,
nome			VARCHAR(40)		NOT NULL,
email			VARCHAR(40)		NOT NULL,
PRIMARY KEY (idUsuario),
FOREIGN KEY (tipoUsuarioId) REFERENCES TipoUsuario(idTipo)
)
GO
CREATE TABLE Reserva(
idReserva		INT				NOT NULL IDENTITY(3001,1),
salaId			INT				NOT NULL,
UsuarioId		INT				NOT NULL,
TipoId			INT				NOT NULL,
RecursoId		INT				NOT NULL,
dataReserva		DATE			NOT NULL,
horaInicio		TIME(2)			NOT NULL,
horaFim			TIME(2)			NOT NULL,
PRIMARY KEY(idReserva),
FOREIGN KEY(salaId) REFERENCES Sala(idSala),
FOREIGN KEY(UsuarioId) REFERENCES Usuario(idUsuario),
FOREIGN KEY(RecursoId) REFERENCES Recurso(idRecurso),
FOREIGN KEY(TipoId) REFERENCES tipoUsuario(idTipo)
)