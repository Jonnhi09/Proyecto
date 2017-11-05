/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.CoordinadorCancelacionesDAO;
import com.projectKepler.persistence.mybatis.mappers.CoordinadorMapper;
import com.projectKepler.services.entities.CoordinadorCancelaciones;
import javax.persistence.PersistenceException;

/**
 *
 * @author danielagonzalez
 */
public class CoordinadorCancelacionesDAOMyBatis implements CoordinadorCancelacionesDAO{

    @Inject
    private CoordinadorMapper coordinador;

    @Override
    public CoordinadorCancelaciones load() throws PersistenceException {
        CoordinadorCancelaciones coordi=null;
        try{
            coordi=coordinador.loadCoordinador();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el coordinador",e);
        }
        return coordi;
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
