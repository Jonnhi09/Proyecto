/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services;

import com.projectkepler.services.entities.Acudiente;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Syllabus;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danielagonzalez
 */
public interface ServiciosCancelaciones {
    
    /**
     * Consulta el plan de estudio de un estudiante a partir de su identificador
     * @param codigo es el identificador del estudiante
     * @return Plan de estudio de un estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese identificador, o si se presenta otro problema en la capas inferiores.
     */
    public String consultarPlanDeEstudioByIdEstudiante(int codigo) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta las asignaturas de un estudiante a partir de su identificador
     * @param codigoEstudiante identificador del estudiante
     * @return Lista de asignaturas de un estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public List<CourseStudent> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta el impacto que genera la cancelacion de una asignatura a partir del identificador del estudiante y de la asignatura
     * @param codigoEstudiante identificador del estudiante
     * @param nemonicoAsignatura identificador de la asignatura
     * @return Impacto de cancelar una asignatura
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese identificador o no tiene esa asignatura, o si se presenta otro problema en las capas inferiores.
     */
    public String consultarImpactoByEstudianteAsignatura(int codigoEstudiante, String[] nemonicoAsignatura) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Modificar la justificacion de una solicitud de cancelacion a partir del codigo y la materia que va a cancelar el estudiante
     * @param id id es el codigo del estudiante que va a cancelar
     * @param justificacion es la justificacion de una cancelacion
     * @param materia es el nemonico de la asignatura a cancelar
     * @Param impacto es el impacto que se produce al cancelar las o la materia
     * @Param proyeccion es la proyeccion que se produce al cancelar las o la materia
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public void actualizarJustificacionById(int id, String justificacion, String materia, String impacto, String proyeccion) throws ExcepcionServiciosCancelaciones;
    
    /**
     *Obtiene el plan de estudios especifico del estudiante y el plan de estudios de programa academico al que pertenece el estudiante
     * @param codigo identificador del estudiante que realiza la solicitud
     * @return en la primera posicion esta el plan de estudios especifico del estudiante y en la segunda posicion esta el plan de estudios del programa academico
     * @throws ExcepcionServiciosCancelaciones Si NO existe un plan de estudios para el estudiante, o si se presenta otro problema en las capas inferiores.
     */
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta la proyeccion que genera la cancelacion de una asignatura a partir del identificador del estudiante y de la asignatura
     * @param codigoEstudiante identificador del estudiante
     * @param nemonicoAsignatura identificador de la asignatura
     * @return Proyeccion de cancelar una asignatura
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese identificador o no tiene esa asignatura, o si se presenta otro problema en las capas inferiores.
     */
    public String consultarProyeccionByEstudianteAsignatura(int codigoEstudiante, String[] nemonicoAsignatura) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Actualiza el numero maximo de creditos que se pueden ver por semestre.
     * @param creditos numero de creditos maximos
     * @throws ExcepcionServiciosCancelaciones Si NO existe un programa con ese nombre, o si se presenta otro problema en las capas inferiores.
     */
    public void actualizarNumeroMaximoDeCreditos(int creditos) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta el numero maximo de creditos que se pueden ver por semestre de un programa
     * @return numero de creditos maximos de un programa
     * @throws ExcepcionServiciosCancelaciones Si se presenta otro problema en las capas inferiores.
     */
    public int consultarNumeroMaximoDeCreditos() throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta un estudiante dado su correo
     * @param correo es el correo del estudiante
     * @return Un estudiante de acuerdo a su correo
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese correo, o si se presenta otro problema en las capas inferiores.
     */
    public Estudiante consultarEstudianteByCorreo(String correo) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consultar las asignaturas de un estudiante que no tienen solicitudes de cancelacion
     * @param codigoEstudiante identificador del estudiante
     * @return Lista de asignaturas del estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe el estudiante, o si se presenta otro problema en las capas inferiores.
     */
    public List<CourseStudent> consultarAsignaturasSinSolicitudByIdEStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consultar todos los programas academicos 
     * @return Lista de Programas Academicos existentes en la base de datos
     * @throws ExcepcionServiciosCancelaciones Si surge un error en capas inferiores
     */
    public List<ProgramaAcademico> consultarTodosProgramasAcademicos() throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consultar un plan de estudios que pertenece a un programa academico
     * @param planDeEstudios identificador del plan de estudio
     * @param programa es el programa academico al que pertenece el plan de estudios
     * @return Lista de asignaturas del estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe el programa o el plan de estudios, o si se presenta otro problema en las capas inferiores.
     */
    public Syllabus consultarPlanDeEstudios(String programa, int planDeEstudios) throws ExcepcionServiciosCancelaciones;
    
    /**
     *Consulta los numeros de planes de estudio que tiene un porgrama academico a partir de su nombre
     * @param programa es el nombre del programa academico
     * @return retorna los id de los planes de estudio que pertenecen al programa
     * @throws ExcepcionServiciosCancelaciones 
     */
    public ArrayList<Integer> consultarPlanesDeEstudiosPorPrograma(String programa) throws ExcepcionServiciosCancelaciones; 
    

    /**
     * Consultar las solicitudes de cancelaciones a partir de un consejero
     * @param consejero es el correo del consejero 
     * @return Lista de solicitudes de cancelaciones
     * @throws ExcepcionServiciosCancelaciones Si NO existen solicitudes, o si se presenta otro problema en las capas inferiores.
     */
    public List<Solicitud> consultarSolicitudesDeCancelaciones(String consejero) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta un estudiante dado el numero de una solicitud
     * @param numero es el identificador de la solicitud
     * @return el codigo de un estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public Estudiante consultarEstudiantePorSolicitud(int numero) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Actualiza los comentarios de una solicitud dados por el consejero
     * @param numero es el identificador del estudiante que hizo la solicitud
     * @param comentarios son los comentarios a insertar
     * @Param nemonico es el nemonico de la asignatura a cancelar
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public void actualizarComentariosSolicitud(int numero,String comentarios) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Actualiza el estado de una solicitud de acuerdo a sus modificaciones
     * @param numero es el numero de la solicitud que se va modificar
     * @param estado es el nuevo estado de la solicitud
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese numero, o si se presenta otro problema en las capas inferiores.
     */
    public void actualizarEstadoSolicitud(int numero, String estado) throws ExcepcionServiciosCancelaciones;

    /**
     * Registra la solicitud en la base de datos
     * @param id id es el codigo del estudiante que va a cancelar
     * @param justificacion es la justificacion de una cancelacion
     * @param materias es una lista que contiene las asignaturas a cancelar
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public void enviarSolicitudes(int id, String justificacion, List<CourseStudent> materias) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta el acudiente de un estudiante dado su codigo
     * @param codigoEstudiante es el identificador del estudiante 
     * @return el Acudiente del estudiante
     * @throws ExcepcionServiciosCancelaciones 
     */
    public Acudiente consultarAcudientePorStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Actualiza el acuse de recibo cuando el acudiente de un estudiante ve la olas solicitudes
     * @param numero es el identificador de la solicitud
     * @throws ExcepcionServiciosCancelaciones cuando no todas las solicitudes del estudiante han sido comentadas por el consejero
     */
    public void actualizarAcuseSolicitud(int numero)throws ExcepcionServiciosCancelaciones;
    
    
    /**
     * Consulta las solicitudes de cancelacion de un estudiante
     * @param codigo es el identificador del estudiante
     * @return Una lista de las solicitudes del estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existen solicitudes para ese estudiante, o si se presenta otro problema en las capas inferiores.
     */
    public List<Solicitud> consultarSolicitudesPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones;

    /**
     * Consulta un estudiante dado su identificador
     * @param codigo identificador del estudiante
     * @return El estudiante
     * @throws ExcepcionServiciosCancelaciones Si NO existe estudiante con ese codigo, o si se presenta otro problema en las capas inferiores.
     */
    public Estudiante consultarEstudianteById(int codigo) throws ExcepcionServiciosCancelaciones;
    
    public Solicitud consultarSolicitudPorEstudianteYNemonico(int codigo,String nemonico) throws ExcepcionServiciosCancelaciones;
    
    public List<Solicitud> consultarSolicitudes() throws  ExcepcionServiciosCancelaciones;
}

