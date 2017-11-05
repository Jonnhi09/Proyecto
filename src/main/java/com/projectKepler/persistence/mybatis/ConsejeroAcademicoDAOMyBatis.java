/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.ConsejeroAcademicoDAO;
import com.projectKepler.persistence.mybatis.mappers.ConsejeroMapper;
import com.projectKepler.services.entities.ConsejeroAcademico;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author danielagonzalez
 */
public class ConsejeroAcademicoDAOMyBatis implements ConsejeroAcademicoDAO{

    @Inject 
    private ConsejeroMapper consejero;
    
    @Override
    public List<ConsejeroAcademico> loadAll() throws PersistenceException {
        List<ConsejeroAcademico> allConsejeros=null;
        try{
            allConsejeros=consejero.loadAllConsejeros();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar todos los consejeros",e);
        }
        return allConsejeros;
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
