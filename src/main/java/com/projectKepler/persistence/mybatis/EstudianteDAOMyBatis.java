/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.persistence.mybatis.mappers.EstudianteMapper;
import com.projectKepler.services.entities.Estudiante;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author diana
 */
public class EstudianteDAOMyBatis implements EstudianteDAO{

    @Inject 
    private EstudianteMapper estudiante;
    
    @Override
    public List<Estudiante> loadAll() throws PersistenceException {
        List<Estudiante> allEstudiantes=null;
        try{
            allEstudiantes=estudiante.loadAllEstudiantes();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar todos los estudiantes",e);
        }
        return allEstudiantes;
    }

    @Override
    public void load() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadById(int codigo) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(int codigo) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
