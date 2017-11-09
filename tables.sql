-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-11-09 16:08:38.891

-- tables
-- Table: Acudiente
CREATE TABLE Acudiente (
    nombre varchar(20)  NULL,
    correo varchar(50)  NOT NULL,
    CONSTRAINT Acudiente_pk PRIMARY KEY (correo)
);

-- Table: Asignatura
CREATE TABLE Asignatura (
    nemonico varchar(4)  NOT NULL,
    nombre varchar(100)  NULL,
    programa varchar(100)  NULL,
    cancelada int  NULL,
    CONSTRAINT Asignatura_pk PRIMARY KEY (nemonico)
);

-- Table: ConsejeroAcademico
CREATE TABLE ConsejeroAcademico (
    nombre varchar(20)  NULL,
    codigo int  NOT NULL,
    CONSTRAINT ConsejeroAcademico_pk PRIMARY KEY (codigo)
);

-- Table: CoordinadorCancelaciones
CREATE TABLE CoordinadorCancelaciones (
    codigo int  NOT NULL,
    nombre varchar(20)  NULL,
    CONSTRAINT CoordinadorCancelaciones_pk PRIMARY KEY (codigo)
);

-- Table: Estudiante
CREATE TABLE Estudiante (
    codigo int  NOT NULL,
    nombre varchar(100)  NULL,
    planDeEstudios varchar(10000)  NOT NULL,
    numeroMatriculas int  NULL,
    correo varchar(50)  NULL,
    ConsejeroAcademico_codigo int  NOT NULL,
    Acudiente_correo varchar(50)  NOT NULL,
    CoordinadorCancelaciones_codigo int  NOT NULL,
    programaAcademico varchar(100)  NOT NULL,
    CONSTRAINT Estudiante_pk PRIMARY KEY (codigo)
);

-- Table: PlanDeEstudios
CREATE TABLE PlanDeEstudios (
    id int  NOT NULL,
    ProgramaAcademico varchar(40)  NOT NULL,
    contenido varchar(10000)  NOT NULL,
    CONSTRAINT PlanDeEstudios_pk PRIMARY KEY (id,ProgramaAcademico)
);

-- Table: ProgramaAcademico
CREATE TABLE ProgramaAcademico (
    nombre varchar(100)  NOT NULL,
    CONSTRAINT ProgramaAcademico_pk PRIMARY KEY (nombre)
);

-- Table: Solicitud
CREATE TABLE Solicitud (
    numero int  NOT NULL,
    justificacion varchar(200)  NOT NULL,
    impacto varchar(200)  NULL,
    proyeccion varchar(200)  NULL,
    comentarios varchar(200)  NULL,
    estado varchar(30)  NULL,
    acuseRecibo boolean  NULL,
    Asignatura_Cancelar varchar(4)  NOT NULL,
    avalConsejero boolean  NULL,
    Estudiante_codigo int  NOT NULL,
    CONSTRAINT Solicitud_pk PRIMARY KEY (numero)
);

-- foreign keys
-- Reference: Estudiante_Acudiente (table: Estudiante)
ALTER TABLE Estudiante ADD CONSTRAINT Estudiante_Acudiente
    FOREIGN KEY (Acudiente_correo)
    REFERENCES Acudiente (correo)  
    NOT DEFERRABLE 
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

-- Reference: Solicitud_Asignatura (table: Solicitud)
ALTER TABLE Solicitud ADD CONSTRAINT Solicitud_Asignatura
    FOREIGN KEY (Asignatura_Cancelar)
    REFERENCES Asignatura (nemonico)  
    NOT DEFERRABLE 
;

-- Reference: Solicitud_Estudiante (table: Solicitud)
ALTER TABLE Solicitud ADD CONSTRAINT Solicitud_Estudiante
    FOREIGN KEY (Estudiante_codigo)
    REFERENCES Estudiante (codigo)  
    NOT DEFERRABLE 
;


-- Poblar ProgramaAcademico --
INSERT INTO ProgramaAcademico VALUES ('Ingenieria de sistemas');

--Poblar Consejero academico 
INSERT INTO ConsejeroAcademico VALUES ('Claudia',231831);
INSERT INTO ConsejeroAcademico VALUES ('Camilo',313131);

-- Poblar Coordinador de Cancelaciones
INSERT INTO CoordinadorCancelaciones VALUES (428131,'Laura');

-- Poblar Asignatura
INSERT INTO Asignatura VALUES ('PRON', 'Procesos de Negocio', 'Ingenieria de sistemas', 0);
INSERT INTO Asignatura VALUES ('POOB', 'Programacion Orientada a Objetos', 'Ingenieria de sistemas', 1);
INSERT INTO Asignatura VALUES ('ARQC', 'Arquitectura del computador', 'Ingenieria de sistemas', 0);

-- Poblar Acudiente 
INSERT INTO Acudiente VALUES ('Yolanda','yolanda@gmail.com');
INSERT INTO Acudiente VALUES ('Maria','maria@gmail.com');

-- Poblar Estudiante 
INSERT INTO Estudiante VALUES (79328, 'Juan David Giraldo Mancilla', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "PREM",
            "creditos": 4,
            "PreRec": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [21],
            "estado":"V"
        },
        {
            "nombre": "CALD",
            "creditos": 4,
            "preReq": "PREM",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "CIED",
            "creditos": 4,
            "preReq": "CALD",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "FFIS",
            "creditos": 4,
            "preReq": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [41],
            "estado":"V"
        },
        {
            "nombre": "FIMF",
            "creditos": 4,
            "preReq": "FFIS",
            "coReq": "CALD",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "FIEM",
            "creditos": 4,
            "preReq": "FIMF",
            "coReq": "CIED",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}', 1, 'juan.giraldo-m@mail.escuelaing.edu.co', 231831, 'yolanda@gmail.com',428131,'Ingenieria de sistemas'); 
INSERT INTO Estudiante VALUES (173183, 'Pepito perez montenegro', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "PREM",
            "creditos": 4,
            "PreRec": "",
            "coReq": "",
            "historialNotas": [35],
            "tercios": [21, 46, 40],
            "estado":"A"
        },
        {
            "nombre": "CALD",
            "creditos": 4,
            "preReq": "PREM",
            "coReq": "",
            "historialNotas": [],
            "tercios": [34],
            "estado":"V"
        },
        {
            "nombre": "CIED",
            "creditos": 4,
            "preReq": "CALD",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "FFIS",
            "creditos": 4,
            "preReq": "",
            "coReq": "",
            "historialNotas": [50],
            "tercios": [50,50,50],
            "estado":"A"
        },
        {
            "nombre": "FIMF",
            "creditos": 4,
            "preReq": "FFIS",
            "coReq": "CALD",
            "historialNotas": [],
            "tercios": [20],
            "estado":"V"
        },
        {
            "nombre": "FIEM",
            "creditos": 4,
            "preReq": "FIMF",
            "coReq": "CIED",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}', 2, 'pepito.perez@mail.escuelaing.edu.co', 231831, 'maria@gmail.com',428131,'Ingenieria de sistemas');
INSERT INTO Estudiante VALUES (2121465, 'Diana Sanchez', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "PREM",
            "creditos": 4,
            "PreRec": "",
            "coReq": "",
            "historialNotas": [30],
            "tercios": [30,30,30],
            "estado":"A"
        },
        {
            "nombre": "CALD",
            "creditos": 4,
            "preReq": "PREM",
            "coReq": "",
            "historialNotas": [-1],
            "tercios": [15],
            "estado":"V"
        },
        {
            "nombre": "CIED",
            "creditos": 4,
            "preReq": "CALD",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "FFIS",
            "creditos": 4,
            "preReq": "",
            "coReq": "",
            "historialNotas": [35],
            "tercios": [35,45,30],
            "estado":"A"
        },
        {
            "nombre": "FIMF",
            "creditos": 4,
            "preReq": "FFIS",
            "coReq": "CALD",
            "historialNotas": [],
            "tercios": [45],
            "estado":"V"
        },
        {
            "nombre": "FIEM",
            "creditos": 4,
            "preReq": "FIMF",
            "coReq": "CIED",
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}', 3, 'diana.sanchez-m@mail.escuelaing.edu.co', 313131, 'yolanda@gmail.com',428131,'Ingenieria de sistemas');

-- Poblar Solicitud--
INSERT INTO Solicitud VALUES (1,'Me consume mucho tiempo y estoy descuidando las otras materias','Se necesitaria un semestre adicional','PRON,FRED,SOPC,FGPR,ESTI y una electiva',
                            'Considero que si se debe aceptar la cancelacion, debido a la justificacion del estudiante','Aceptada',null,'PRON',true,79328);
INSERT INTO Solicitud VALUES (2,'Tengo muy bajita la nota y no me quiero arriesgar a perderla','Necesitaria dos semestres adicionales','POOB,ARQC,PDIS,PRON,ACFI',
                            'El estudiante no le entiende al profesor, por ende va mal en la materia y ya es imposible recuperar la materia','En estudio',true,'POOB',true,173183);
INSERT INTO Solicitud VALUES (3,'No le entiendo al profesor','Necesitarian dos semestres adicionales','ARQC,PDIS,ACFI,PRON,POOB',
                            'El estudiante puede buscar alternativas para entender los temas y pasar la materia','En estudio',false,'ARQC',true,173183);  

-- Poblar PlanDeEstudios
INSERT INTO PlanDeEstudios VALUES (13,'Ingenieria de sistemas','{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "PREM",
            "creditos": 4,
            "PreRec": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "CALD",
            "creditos": 4,
            "preReq": "PREM",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "CIED",
            "creditos": 4,
            "preReq": "CALD",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"V"
        },
        {
            "nombre": "FFIS",
            "creditos": 4,
            "preReq": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "FIMF",
            "creditos": 4,
            "preReq": "FFIS",
            "coReq": "CALD",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "FIEM",
            "creditos": 4,
            "preReq": "FIMF",
            "coReq": "CIED",
            "historialNotas": [],
            "tercios": [],
            "estado":"C"
        }
    ]
}');

INSERT INTO PlanDeEstudios VALUES (8,'Ingenieria de sistemas','{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "PREM",
            "creditos": 4,
            "PreRec": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "CALD",
            "creditos": 4,
            "preReq": "PREM",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "CIED",
            "creditos": 4,
            "preReq": "CALD",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"V"
        },
        {
            "nombre": "FFIS",
            "creditos": 4,
            "preReq": "",
            "coReq": "",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "FIMF",
            "creditos": 4,
            "preReq": "FFIS",
            "coReq": "CALD",
            "historialNotas": [],
            "tercios": [],
            "estado":"A"
        },
        {
            "nombre": "FIEM",
            "creditos": 4,
            "preReq": "FIMF",
            "coReq": "CIED",
            "historialNotas": [],
            "tercios": [],
            "estado":"C"
        }
    ]
}');
-- End of file.
