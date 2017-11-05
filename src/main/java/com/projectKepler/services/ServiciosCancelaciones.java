/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services;

import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.CoordinadorCancelaciones;
import java.util.List;

/**
 *
 * @author danielagonzalez
 */
public interface ServiciosCancelaciones {
    
    public abstract CoordinadorCancelaciones consultarCoordinador() throws ExcepcionServiciosCancelaciones;
    public abstract List<ConsejeroAcademico> consultarConsejerosAcademicos() throws ExcepcionServiciosCancelaciones;
    
}
