/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence;

import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Solicitud;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;


/**
 *
 * @author diana
 */
public interface EstudianteDAO {
    
    public List<Estudiante> loadAllEstudiantes() throws PersistenceException;
    public String loadPlanDeEstudio(int codigoEstudiante) throws PersistenceException;
    public String loadSyllabusProgramaById(int codigoEstudiante) throws PersistenceException;
    public List<Asignatura> loadCoursesById(int codigo) throws PersistenceException;
    public String consultImpactById(int codigo, String nemonico) throws PersistenceException;
    public String consultProyectionById(int codigo, String nemonico) throws PersistenceException;
    public void updateJustification(int codigo, String materia, String justificacion, int numero, boolean acuse, String impacto, String proyeccion) throws PersistenceException;
    public Estudiante loadEstudianteById(int codigo) throws PersistenceException;
    public List<Solicitud> loadSolicitudes() throws PersistenceException;
    public void updateCredits(int creditos, String programa) throws PersistenceException;
    public int consultCredits(String programa) throws PersistenceException;
    public Estudiante consultStudentByEmail(String correo) throws PersistenceException;
    public List<String> consultCourse(int codigo) throws PersistenceException;

}
