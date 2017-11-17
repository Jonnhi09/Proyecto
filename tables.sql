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

-- Table: Course
CREATE TABLE Course (
    nemonico varchar(4)  NOT NULL,
    nombre varchar(100)  NOT NULL,
    programa varchar(100)  NOT NULL,
    CONSTRAINT Course_pk PRIMARY KEY (nemonico)
);

-- Table: Course_Estudiante
CREATE TABLE Course_Estudiante (
    Estudiante_codigo int  NOT NULL,
    Course_nemonico varchar(4)  NOT NULL,
    historialNotas varchar(20)  NOT NULL,
    notasTercios varchar(20)  NOT NULL,
    cantidadCancelaciones int  NOT NULL,
    estado char(1)  NOT NULL,
    CONSTRAINT Course_Estudiante_pk PRIMARY KEY (Estudiante_codigo,Course_nemonico)
);

-- Table: Estudiante
CREATE TABLE Estudiante (
    codigo int  NOT NULL,
    nombre varchar(100)  NOT NULL,
    planDeEstudios varchar(10000)  NOT NULL,
    versionPlanDeEstudio int  NOT NULL,
    numeroMatriculas int  NULL,
    correo varchar(50)  NOT NULL,
    ConsejeroAcademico_codigo int  NOT NULL,
    Acudiente_correo varchar(50)  NOT NULL,
    CoordinadorCancelaciones_codigo int  NOT NULL,
    programaAcademico varchar(100)  NOT NULL,
    CONSTRAINT correo UNIQUE (correo),
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
    creditosMaximos int  NOT NULL,
    CONSTRAINT ProgramaAcademico_pk PRIMARY KEY (nombre)
);

-- Table: Solicitud
CREATE TABLE Solicitud (
    numero int  NOT NULL,
    justificacion varchar(200)  NOT NULL,
    impacto varchar(200)  NOT NULL,
    proyeccion varchar(200)  NOT NULL,
    comentarios varchar(200)  NULL,
    estado varchar(30)  NULL,
    acuseRecibo boolean  NULL,
    Asignatura_Cancelar varchar(4)  NOT NULL,
    avalConsejero boolean  NULL,
    Estudiante_codigo int  NOT NULL,
    necesitaAcuseRecibo boolean  NULL,
    CONSTRAINT Solicitud_pk PRIMARY KEY (numero)
);

-- foreign keys
-- Reference: Course_Estudiante_Course (table: Course_Estudiante)
ALTER TABLE Course_Estudiante ADD CONSTRAINT Course_Estudiante_Course
    FOREIGN KEY (Course_nemonico)
    REFERENCES Course (nemonico)  
    NOT DEFERRABLE 
;

-- Reference: Course_Estudiante_Estudiante (table: Course_Estudiante)
ALTER TABLE Course_Estudiante ADD CONSTRAINT Course_Estudiante_Estudiante
    FOREIGN KEY (Estudiante_codigo)
    REFERENCES Estudiante (codigo)  
    NOT DEFERRABLE 
;

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
    REFERENCES Course (nemonico)  
    NOT DEFERRABLE 
;

-- Reference: Solicitud_Estudiante (table: Solicitud)
ALTER TABLE Solicitud ADD CONSTRAINT Solicitud_Estudiante
    FOREIGN KEY (Estudiante_codigo)
    REFERENCES Estudiante (codigo)  
    NOT DEFERRABLE 
;


-- Poblar ProgramaAcademico --
INSERT INTO ProgramaAcademico VALUES ('Ingenieria de sistemas',18);

--Poblar Consejero academico 
INSERT INTO ConsejeroAcademico VALUES (231831,'Claudia','claudia@escuelaing.edu.co');
INSERT INTO ConsejeroAcademico VALUES (313131,'Camilo','camilo@escuelaing.edu.co');

-- Poblar Coordinador de Cancelaciones
INSERT INTO CoordinadorCancelaciones VALUES (428131,'Laura','laura@escuelaing.edu.co');

-- Poblar Course
INSERT INTO Course VALUES ('CALD','Calculo Diferencial','Ingenieria de sistemas');
INSERT INTO Course VALUES ('FFIS','Fundamentos de Fisica','Ingenieria de sistemas');
INSERT INTO Course VALUES ('PREM','Precalculo','Ingenieria de sistemas');
INSERT INTO Course VALUES ('FIMF','Fisica Mecanica y Fluidos','Ingenieria de Sistemas');

-- Poblar Acudiente 
INSERT INTO Acudiente VALUES ('Yolanda','yolanda@gmail.com');
INSERT INTO Acudiente VALUES ('Maria','maria@gmail.com');

-- Poblar Estudiante 
INSERT INTO Estudiante VALUES (79328, 'Juan David Giraldo Mancilla', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "preca" ,
 	    "nemonico": "PREM",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [21],
            "estado":"V"
        },
        {
            "nombre": "calculo 1" ,
 	    "nemonico": "CALD",
            "creditos": 4,
            "preReq": ["PREM"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "calculo 2" ,
 	    "nemonico": "CIED",
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "fundamentos fisica" ,
 	    "nemonico": "FFIS",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [41],
            "estado":"V"
        },
        {
            "nombre": "fisica 1" ,
 	    "nemonico": "FIMF",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "fisica 2" ,
 	    "nemonico": "FIEM",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}',13, 1, 'juan.giraldo-m@mail.escuelaing.edu.co', 231831, 'yolanda@gmail.com',428131,'Ingenieria de sistemas'); 
INSERT INTO Estudiante VALUES (173183, 'Pepito perez montenegro', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "preca" ,
 	    "nemonico": "PREM",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "tercios": [21, 46, 40],
            "estado":"A"
        },
        {
            "nombre": "calculo 1" ,
 	    "nemonico": "CALD",,
            "creditos": 4,
            "preReq": ["PREM"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [34],
            "estado":"V"
        },
        {
            "nombre": "calculo 2" ,
 	    "nemonico": "CIED",,
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "fundamentos fisica" ,
 	    "nemonico": "FFIS",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [50],
            "tercios": [50,50,50],
            "estado":"A"
        },
        {
            "nombre": "fisica 1" ,
 	    "nemonico": "FIMF",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [20],
            "estado":"V"
        },
        {
            "nombre": "fisica 2" ,
 	    "nemonico": "FIEM",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}', 13, 2, 'pepito.perez@mail.escuelaing.edu.co', 231831, 'maria@gmail.com',428131,'Ingenieria de sistemas');
INSERT INTO Estudiante VALUES (2121465, 'Diana Sanchez', '{
    "programa": "ing. sistemas",
    "version": 13,        
    "courses": [
        {
            "nombre": "preca" ,
 	    "nemonico": "PREM",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [30],
            "tercios": [30,30,30],
            "estado":"A"
        },
        {
            "nombre": "calculo 1" ,
 	    "nemonico": "CALD",,
            "creditos": 4,
            "preReq": ["PREM"],
            "coReq": [],
            "historialNotas": [-1],
            "tercios": [15],
            "estado":"V"
        },
        {
            "nombre": "calculo 2" ,
 	    "nemonico": "CIED",,
            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        },
        {
            "nombre": "fundamentos fisica" ,
 	    "nemonico": "FFIS",
            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [35],
            "tercios": [35,45,30],
            "estado":"A"
        },
        {
            "nombre": "fisica 1" ,
 	    "nemonico": "FIMF",
            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [45],
            "estado":"V"
        },
        {
            "nombre": "fisica 2" ,
 	    "nemonico": "FIEM",
            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P"
        }
    ]
}', 13, 3, 'diana.sanchez-m@mail.escuelaing.edu.co', 313131, 'yolanda@gmail.com',428131,'Ingenieria de sistemas');

-- Poblar Course_Estudiante
INSERT INTO Course_Estudiante VALUES (79328,'PREM','{}','{21}',0,'V');
INSERT INTO Course_Estudiante VALUES (79328,'FFIS','{}','{41}',0,'V');
INSERT INTO Course_Estudiante VALUES (173183,'PREM','{35}','{21,46,40}',0,'A');
INSERT INTO Course_Estudiante VALUES (173183,'CALD','{}','{34}',0,'V');
INSERT INTO Course_Estudiante VALUES (173183,'FFIS','{50}','{50,50,50}',0,'A');
INSERT INTO Course_Estudiante VALUES (173183,'FIMF','{}','{20}',0,'V');
INSERT INTO Course_Estudiante VALUES (2121465,'PREM','{30}','{30,30,30}',0,'A');
INSERT INTO Course_Estudiante VALUES (2121465,'CALD','{-1}','{15}',1,'V');
INSERT INTO Course_Estudiante VALUES (2121465,'FFIS','{35}','{35,45,30}',0,'A');
INSERT INTO Course_Estudiante VALUES (2121465,'FIMF','{}','{45}',0,'V');

-- Poblar Solicitud--
INSERT INTO Solicitud VALUES (1,'Me consume mucho tiempo y estoy descuidando las otras materias','Si cancela FFIS le quedan: 20 creditos por ver.','FFIS,CALD,ALLI,LCAL y una electiva',
                            'Considero que si se debe aceptar la cancelacion, debido a la justificacion del estudiante','Aceptada',null,'FFIS',true,79328,false);
INSERT INTO Solicitud VALUES (2,'Tengo muy bajita la nota y no me quiero arriesgar a perderla','Si cancela CALD le quedan: 16 creditos por ver.','CALD,PIMB,ALLI,FIMF, ENG4',
                            'El estudiante no le entiende al profesor, por ende va mal en la materia y ya es imposible recuperar la materia','En estudio',true,'CALD',true,173183,true);
INSERT INTO Solicitud VALUES (3,'No le entiendo al profesor','Necesitarian dos semestres adicionales','ARQC,PDIS,ACFI,PRON,POOB',
                            'El estudiante puede buscar alternativas para entender los temas y pasar la materia','En estudio',false,'PREM',true,173183,true);  


-- Poblar PlanDeEstudios
INSERT INTO PlanDeEstudios VALUES (13,'Ingenieria de sistemas','{
    "programa": "Ingenieria de sistemas",
    "version": 13,        
    "courses": [
        {

            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fisica 2" ,
 	    "nemonico":"FIEM"
        },
        {

            "creditos": 4,
            "preReq": ["PREM"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "calculo 1" ,
 	    "nemonico":"CALD"
        },
        {

            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "calculo 2" ,
 	    "nemonico":"CIED"
        },
        {

            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fundamentos fisica" ,
 	    "nemonico":"FFIS"
        },
        {

            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fisica 1" ,
 	    "nemonico":"FIMF"
        },
        {

            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fisica 2" ,
 	    "nemonico":"FIEM"
        }
    ]
}');

INSERT INTO PlanDeEstudios VALUES (8,'Ingenieria de sistemas','{
    "programa": "Ingenieria de sistemas",
    "version": 13,        
    "courses": [
        {

            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "preca" ,
 	    "nemonico": "PREM"
        },
        {

            "creditos": 4,
            "preReq": ["PREM"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "calculo 1" ,
 	    "nemonico":"CALD"
        },
        {

            "creditos": 4,
            "preReq": ["CALD"],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "calculo 2" ,
 	    "nemonico":"CIED"
        },
        {

            "creditos": 4,
            "preReq": [],
            "coReq": [],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fundamentos fisica" ,
 	    "nemonico": "FFIS"
        },
        {

            "creditos": 4,
            "preReq": ["FFIS"],
            "coReq": ["CALD"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fisica 1" ,
 	    "nemonico":"FIMF"
        },
        {

            "creditos": 4,
            "preReq": ["FIMF"],
            "coReq": ["CIED"],
            "historialNotas": [],
            "tercios": [],
            "estado":"P",
            "nombre": "fisica 2" ,
 	    "nemonico":"FIEM"
        }
    ]
}');
-- End of file.
