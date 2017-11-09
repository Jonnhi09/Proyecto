/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.impl;

import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.Acudiente;
import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.CoordinadorCancelaciones;
import com.projectKepler.services.entities.Estudiante;
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
    private EstudianteDAO estudiante;
    
    public ServiciosCancelacionesImpl(){
        
    }
    
    @Transactional
    @Override
    public String consultarProgramaById(int codigo) throws ExcepcionServiciosCancelaciones {
        String programa="";
        try{
            programa=estudiante.loadPrograma(codigo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return programa;
        
    }

    @Override
    public List<Asignatura> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        return null;
    }

    @Override
    public String consultarImpactoByEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        return null;
    }

    @Override
    public void actualizarJustificacionById(int id, String justificacion) throws ExcepcionServiciosCancelaciones {
        
    }

    
    
}

