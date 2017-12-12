-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-11-15 03:11:38.911

-- tables
-- Table: Acudiente
CREATE TABLE Acudiente (
	nombre varchar(100)  NOT NULL,
	correo varchar(50)  NOT NULL,
	CONSTRAINT correoAcudiente UNIQUE (correo),
	CONSTRAINT Acudiente_pk PRIMARY KEY (correo)
);

-- Table: ConsejeroAcademico
CREATE TABLE ConsejeroAcademico (
	codigo int  NOT NULL,
	nombre varchar(100)  NOT NULL,
	correo varchar(50)  NOT NULL,
	CONSTRAINT correoConsejero UNIQUE (correo),
	CONSTRAINT ConsejeroAcademico_pk PRIMARY KEY (codigo)
);

-- Table: CoordinadorCancelaciones
CREATE TABLE CoordinadorCancelaciones (
	codigo int  NOT NULL,
	nombre varchar(100)  NOT NULL,
	correo varchar(50)  NOT NULL,
	CONSTRAINT correoCoordinador UNIQUE (correo),
	CONSTRAINT CoordinadorCancelaciones_pk PRIMARY KEY (codigo)
);


-- Table: Asignatura
CREATE TABLE Asignatura (
	nemonico varchar(4)  NOT NULL,
	nombre varchar(100)  NOT NULL,
	CONSTRAINT Asignatura_pk PRIMARY KEY (nemonico)
);

-- Table: Estudiante
CREATE TABLE Estudiante (
	codigo int  NOT NULL,
	nombre varchar(100)  NOT NULL,
	asignaturas varchar(10000)  NOT NULL,
	versionPlanDeEstudio int  NOT NULL,
	numeroMatriculas int  NULL,
	correo varchar(50)  NOT NULL,
	ConsejeroAcademico_codigo int  NOT NULL,
	CoordinadorCancelaciones_codigo int  NOT NULL,
	programaAcademico varchar(100)  NOT NULL,
	CONSTRAINT correo UNIQUE (correo),
	Acudiente_correo varchar(50)  NOT NULL,
	CONSTRAINT Estudiante_pk PRIMARY KEY (codigo)
);

-- Table: PlanDeEstudios
CREATE TABLE PlanDeEstudios (
	id int  NOT NULL,
	ProgramaAcademico varchar(40)  NOT NULL,
	contenido varchar(20000)  NOT NULL,
	totalCreditos int  NOT NULL,
	CONSTRAINT PlanDeEstudios_pk PRIMARY KEY (id,ProgramaAcademico)
);

-- Table: ProgramaAcademico
CREATE TABLE ProgramaAcademico (
	nombre varchar(100)  NOT NULL,
	institucion varchar(150)  NOT NULL,
	CONSTRAINT ProgramaAcademico_pk PRIMARY KEY (nombre)
);

-- Table: Solicitud
CREATE TABLE Solicitud (
	numero int  NOT NULL,
	fecha TIMESTAMP NOT NULL,
	justificacion varchar(200)  NOT NULL,
	comentarios varchar(200)  NULL,
	estado varchar(30)  NULL,
	acuseRecibo boolean  NULL,
	avalConsejero boolean  NULL,
	Estudiante_codigo int  NOT NULL,
	necesitaAcuseRecibo boolean  NULL,
	Asignatura_nemonico varchar(4)  NOT NULL,
	CONSTRAINT Solicitud_pk PRIMARY KEY (numero)
);

-- Table: Universidad
CREATE TABLE Universidad (
	totalCredits int  NOT NULL,
	nombre varchar(150)  NOT NULL,
	CONSTRAINT Universidad_pk PRIMARY KEY (nombre)
);

-- foreign keys
-- Reference: Estudiante_Acudiente (table: Estudiante)
ALTER TABLE Estudiante ADD CONSTRAINT Estudiante_Acudiente
	FOREIGN KEY (Acudiente_correo)
	REFERENCES Acudiente (correo)  
	NOT DEFERRABLE
;

-- Reference: Solicitud_Asignatura (table: Solicitud)
ALTER TABLE Solicitud ADD CONSTRAINT Solicitud_Asignatura
	FOREIGN KEY (Asignatura_nemonico)
	REFERENCES Asignatura (nemonico)  
;


-- Reference: Estudiante_ConsejeroAcademico (table: Estudiante)
ALTER TABLE Estudiante ADD CONSTRAINT Estudiante_ConsejeroAcademico
	FOREIGN KEY (ConsejeroAcademico_codigo)
	REFERENCES ConsejeroAcademico (codigo)  
	NOT DEFERRABLE
;

-- Reference: Estudiante_CoordinadorCancelaciones (table: Estudiante)
ALTER TABLE Estudiante ADD CONSTRAINT Estudiante_CoordinadorCancelaciones
	FOREIGN KEY (CoordinadorCancelaciones_codigo)
	REFERENCES CoordinadorCancelaciones (codigo)  
	NOT DEFERRABLE
;

-- Reference: Estudiante_ProgramaAcademico_Principal (table: Estudiante)
ALTER TABLE Estudiante ADD CONSTRAINT Estudiante_ProgramaAcademico_Principal
	FOREIGN KEY (programaAcademico)
	REFERENCES ProgramaAcademico (nombre)  
	NOT DEFERRABLE
;

-- Reference: PlanDeEstudios_ProgramaAcademico (table: PlanDeEstudios)
ALTER TABLE PlanDeEstudios ADD CONSTRAINT PlanDeEstudios_ProgramaAcademico
	FOREIGN KEY (ProgramaAcademico)
	REFERENCES ProgramaAcademico (nombre)  
	NOT DEFERRABLE
;

-- Reference: Solicitud_Estudiante (table: Solicitud)
ALTER TABLE Solicitud ADD CONSTRAINT Solicitud_Estudiante
	FOREIGN KEY (Estudiante_codigo)
	REFERENCES Estudiante (codigo)  
	NOT DEFERRABLE
;

-- Reference: programasAcademicos (table: ProgramaAcademico)
ALTER TABLE ProgramaAcademico ADD CONSTRAINT programasAcademicos
	FOREIGN KEY (institucion)
	REFERENCES Universidad (nombre)  
	NOT DEFERRABLE
;

-- Poblar Universidad --
INSERT INTO Universidad VALUES (18,'Escuela Colombiana de Ingeniería Julio Garavito');

-- Poblar ProgramaAcademico --
INSERT INTO ProgramaAcademico VALUES ('Ingeniería de sistemas','Escuela Colombiana de Ingeniería Julio Garavito');

INSERT INTO ProgramaAcademico VALUES ('Ingeniería industrial','Escuela Colombiana de Ingeniería Julio Garavito');

-- Poblar ConsejeroAcademico
INSERT INTO ConsejeroAcademico VALUES (231831,'Claudia Patricia Santiago Cely','claudia.santiago@escuelaing.edu.co');

INSERT INTO ConsejeroAcademico VALUES (313131,'Claudia Patricia Castañeda Bermúdez','patricia.castaneda@escuelaing.edu.co');

INSERT INTO ConsejeroAcademico VALUES (4235860,'Wilmer Edicson Garzón Alfonso','wilmer.garzon@escuelaing.edu.co');

INSERT INTO ConsejeroAcademico VALUES (568970,'Ignacio Pérez Vélez','ignacio.perez@escuelaing.edu.co');

INSERT INTO ConsejeroAcademico VALUES (1675926,'Sonia Alexandra Jaimes Suarez','sonia.jaimes@escuelaing.edu.co');

-- Poblar Coordinador de Cancelaciones
INSERT INTO CoordinadorCancelaciones VALUES (428131,'Oswaldo Castillo Navetty','oswaldo.castillo@escuelaing.edu.co');

INSERT INTO CoordinadorCancelaciones VALUES (2138645,'Joseph Robert Voelkl Peñaloza','joseph.voelkl@escuelaing.edu.co');

-- Poblar Acudiente 
INSERT INTO Acudiente VALUES ('Yolanda Marulanda Izasa','yolanda@gmail.com');
INSERT INTO Acudiente VALUES ('Judith Ramirez Gonzalez','judith@gmail.com');
INSERT INTO Acudiente VALUES ('Amanda Garcia Rincon','amandaGarcia89@gmail.com');



-- Poblar Estudiante
INSERT INTO Estudiante VALUES (2121465, 'Diana Sanchez', '[
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"
        },
        {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [42],
            "estado":"A"   
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "MMIN",
            "nombre": "Modelos matemáticos para informática",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [41],
            "estado":"A"   
        },
        {
            "nemonico": "ALLI",
            "nombre": "Álgebra Lineal",
            "creditos": 3,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [38],
            "estado":"A"   
        },
        {
            "nemonico": "IINS",
            "nombre": "Introducción a la ingeniería de sistemas",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "CIED",
            "nombre": "Cálculo Integral y Ecuaciones Diferenciales",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [39],
            "estado":"A"   
        },
        {
            "nemonico": "LCAL",
            "nombre": "Lógica Calculativa",
            "creditos": 3,
            "preReq": ["MMIN"],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"   
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [-1,38],
            "estado":"A"   
        },
        {
            "nemonico": "PIMB",
            "nombre": "Programación Imperativa Básica",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [46],
            "estado":"A"   
        },
        {
            "nemonico": "MDIS",
            "nombre": "Matemáticas Discretas",
            "creditos": 3,
            "preReq": ["LCAL"],
            "coReq": [],
            "historialNotas": [38],
            "estado":"A"   
        },
        {
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [28,42],
            "estado":"A"   
        },
        {
            "nemonico": "MBDA",
            "nombre": "Modelos y Bases de Datos",
            "creditos": 4,
            "preReq": ["LCAL","PIMB"],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "PIMO",
            "nombre": "Programación Imperativa Modular",
            "creditos": 4,
            "preReq": ["MMIN","PIMB"],
            "coReq": [],
            "historialNotas": [-1,41],
            "estado":"A"   
        },
        {
            "nemonico": "PDIS",
            "nombre": "Probabilidad Discreta",
            "creditos": 3,
            "preReq": ["CIED"],
            "coReq": [],
            "historialNotas": [-1],
            "estado":"P"   
        },
        {
            "nemonico": "ARQC",
            "nombre": "Arquitectura del computador",
            "creditos": 3,
            "preReq": ["FIEM"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        },
        {
            "nemonico": "POOB",
            "nombre": "Programación Orientada a Objetos",
            "creditos": 4,
            "preReq": ["PIMO"],
            "coReq": [],
            "historialNotas": [36],
            "estado":"A"   
        },
        {
            "nemonico": "TSOR",
            "nombre": "Teoría de Sistemas y Organizaciones",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [44],
            "estado":"A"   
        },
        {
            "nemonico": "FUNE",
            "nombre": "Fundamentos de Economía",
            "creditos": 3,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [45],
            "estado":"A"   
        },
        {
            "nemonico": "ACFI",
            "nombre": "Análisis Contable y Financiero",
            "creditos": 3,
            "preReq": ["FUNE"],
            "coReq": [],
            "historialNotas": [43],
            "estado":"A"   
        },
        {
            "nemonico": "CRIP",
            "nombre": "Colombia: Realidad e Instituciones Políticas",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [43],
            "estado":"A"   
        },
        {
            "nemonico": "TCOM",
            "nombre": "Teoría de la computación",
            "creditos": 3,
            "preReq": ["MDIS","POOB"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        },
        {
            "nemonico": "PRON",
            "nombre": "Procesos de Negocio",
            "creditos": 3,
            "preReq": ["TSOR"],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "PDSW",
            "nombre": "Proceso de Desarrollo de Software",
            "creditos": 4,
            "preReq": ["POOB","MBDA"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        }
]', 13, 6, 'diana.sanchez-m@mail.escuelaing.edu.co', 313131,428131,'Ingeniería de sistemas', 'yolanda@gmail.com'); 

INSERT INTO Estudiante VALUES (2109734, 'Daniela Gonzalez Ramirez', '[
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [28,44],
            "estado":"A"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"
        },
        {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [34],
            "estado":"A"   
        },
        {
            "nemonico": "MMIN",
            "nombre": "Modelos matemáticos para informática",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"   
        },
        {
            "nemonico": "ALLI",
            "nombre": "Álgebra Lineal",
            "creditos": 3,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [31],
            "estado":"A"   
        },
        {
            "nemonico": "IINS",
            "nombre": "Introducción a la ingeniería de sistemas",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"   
        },
        {
            "nemonico": "CIED",
            "nombre": "Cálculo Integral y Ecuaciones Diferenciales",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [35],
            "estado":"A"   
        },
        {
            "nemonico": "LCAL",
            "nombre": "Lógica Calculativa",
            "creditos": 3,
            "preReq": ["MMIN"],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"   
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [36],
            "estado":"A"   
        },
        {
            "nemonico": "PIMB",
            "nombre": "Programación Imperativa Básica",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [-1,46],
            "estado":"A"   
        },
        {
            "nemonico": "MDIS",
            "nombre": "Matemáticas Discretas",
            "creditos": 3,
            "preReq": ["LCAL"],
            "coReq": [],
            "historialNotas": [32],
            "estado":"A"   
        },
        {
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [34],
            "estado":"A"   
        },
        {
            "nemonico": "MBDA",
            "nombre": "Modelos y Bases de Datos",
            "creditos": 4,
            "preReq": ["LCAL","PIMB"],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"   
        },
        {
            "nemonico": "PIMO",
            "nombre": "Programación Imperativa Modular",
            "creditos": 4,
            "preReq": ["MMIN","PIMB"],
            "coReq": [],
            "historialNotas": [31],
            "estado":"A"   
        },
        {
            "nemonico": "PDIS",
            "nombre": "Probabilidad Discreta",
            "creditos": 3,
            "preReq": ["CIED"],
            "coReq": [],
            "historialNotas": [33],
            "estado":"A"   
        },
        {
            "nemonico": "ARQC",
            "nombre": "Arquitectura del computador",
            "creditos": 3,
            "preReq": ["FIEM"],
            "coReq": [],
            "historialNotas": [41],
            "estado":"A"   
        },
        {
            "nemonico": "POOB",
            "nombre": "Programación Orientada a Objetos",
            "creditos": 4,
            "preReq": ["PIMO"],
            "coReq": [],
            "historialNotas": [36],
            "estado":"A"   
        },
        {
            "nemonico": "TSOR",
            "nombre": "Teoría de Sistemas y Organizaciones",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "estado":"A"   
        },
        {
            "nemonico": "FUNE",
            "nombre": "Fundamentos de Economía",
            "creditos": 3,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"   
        },
        {
            "nemonico": "ACFI",
            "nombre": "Análisis Contable y Financiero",
            "creditos": 3,
            "preReq": ["FUNE"],
            "coReq": [],
            "historialNotas": [43],
            "estado":"A"   
        },
        {
            "nemonico": "CRIP",
            "nombre": "Colombia: Realidad e Instituciones Políticas",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"   
        },
        {
            "nemonico": "ESTI",
            "nombre": "Estadística",
            "creditos": 3,
            "preReq": ["PDIS"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        },
        {
            "nemonico": "TCOM",
            "nombre": "Teoría de la computación",
            "creditos": 3,
            "preReq": ["MDIS","POOB"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        },
        {
            "nemonico": "PRON",
            "nombre": "Procesos de Negocio",
            "creditos": 3,
            "preReq": ["TSOR"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"   
        },
        {
            "nemonico": "PDSW",
            "nombre": "Proceso de Desarrollo de Software",
            "creditos": 4,
            "preReq": ["POOB","MBDA"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"   
        }
]',13, 7, 'daniela.gonzalez-ra@mail.escuelaing.edu.co', 231831,428131,'Ingeniería de sistemas','judith@gmail.com'); 

INSERT INTO Estudiante VALUES (2245696, 'Daniel Medina Lozano', '[
       {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"
    	},
    	{
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [49],
            "estado":"A"
    	},
	    {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "estado":"A"
    	},
	    {
            "nemonico": "FQUI",
            "nombre": "",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"
        },
 	    {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [37],
            "estado":"A"
        },
    	{
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [45],
            "estado":"A"
    	},
	    {
            "nemonico": "ALLI",
            "nombre": "Algebra lineal",
            "creditos": 3,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [36],
            "estado":"A"
        },
	    {
            "nemonico": "BIOL",
            "nombre": "Biología",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [38],
            "estado":"A"
        },
	    {
            "nemonico": "EGR1",
            "nombre": "Expresión Gráfica 1",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "estado":"A"
        },
        {
            "nemonico": "IINI",
            "nombre": "Introducción a la ingeniería industrial",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "historialNotas": [50],
            "estado":"A"
        },
   	    {
            "nemonico": "CALI",
            "nombre": "Cálculo Integral",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [46],
            "estado":"A"
    	},
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [45],
            "estado":"A"
    	},
	    {
            "nemonico": "QUIM",
            "nombre": "Química",
            "creditos": 3,
            "preReq": ["FQUI"],
            "coReq": [],
            "historialNotas": [39],
            "estado":"A"
        },
	    {
            "nemonico": "EGR2",
            "nombre": "Expresión Gráfica 2",
            "creditos": 3,
            "preReq": ["EGR1"],
            "coReq": [],
            "historialNotas": [40],
            "estado":"A"
        },
	    {
            "nemonico": "CALV",
            "nombre": "Cálculo vectorial",
            "creditos": 4,
            "preReq": ["ALLI","CALI"],
            "coReq": [],
            "historialNotas": [47],
            "estado":"A"
        },
    	{
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CALI"],
            "historialNotas": [34],
            "estado":"A"   
        },
        {
            "nemonico": "MSOL",
            "nombre": "Mecánica de sólidos",
            "creditos": 4,
            "preReq": ["FIMF","CALD"],
            "coReq": [],
            "historialNotas": [35],
            "estado":"A"   
        },
	    {
            "nemonico": "HYSE",
            "nombre": "Higiene y seguridad",
            "creditos": 2,
            "preReq": ["IINI"],
            "coReq": [],
            "historialNotas": [34],
            "estado":"A"
        },
	    {
            "nemonico": "ECDI",
            "nombre": "Ecuaciones diferenciales",
            "creditos": 3,
            "preReq": ["CALV"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"
        },
	    {
            "nemonico": "FICO",
            "nombre": "Física de calor, ondas y estructura atomica",
            "creditos": 4,
            "preReq": ["FIEM"],
            "coReq": [],
            "historialNotas": [38],
            "estado":"A"
        },
	    {
            "nemonico": "MTRL",
            "nombre": "Materiales",
            "creditos": 3,
            "preReq": ["FIMF","QUIM"],
            "coReq": ["LMTR"],
            "historialNotas": [],
            "estado":"V"
        },
	    {
            "nemonico": "LMTR",
            "nombre": "Laboratorio de materiales",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"
        },
	    {
            "nemonico": "SPPC",
            "nombre": "Soluciones de problemas por computador",
            "creditos": 3,
            "preReq": ["ALLI","CALD"],
            "coReq": [],
            "historialNotas": [-1],
            "estado":"P"
        },
	    {
            "nemonico": "ERGO",
            "nombre": "Ergonomia",
            "creditos": 2,
            "preReq": ["HYSE"],
            "coReq": ["LCTR"],
            "historialNotas": [35],
            "estado":"A"
        },
	    {
            "nemonico": "LCTR",
            "nombre": "Laboratorio de condiciones de trabajo",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [31],
            "estado":"A"
        },
	    {
            "nemonico": "FUNE",
            "nombre": "Fundamentos de economía",
            "creditos": 3,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"V"
        }
]',7, 5, 'daniel.medina-l@mail.escuelaing.edu.co', 1675926,2138645,'Ingeniería industrial','amandaGarcia89@gmail.com'); 


-- Poblar Course
INSERT INTO Asignatura VALUES ('PREM','Precálculo');
INSERT INTO Asignatura VALUES ('AGEO','Análisis Geométrico');
INSERT INTO Asignatura VALUES ('CALD','Cálculo Diferencial');
INSERT INTO Asignatura VALUES ('CIED','Cálculo Integral y Ecuaciones Diferenciales');
INSERT INTO Asignatura VALUES ('FFIS','Fundamentos de Física');
INSERT INTO Asignatura VALUES ('FIMF','Física Mecánica y de Fluidos');
INSERT INTO Asignatura VALUES ('FIEM','Física del Electromagnetismo');
INSERT INTO Asignatura VALUES ('EXOE','Expresión oral y escrita');
INSERT INTO Asignatura VALUES ('MMIN','Modelos matemáticos para informática');
INSERT INTO Asignatura VALUES ('ALLI','Álgebra lineal');
INSERT INTO Asignatura VALUES ('IINS','Introducción a la ingeniería de sistemas');
INSERT INTO Asignatura VALUES ('LCAL','Lógica Calculativa');
INSERT INTO Asignatura VALUES ('PIMB','Programación Imperativa Básica');
INSERT INTO Asignatura VALUES ('MDIS','Matemáticas Discretas');
INSERT INTO Asignatura VALUES ('MBDA','Modelos y Bases de Datos');
INSERT INTO Asignatura VALUES ('PIMO','Programación Imperativa Modular');
INSERT INTO Asignatura VALUES ('PDIS','Probabilidad Discreta');
INSERT INTO Asignatura VALUES ('ARQC','Arquitectura del computador');
INSERT INTO Asignatura VALUES ('POOB','Programación Orientada a Objetos');
INSERT INTO Asignatura VALUES ('TSOR','Teoría de Sistemas y Organizaciones');
INSERT INTO Asignatura VALUES ('FUNE','Fundamentos de Economía');
INSERT INTO Asignatura VALUES ('ACFI','Análisis Contable y Financiero');
INSERT INTO Asignatura VALUES ('CRIP','Colombia: Realidad e Instituciones Políticas');
INSERT INTO Asignatura VALUES ('ESTI','Estadística');
INSERT INTO Asignatura VALUES ('TCOM','Teoría de la computación');
INSERT INTO Asignatura VALUES ('PRON','Procesos de Negocio');
INSERT INTO Asignatura VALUES ('PDSW','Proceso de Desarrollo de Software');
INSERT INTO Asignatura VALUES ('TPRO','Teoría de la Programación');
INSERT INTO Asignatura VALUES ('FRED','Fundamentos de Redes');
INSERT INTO Asignatura VALUES ('SEGI','Seguridad Informática');
INSERT INTO Asignatura VALUES ('SOPC','Sistemas Operativos y Plataformas Computacionales');
INSERT INTO Asignatura VALUES ('ARSW','Arquitecturas de Software');
INSERT INTO Asignatura VALUES ('AREM','Arquitecturas Empresariales');
INSERT INTO Asignatura VALUES ('COSW','Construcción de Software');
INSERT INTO Asignatura VALUES ('FGPR','Fundamentos y Gerencia de Proyectos');
INSERT INTO Asignatura VALUES ('SOSW','Soluciones de Software');
INSERT INTO Asignatura VALUES ('PGR1','Proyecto de Grado 1');
INSERT INTO Asignatura VALUES ('PGR2','Proyecto de Grado 2');
INSERT INTO Asignatura VALUES ('FQUI','Fundamentos de química');
INSERT INTO Asignatura VALUES ('BIOL','Biología');
INSERT INTO Asignatura VALUES ('EGR1','Expresión Gráfica 1');
INSERT INTO Asignatura VALUES ('EGR2','Expresión Gráfica 2');
INSERT INTO Asignatura VALUES ('IINI','Introducción a la ingeniería industrial');
INSERT INTO Asignatura VALUES ('QUIM','Química');
INSERT INTO Asignatura VALUES ('CALI','Cálculo Integral');
INSERT INTO Asignatura VALUES ('CALV','Cálculo Vectorial');
INSERT INTO Asignatura VALUES ('ECDI','Ecuaciones Diferenciales');
INSERT INTO Asignatura VALUES ('PRBA','Probabilidad');
INSERT INTO Asignatura VALUES ('FICO','Física de Calor, Ondas y Estr. Atómica');
INSERT INTO Asignatura VALUES ('MSOL','Mecánica de sólidos');
INSERT INTO Asignatura VALUES ('HYSE','Higiene y Seguridad');
INSERT INTO Asignatura VALUES ('MTRL','Materiales');
INSERT INTO Asignatura VALUES ('SPPC','Solución de Problemas por Computador');
INSERT INTO Asignatura VALUES ('TMDN','Termodinámica');
INSERT INTO Asignatura VALUES ('LMTR','Laboratorio de Materiales');
INSERT INTO Asignatura VALUES ('ERGO','Ergonomía');
INSERT INTO Asignatura VALUES ('LCTR','Laboratorio Condiciones de Trabajo');
INSERT INTO Asignatura VALUES ('PIND','Procesos Industriales');
INSERT INTO Asignatura VALUES ('LPIN','Laboratorio de Procesos Industriales');
INSERT INTO Asignatura VALUES ('MMAT','Modelamiento matemático');
INSERT INTO Asignatura VALUES ('ELET','Electrotecnia');
INSERT INTO Asignatura VALUES ('LETC','Laboratorio de electrotecnia');
INSERT INTO Asignatura VALUES ('DPRD','Desarrollo del producto');
INSERT INTO Asignatura VALUES ('LDPR','Laboratorio de desarrollo del producto');
INSERT INTO Asignatura VALUES ('ETTR','Estudio del trabajo');
INSERT INTO Asignatura VALUES ('GORG','Gestión organizacional');
INSERT INTO Asignatura VALUES ('CMAN','Costos de manufactura');
INSERT INTO Asignatura VALUES ('FEPR','Formulación y evaluación de proyectos');
INSERT INTO Asignatura VALUES ('GCHU','Gestión del capital humano');
INSERT INTO Asignatura VALUES ('CLDD','Calidad');
INSERT INTO Asignatura VALUES ('DPIN','Diseño de plantas');
INSERT INTO Asignatura VALUES ('PCPI','Planeación y control, producciones e inventarios');
INSERT INTO Asignatura VALUES ('GCAB','Gestión de la cadena de abastecimiento');
INSERT INTO Asignatura VALUES ('PMLI','Producción más limpia');
INSERT INTO Asignatura VALUES ('NEGO','Negociación');


-- Asignaturas Plan de Estudios Sistemas 11
INSERT INTO Asignatura VALUES ('FISF','Física fundamental');
INSERT INTO Asignatura VALUES ('PREC','Precálculo');
INSERT INTO Asignatura VALUES ('ANGE','Análisis geométrico');
INSERT INTO Asignatura VALUES ('CAL1','Cálculo 1');
INSERT INTO Asignatura VALUES ('FIS1','Física 1');
INSERT INTO Asignatura VALUES ('CAL2','Cálculo 2');
INSERT INTO Asignatura VALUES ('PC01','Programación de computadores 1');
INSERT INTO Asignatura VALUES ('TGSI','Teoría general de sistemas');
INSERT INTO Asignatura VALUES ('FIS2','Física 2');
INSERT INTO Asignatura VALUES ('CAL3','Cálculo 3');
INSERT INTO Asignatura VALUES ('MDC1','Matemáticas discretas 1');
INSERT INTO Asignatura VALUES ('PC02','Programación de computadores 2');
INSERT INTO Asignatura VALUES ('FIS3','Física 3');
INSERT INTO Asignatura VALUES ('EDIF','Ecuaciones diferenciales');
INSERT INTO Asignatura VALUES ('MDC2','Matemáticas discretas 2');
INSERT INTO Asignatura VALUES ('PROB','Probabilidad');
INSERT INTO Asignatura VALUES ('TYPO','Teorías y procesos organizacionales');
INSERT INTO Asignatura VALUES ('PC03','Programación de computadores 3');
INSERT INTO Asignatura VALUES ('TCO1','Teoría de la computación 1');
INSERT INTO Asignatura VALUES ('ESTD','Estadística');
INSERT INTO Asignatura VALUES ('SDYM','Sistemas digitales y microprocesadores');
INSERT INTO Asignatura VALUES ('POB1','Programación orientada a objetos 1');
INSERT INTO Asignatura VALUES ('BDAT','Bases de datos');
INSERT INTO Asignatura VALUES ('MIC1','Microeconomía 1');
INSERT INTO Asignatura VALUES ('IOP1','Investigación de operaciones 1');
INSERT INTO Asignatura VALUES ('TCO2','Teoría de la computación 2');
INSERT INTO Asignatura VALUES ('ISW1','Ingeniería de software 1');
INSERT INTO Asignatura VALUES ('CFIN','Contabilidad financiera');
INSERT INTO Asignatura VALUES ('PLYF','Programación lógica y funcional');
INSERT INTO Asignatura VALUES ('RDC1','Redes de computadores 1');
INSERT INTO Asignatura VALUES ('SIOP','Sistemas operativos');
INSERT INTO Asignatura VALUES ('ISW2','Ingeniería de software 2');
INSERT INTO Asignatura VALUES ('APRO','Análisis de Proyectos');
INSERT INTO Asignatura VALUES ('RDC2','Redes de computadores 2');
INSERT INTO Asignatura VALUES ('ISW3','Ingeniería de software 3');
INSERT INTO Asignatura VALUES ('FYEP','Formulación y evaluación de proyectos');
INSERT INTO Asignatura VALUES ('IART','Inteligencia Artificial');
INSERT INTO Asignatura VALUES ('SACT','Seminario de actualización');
INSERT INTO Asignatura VALUES ('SYPI','Seguridad y privacidad de la información');
INSERT INTO Asignatura VALUES ('SEMI','Seminarios');

-- Poblar Solicitud--
INSERT INTO Solicitud VALUES (1,'2017-10-26 10:23:54','Me consume mucho tiempo y estoy descuidando las otras materias', 'Considero que el estudiante se puede esforzar y sacar la materia adelante','Tramitada',true,true,2245696,true,'SPPC');
INSERT INTO Solicitud VALUES (2,'2017-10-04 17:40:34','Tengo muy bajita la nota y no me quiero arriesgar a perderla', 'Las notas del estudiante son muy bajas y su carga academica es alta, lo cual no le permite subir la nota en tercer corte','Aceptada',null,true,2121465,false,'PDIS');
INSERT INTO Solicitud VALUES (3,'2017-10-29 13:24:24','No le entiendo al profesor',null,'No tramitada',null,false,2109734,false,'PRON'); 


 
-- Poblar PlanDeEstudios
INSERT INTO PlanDeEstudios VALUES (7,'Ingeniería industrial','{
    "programa": "Ingeniería industrial",
    "version": 7,       
    "courses": [
    	{
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
    	},
    	{
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
    	},
	    {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
    	},
	    {
            "nemonico": "FQUI",
            "nombre": "",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
 	    {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
    	},
	    {
            "nemonico": "ALLI",
            "nombre": "Algebra lineal",
            "creditos": 3,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "BIOL",
            "nombre": "Biología",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "EGR1",
            "nombre": "Expresión Gráfica 1",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
 	    {
           "nemonico": "IINI",
            "nombre": "Introducción a la ingeniería industrial",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
       	{
            "nemonico": "CALI",
            "nombre": "Cálculo Integral",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
    	},
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "estado":"P"
    	},
    	{
            "nemonico": "QUIM",
            "nombre": "Química",
            "creditos": 3,
            "preReq": ["FQUI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "EGR2",
            "nombre": "Expresión Gráfica 2",
            "creditos": 3,
            "preReq": ["EGR1"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "CALV",
            "nombre": "Cálculo vectorial",
            "creditos": 4,
            "preReq": ["ALLI","CALI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
      	{
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CALI"],
            "historialNotas": [],
            "estado":"P"
    	},
	    {
            "nemonico": "MSOL",
            "nombre": "Mecánica de sólidos",
            "creditos": 4,
            "preReq": ["FIMF","CALD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "HYSE",
            "nombre": "Higiene y seguridad",
            "creditos": 2,
            "preReq": ["IINI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "ECDI",
            "nombre": "Ecuaciones diferenciales",
            "creditos": 3,
            "preReq": ["CALV"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "FICO",
            "nombre": "Física de calor, ondas y estructura atomica",
            "creditos": 4,
            "preReq": ["FIEM"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "MTRL",
            "nombre": "Materiales",
            "creditos": 3,
            "preReq": ["FIMF","QUIM"],
            "coReq": ["LMTR"],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "LMTR",
            "nombre": "Laboratorio de materiales",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "SPPC",
            "nombre": "Soluciones de problemas por computador",
            "creditos": 3,
            "preReq": ["ALLI","CALD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "ERGO",
            "nombre": "Ergonomia",
            "creditos": 2,
            "preReq": ["HYSE"],
            "coReq": ["LCTR"],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "LCTR",
            "nombre": "Laboratorio de condiciones de trabajo",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "PRBA",
            "nombre": "Probabilidad",
            "creditos": 3,
            "preReq": ["CALV"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "TMDN",
            "nombre": "Termodinámica",
            "creditos": 3,
            "preReq": ["ECDI","FICO"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
      	{
            "nemonico": "PIND",
            "nombre": "Procesos industriales",
            "creditos": 3,
            "preReq": ["EGR2","MTRL","LMTR","MSOL"],
            "coReq": ["LPIN"],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "LPIN",
            "nombre": "Laboratorio de procesos industriales",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "FUNE",
            "nombre": "Fundamentos de economía",
            "creditos": 3,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "ESTI",
            "nombre": "Estadística",
            "creditos": 3,
            "preReq": ["PRBA"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "MMAT",
            "nombre": "Modelamiento matemático",
            "creditos": 2,
            "preReq": ["SPPC","PRBA"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "ELET",
            "nombre": "Electrotecnia",
            "creditos": 3,
            "preReq": ["FIEM","ECDI"],
            "coReq": ["LETC"],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "LETC",
            "nombre": "Laboratorio de electrotecnia",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "DPRD",
            "nombre": "Desarrollo del producto",
            "creditos": 2,
            "preReq": ["PIND","LPIN"],
            "coReq": ["LDPR"],
            "historialNotas": [],
            "estado":"P"
        },
    	{
            "nemonico": "LDPR",
            "nombre": "Laboratorio desarrollo del producto",
            "creditos": 1,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "ACFI",
            "nombre": "Análisis contable y financiero",
            "creditos": 3,
            "preReq": ["FUNE"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "OPDO",
            "nombre": "Optimización de operaciones",
            "creditos": 3,
            "preReq": ["MMAT"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "ETTR",
            "nombre": "Estudio del trabajo",
            "creditos": 3,
            "preReq": ["ESTI","LDPR","DPRD"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "GORG",
            "nombre": "Gestión organizacional",
            "creditos": 3,
            "preReq": ["IINI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "CMAN",
            "nombre": "Costos de manufactura",
            "creditos": 3,
            "preReq": ["ACFI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "MOES",
            "nombre": "Modelos estocásticos",
            "creditos": 3,
            "preReq": ["MMAT"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "PCPI",
            "nombre": "Planeación y control producción e inventarios",
            "creditos": 3,
            "preReq": ["ETTR","MMAT"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "DPIN",
            "nombre": "Diseño de plantas",
            "creditos": 3,
            "preReq": ["TMDN","ELET","ETTR"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "CLDD",
            "nombre": "Calidad",
            "creditos": 3,
            "preReq": ["PIND","ESTI"],
            "coReq": ["ETTR"],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "GCHU",
            "nombre": "Gestión del capital humano",
            "creditos": 3,
            "preReq": ["GORG","ESTI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "FEPR",
            "nombre": "Formulación y evaluación de proyectos",
            "creditos": 3,
            "preReq": ["ESTI","ACFI","GORG"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "GCAB",
            "nombre": "Gestión de la cadena de abastecimiento",
            "creditos": 3,
            "preReq": ["PCPI"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "PMLI",
            "nombre": "Producción más limpia",
            "creditos": 3,
            "preReq": ["BIOL","CMAN"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "CRIP",
            "nombre": "Colombia, realidad e instituciones políticas",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        },
	    {
            "nemonico": "NEGO",
            "nombre": "Negociación",
            "creditos": 3,
            "preReq": ["FEPR"],
            "coReq": [],
            "historialNotas": [],
            "estado":"P"
        }
    ]
}',149);

INSERT INTO PlanDeEstudios VALUES (13,'Ingeniería de sistemas','{
    "programa": "Ingeniería de sistemas",
    "version": 13,        
    "courses": [
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "MMIN",
            "nombre": "Modelos matemáticos para informática",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "ALLI",
            "nombre": "Álgebra Lineal",
            "creditos": 3,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "IINS",
            "nombre": "Introducción a la ingeniería de sistemas",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "CIED",
            "nombre": "Cálculo Integral y Ecuaciones Diferenciales",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "LCAL",
            "nombre": "Lógica Calculativa",
            "creditos": 3,
            "preReq": ["MMIN"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "estado":"P"
        },
        {
            "nemonico": "PIMB",
            "nombre": "Programación Imperativa Básica",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "MDIS",
            "nombre": "Matemáticas Discretas",
            "creditos": 3,
            "preReq": ["LCAL"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "estado":"P"
        },
        {
            "nemonico": "MBDA",
            "nombre": "Modelos y Bases de Datos",
            "creditos": 4,
            "preReq": ["LCAL","PIMB"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PIMO",
            "nombre": "Programación Imperativa Modular",
            "creditos": 4,
            "preReq": ["MMIN","PIMB"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PDIS",
            "nombre": "Probabilidad Discreta",
            "creditos": 3,
            "preReq": ["CIED"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "TPRO",
            "nombre": "Teoría de la Programación",
            "creditos": 3,
            "preReq": ["MDIS","PIMO"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "ARQC",
            "nombre": "Arquitectura del computador",
            "creditos": 3,
            "preReq": ["FIEM"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "POOB",
            "nombre": "Programación Orientada a Objetos",
            "creditos": 4,
            "preReq": ["PIMO"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "ESTI",
            "nombre": "Estadística",
            "creditos": 3,
            "preReq": ["PDIS"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "TCOM",
            "nombre": "Teoría de la computación",
            "creditos": 3,
            "preReq": ["MDIS","POOB"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "FRED",
            "nombre": "Fundamentos de Redes",
            "creditos": 3,
            "preReq": ["ARQC"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "TSOR",
            "nombre": "Teoría de Sistemas y Organizaciones",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PDSW",
            "nombre": "Proceso de Desarrollo de Software",
            "creditos": 4,
            "preReq": ["PIMO","MBDA"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "FUNE",
            "nombre": "Fundamentos de Economía",
            "creditos": 3,
            "preReq": ["CALD"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "SEGI",
            "nombre": "Seguridad Informática",
            "creditos": 3,
            "preReq": ["FRED"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "SOPC",
            "nombre": "Sistemas Operativos y Plataformas Computacionales",
            "creditos": 3,
            "preReq": ["ARQC"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PRON",
            "nombre": "Procesos de Negocio",
            "creditos": 3,
            "preReq": ["TSOR"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "ARSW",
            "nombre": "Arquitecturas de Software",
            "creditos": 4,
            "preReq": ["FRED","PDSW"],
            "coReq": [],
            "estado":"P"   
        },
      {
            "nemonico": "ACFI",
            "nombre": "Análisis Contable y Financiero",
            "creditos": 3,
            "preReq": ["FUNE"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "AREM",
            "nombre": "Arquitecturas Empresariales",
            "creditos": 3,
            "preReq": ["PRON"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "COSW",
            "nombre": "Construcción de Software",
            "creditos": 4,
            "preReq": ["ARSW","PRON"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "FGPR",
            "nombre": "Fundamentos y Gerencia de Proyectos",
            "creditos": 3,
            "preReq": ["ACFI","PDIS"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "SOSW",
            "nombre": "Soluciones de Software",
            "creditos": 4,
            "preReq": ["ARSW","AREM"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PGR1",
            "nombre": "Proyecto de Grado 1",
            "creditos": 4,
            "preReq": ["FGPR"],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "CRIP",
            "nombre": "Colombia: Realidad e Instituciones Políticas",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"   
        },
        {
            "nemonico": "PGR2",
            "nombre": "Proyecto de Grado 2",
            "creditos": 4,
            "preReq": ["PGR1"],
            "coReq": [],
            "estado":"P"   
        }
    ]
}',129);

INSERT INTO PlanDeEstudios VALUES (11,'Ingeniería de sistemas','{
    "programa": "Ingeniería de sistemas",
    "version": 11,        
    "courses": [
        {
            "nemonico": "FISF",
            "nombre": "Física fundamental",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PREC",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ANGE",
            "nombre": "Análisis geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "EXOE",
            "nombre": "Expresión oral y escrita",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "CAL1",
            "nombre": "Cálculo 1",
            "creditos": 4,
            "preReq": ["PREC","ANGE"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ALLI",
            "nombre": "Álgebra lineal",
            "creditos": 3,
            "preReq": ["PREC","ANGE"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "IINS",
            "nombre": "Introducción a la ingeniería de sistemas",
            "creditos": 2,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "MMIN",
            "nombre": "Modelos matemáticos para informática",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "FIS1",
            "nombre": "Física 1",
            "creditos": 4,
            "preReq": ["FISF"],
            "coReq": ["CAL1"],
            "estado":"P"
        },
        {
            "nemonico": "CAL2",
            "nombre": "Cálculo 2",
            "creditos": 4,
            "preReq": ["CAL1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PC01",
            "nombre": "Programación de computadores 1",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "TGSI",
            "nombre": "Teoría general de sistemas",
            "creditos": 3,
            "preReq": ["MMIN"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "FIS2",
            "nombre": "Física 2",
            "creditos": 4,
            "preReq": ["FIS1"],
            "coReq": ["CAL2"],
            "estado":"P"
        },
        {
            "nemonico": "CAL3",
            "nombre": "Cálculo 3",
            "creditos": 4,
            "preReq": ["CAL2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "MDC1",
            "nombre": "Matemáticas discretas 1",
            "creditos": 3,
            "preReq": ["TGSI"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PC02",
            "nombre": "Programación de computadores 2",
            "creditos": 4,
            "preReq": ["PC01"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "FIS3",
            "nombre": "Física 3",
            "creditos": 4,
            "preReq": ["FIS2"],
            "coReq": ["CAL3"],
            "estado":"P"
        },
        {
            "nemonico": "EDIF",
            "nombre": "Ecuaciones diferenciales",
            "creditos": 3,
            "preReq": ["CAL3"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "MDC2",
            "nombre": "Matemáticas discretas 2",
            "creditos": 3,
            "preReq": ["MDC1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PROB",
            "nombre": "Probabilidad",
            "creditos": 3,
            "preReq": ["CAL3"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "TYPO",
            "nombre": "Teorías y procesos organizacionales",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PC03",
            "nombre": "Programación de computadores 3",
            "creditos": 4,
            "preReq": ["PC02"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "TCO1",
            "nombre": "Teoría de la computación 1",
            "creditos": 3,
            "preReq": ["MDC2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ESTD",
            "nombre": "Estadística",
            "creditos": 3,
            "preReq": ["PROB"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "SDYM",
            "nombre": "Sistemas digitales y microprocesadores",
            "creditos": 3,
            "preReq": [],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "POB1",
            "nombre": "Programación orientada a objetos 1",
            "creditos": 4,
            "preReq": ["PC03"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "BDAT",
            "nombre": "Bases de datos",
            "creditos": 4,
            "preReq": ["PC03"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "MIC1",
            "nombre": "Microeconomía 1",
            "creditos": 3,
            "preReq": ["CAL1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "IOP1",
            "nombre": "Investigación de operaciones 1",
            "creditos": 3,
            "preReq": ["PROB"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "TCO2",
            "nombre": "Teoría de la computación 2",
            "creditos": 3,
            "preReq": ["TCO1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ARQC",
            "nombre": "Arquitectura del computador",
            "creditos": 3,
            "preReq": ["SDYM"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "POB2",
            "nombre": "Programación orientada a objetos 2",
            "creditos": 4,
            "preReq": ["POB1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ISW1",
            "nombre": "Ingeniería de software 1",
            "creditos": 4,
            "preReq": ["BDAT","POB2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "CFIN",
            "nombre": "Contabilidad financiera",
            "creditos": 3,
            "preReq": ["MIC1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PLYF",
            "nombre": "Programación lógica y funcional",
            "creditos": 3,
            "preReq": ["TCO2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "RDC1",
            "nombre": "Redes de computadores 1",
            "creditos": 3,
            "preReq": ["ARQC"],
            "coReq": [],
            "estado":"P"
        }, 
        {
            "nemonico": "SIOP",
            "nombre": "Sistemas operativos",
            "creditos": 3,
            "preReq": ["ARQC"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ISW2",
            "nombre": "Ingeniería de software 2",
            "creditos": 4,
            "preReq": ["ISW1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "APRO",
            "nombre": "Análisis de Proyectos",
            "creditos": 3,
            "preReq": ["CFIN"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PGR1",
            "nombre": "Proyecto de Grado 1",
            "creditos": 4,
            "preReq": ["FYEP"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "RDC2",
            "nombre": "Redes de computadores 2",
            "creditos": 3,
            "preReq": ["RDC1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "ISW3",
            "nombre": "Ingeniería de software 3",
            "creditos": 3,
            "preReq": ["ISW2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "FYEP",
            "nombre": "Formulación y evaluación de proyectos",
            "creditos": 3,
            "preReq": ["APRO"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "PGR2",
            "nombre": "Proyecto de Grado 2",
            "creditos": 4,
            "preReq": ["PGR1"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "IART",
            "nombre": "Inteligencia Artificial",
            "creditos": 4,
            "preReq": ["ISW3"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "SACT",
            "nombre": "Seminario de actualización",
            "creditos": 3,
            "preReq": ["SIOP"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "SYPI",
            "nombre": "Seguridad y privacidad de la información",
            "creditos": 3,
            "preReq": ["RDC2"],
            "coReq": [],
            "estado":"P"
        },
        {
            "nemonico": "SEMI",
            "nombre": "Seminarios",
            "creditos": 4,
            "preReq": ["ISW3","RDC2"],
            "coReq": [],
            "estado":"P"
        }
    ]
}',163);




