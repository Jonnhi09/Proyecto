/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence;

import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Estudiante;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author diana
 */
public interface EstudianteDAO {
    
    public List<Estudiante> loadAllEstudiantes() throws PersistenceException;
    public String loadPlanDeEstudio(int codigoEstudiante) throws PersistenceException;
    public List<Asignatura> loadCoursesById(int codigo) throws PersistenceException;
    public String consultImpactById(int codigo, String nemonico) throws PersistenceException;
    public void updateJustification(int codigo) throws PersistenceException;
}
