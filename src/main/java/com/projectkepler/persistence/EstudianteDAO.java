/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence;

import com.projectkepler.services.entities.ConsejeroAcademico;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.Solicitud;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;


/**
 *
 * @author diana
 */
public interface EstudianteDAO {
    
    public String loadEstudianteProgramaById(int codigo) throws PersistenceException;
    public Estudiante loadEstudianteById(int codigo) throws PersistenceException;
    public int loadEstudianteVersionById(int codigo) throws PersistenceException;
    public Estudiante consultStudentByEmail(String correo) throws PersistenceException;
    public List<Solicitud> consultRequest(String consejero) throws PersistenceException;
    public Estudiante consultStudentByRequest(int numero) throws PersistenceException;
    public List<Solicitud> consultRequestByStudent(int codigo) throws PersistenceException;
    public ConsejeroAcademico consultarConsejeroPorEstudiante(int codigo) throws PersistenceException;
}
