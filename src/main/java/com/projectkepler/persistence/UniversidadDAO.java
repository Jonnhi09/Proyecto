/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence;

import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public interface UniversidadDAO {
    public void updateCredits(int creditos) throws PersistenceException;
    public Universidad consultUniversity() throws PersistenceException;
    public PlanDeEstudios loadSyllabusByStudent(int codigoEstudiante) throws PersistenceException;
    public List<ProgramaAcademico> consultarProgramasAcademicos() throws PersistenceException;
    public PlanDeEstudios consultarPlanDeEstudios(String programa, int numero) throws PersistenceException; 
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(String nombre) throws PersistenceException;
    
}
