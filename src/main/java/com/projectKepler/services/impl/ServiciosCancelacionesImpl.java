/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.impl;

import com.google.inject.Inject;
import com.projectKepler.persistence.ConsejeroAcademicoDAO;
import com.projectKepler.persistence.CoordinadorCancelacionesDAO;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.CoordinadorCancelaciones;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

/**
 *
 * @author danielagonzalez
 */
public class ServiciosCancelacionesImpl implements ServiciosCancelaciones{

    @Inject
    private ConsejeroAcademicoDAO consejeroAc;
    @Inject
    private CoordinadorCancelacionesDAO coordinador;
    
    public ServiciosCancelacionesImpl(){
    
    }
    
    @Transactional
    @Override
    public List<ConsejeroAcademico> consultarConsejerosAcademicos() throws ExcepcionServiciosCancelaciones{
        List<ConsejeroAcademico> consejero=null;
        try{
            consejero=consejeroAc.loadAll();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE,null,e);
        }
        return consejero;
    }
    
    @Transactional
    @Override
    public CoordinadorCancelaciones consultarCoordinador() throws ExcepcionServiciosCancelaciones{
        CoordinadorCancelaciones coordinadorC=null;
        try{
            coordinadorC=coordinador.load();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE,null,e);
        }
        return coordinadorC;
    }
}

