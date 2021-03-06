-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-11-15 03:11:38.911

-- tables
-- Table: Acudiente
CREATE TABLE Acudiente (
    nombre varchar(20)  NOT NULL,
    correo varchar(50)  NOT NULL,
    CONSTRAINT correoAcudiente UNIQUE (correo),
    CONSTRAINT Acudiente_pk PRIMARY KEY (correo)
);

-- Table: ConsejeroAcademico
CREATE TABLE ConsejeroAcademico (
    codigo int  NOT NULL,
    nombre varchar(20)  NOT NULL,
    correo varchar(50)  NOT NULL,
    CONSTRAINT correoConsejero UNIQUE (correo),
    CONSTRAINT ConsejeroAcademico_pk PRIMARY KEY (codigo)
);

-- Table: CoordinadorCancelaciones
CREATE TABLE CoordinadorCancelaciones (
    codigo int  NOT NULL,
    nombre varchar(20)  NOT NULL,
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
    contenido varchar(10000)  NOT NULL,
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
INSERT INTO ProgramaAcademico VALUES ('Ingenieria de sistemas','Escuela Colombiana de Ingeniería Julio Garavito');

--Poblar Consejero academico 
INSERT INTO ConsejeroAcademico VALUES (231831,'Claudia Patricia','claudia.patricia@escuelaing.edu.co');
INSERT INTO ConsejeroAcademico VALUES (313131,'Camilo Cadavid','camilo.cadavid@escuelaing.edu.co');

-- Poblar Coordinador de Cancelaciones
INSERT INTO CoordinadorCancelaciones VALUES (428131,'Oswaldo Navetty','oswaldo.navetty@escuelaing.edu.co');


-- Poblar Acudiente 
INSERT INTO Acudiente VALUES ('Yolanda','yolanda@gmail.com');
INSERT INTO Acudiente VALUES ('Maria','maria@gmail.com');

-- Poblar Estudiante 
INSERT INTO Estudiante VALUES (79328, 'Juan David Giraldo Mancilla', '[
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [21],
            "estado":"V"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [32],
            "estado":"V"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [41],
            "estado":"P"
        }
]',13, 1, 'juan.giraldo-m@mail.escuelaing.edu.co', 231831,428131,'Ingenieria de sistemas','yolanda@gmail.com'); 
INSERT INTO Estudiante VALUES (173183, 'Pepito perez montenegro', '[
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "tercios": [21, 46, 40],
            "estado":"A"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [34],
            "tercios": [35,28,40],
            "estado":"A"
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [34],
            "estado":"P"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [50],
            "tercios": [50,50,50],
            "estado":"A"
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [-1],
            "tercios": [20],
            "estado":"P"
        }
]', 13, 2, 'pepito.perez@mail.escuelaing.edu.co', 231831,428131,'Ingenieria de sistemas', 'maria@gmail.com');
INSERT INTO Estudiante VALUES (2121465, 'Diana Sanchez', '[
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "tercios": [30,30,30],
            "estado":"A"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "tercios": [30,30,30],
            "estado":"A"
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [-1],
            "tercios": [15],
            "estado":"V"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "tercios": [35,45,30],
            "estado":"A"
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [45],
            "estado":"V"
        }
]', 13, 3, 'diana.sanchez-m@mail.escuelaing.edu.co', 313131,428131,'Ingenieria de sistemas', 'yolanda@gmail.com'); 

-- Poblar Course
INSERT INTO Asignatura VALUES ('PREM','Precálculo');
INSERT INTO Asignatura VALUES ('AGEO','Análisis Geométrico');
INSERT INTO Asignatura VALUES ('CALD','Cálculo Diferencial');
INSERT INTO Asignatura VALUES ('CIED','Cálculo Integral y Ecuaciones Diferenciales');
INSERT INTO Asignatura VALUES ('FFIS','Fundamentos de Física');
INSERT INTO Asignatura VALUES ('FIMF','Física Mecánica y de Fluidos');
INSERT INTO Asignatura VALUES ('FIEM','Física del Electromagnetismo');

-- Poblar Solicitud--
INSERT INTO Solicitud VALUES (1,'2017-10-26 10:23:54','Me consume mucho tiempo y estoy descuidando las otras materias','Considero que si se debe aceptar la cancelacion, debido a la justificacion del estudiante','Tramitada',null,true,79328,false,'FFIS');
INSERT INTO Solicitud VALUES (2,'2017-10-04 17:40:34','Tengo muy bajita la nota y no me quiero arriesgar a perderla',null,'No tramitada',null,true,173183,true,'CALD');
INSERT INTO Solicitud VALUES (3,'2017-10-29 13:24:24','No le entiendo al profesor','El estudiante no le entiende al profesor, por ende va mal en la materia y ya es imposible recuperar la materia','Aceptada',true,true,173183,true,'FIMF'); 

-- Poblar PlanDeEstudios
INSERT INTO PlanDeEstudios VALUES (13,'Ingenieria de sistemas','{
    "programa": "Ingenieria de sistemas",
    "version": 13,        
    "courses": [
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "CIED",
            "nombre": "Cálculo Integral y Ecuaciones Diferenciales",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}',28);

INSERT INTO PlanDeEstudios VALUES (8,'Ingenieria de sistemas','{
    "programa": "Ingenieria de sistemas",
    "version": 8,        
    "courses": [
        {
            "nemonico": "PREM",
            "nombre": "Precálculo",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "AGEO",
            "nombre": "Análisis Geométrico",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "CALD",
            "nombre": "Cálculo Diferencial",
            "creditos": 4,
            "preReq": ["PREM","AGEO"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "CIED",
            "nombre": "Cálculo Integral y Ecuaciones Diferenciales",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FFIS",
            "nombre": "Fundamentos de Física",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FIMF",
            "nombre": "Física Mecánica y de Fluidos",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nemonico": "FIEM",
            "nombre": "Física del Electromagnetismo",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}',28);
-- End of file.
