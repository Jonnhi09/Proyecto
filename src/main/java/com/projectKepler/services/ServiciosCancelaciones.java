/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services;

import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Course;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Syllabus;
import java.util.List;

/**
 *
 * @author danielagonzalez
 */
public interface ServiciosCancelaciones {
    
    /**
     * Carga todos los estudiantes registrados en la base de datos
     * @return Lista de estudiantes
     * @throws ExcepcionServiciosCancelaciones Si NO existe ningun estudiante registrado, o si se presenta otro problema en las capas inferiores.
     */
    public List<Estudiante> cargarEstudiantes() throws ExcepcionServiciosCancelaciones;
    
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
    public List<Course> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Obtener el impacto de una cancelacion calculado por el algoritmo a partir del identificador del estudiante y de la asignatura
     * @param codigoEstudiante es el identificador del estudiante
     * @param nemonicoAsignatura identificador de la asignatura
     * @return Impacto calculado por el algoritmo.
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con el identificador del estudiante, o si se presenta otro problema en las capas inferiores.
     */
    public String obtenerImpactoByEstudiante(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Consulta el impacto que genera la cancelacion de una asignatura a partir del identificador del estudiante y de la asignatura
     * @param codigoEstudiante identificador del estudiante
     * @param nemonicoAsignatura identificador de la asignatura
     * @return Impacto de cancelar una asignatura
     * @throws ExcepcionServiciosCancelaciones Si NO existe un estudiante con ese identificador o no tiene esa asignatura, o si se presenta otro problema en las capas inferiores.
     */
    public String consultarImpactoByEstudianteAsignatura(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones;
    
    /**
     * Modificar la justificacion de una solicitud de cancelacion a partir del identificador de la solicitud
     * @param id identificador de la solicitud
     * @param justificacion es la justificacion de una cancelacion
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con ese identificador, o si se presenta otro problema en las capas inferiores.
     */
    public void actualizarJustificacionById(int id, String justificacion, String materia) throws ExcepcionServiciosCancelaciones;
    
    /**
     *
     * @param codigo
     * @return
     */
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones;

    /**
     * Obtener el impacto de una cancelacion calculado por el algoritmo a partir del identificador del estudiante y de la asignatura
     * @param codigoEstudiante es el identificador del estudiante
     * @param nemonicoAsignatura identificador de la asignatura
     * @return Impacto calculado por el algoritmo.
     * @throws ExcepcionServiciosCancelaciones Si NO existe una solicitud con el identificador del estudiante, o si se presenta otro problema en las capas inferiores.
     */
    public String obtenerProyeccionByEstudiante(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones;
    
}
