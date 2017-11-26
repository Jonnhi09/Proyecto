/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence;

import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;


/**
 *
 * @author diana
 */
public interface EstudianteDAO {
    
    public PlanDeEstudios loadSyllabusByStudent(int codigoEstudiante) throws PersistenceException;
    public List<CourseStudent> loadCoursesById(int codigo) throws PersistenceException;
    public Solicitud consultRequestByStudentAndId(int codigo,String nemonico) throws PersistenceException;
    public void updateJustification(int codigo, String materia, String justificacion, int numero, boolean acuse, String impacto, String proyeccion) throws PersistenceException;
    public String loadEstudianteProgramaById(int codigo) throws PersistenceException;
    public Estudiante loadEstudianteById(int codigo) throws PersistenceException;
    public int loadEstudianteVersionById(int codigo) throws PersistenceException;
    public void updateCredits(int creditos) throws PersistenceException;
    public Universidad consultUniversity() throws PersistenceException;
    public Estudiante consultStudentByEmail(String correo) throws PersistenceException;
    public List<ProgramaAcademico> consultarProgramasAcademicos() throws PersistenceException;
    public PlanDeEstudios consultarPlanDeEstudios(String programa, int numero) throws PersistenceException; 
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(String nombre) throws PersistenceException;
    public List<Solicitud> consultarSolicitudes() throws PersistenceException;
    public List<Solicitud> consultRequest(String consejero) throws PersistenceException;
    public Estudiante consultStudentByRequest(int numero) throws PersistenceException;
    public void updateStateRequest(int numero,String estado) throws PersistenceException;
}
