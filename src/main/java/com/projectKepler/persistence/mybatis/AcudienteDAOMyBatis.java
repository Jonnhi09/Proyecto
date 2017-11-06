/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.AcudienteDAO;
import com.projectKepler.persistence.mybatis.mappers.AcudienteMapper;
import com.projectKepler.services.entities.Acudiente;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author diana
 */
public class AcudienteDAOMyBatis implements AcudienteDAO{

    @Inject 
    private AcudienteMapper acudiente;
    
    @Override
    public List<Acudiente> loadAll() throws PersistenceException {
        List<Acudiente> allAcudientes=null;
        try{
            allAcudientes=acudiente.loadAllAcudientes();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar todos los Acudientes",e);
        }
        return allAcudientes;
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
