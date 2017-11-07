/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services;

import com.projectKepler.services.entities.Acudiente;
import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.CoordinadorCancelaciones;
import com.projectKepler.services.entities.Estudiante;
import java.util.List;

/**
 *
 * @author danielagonzalez
 */
public interface ServiciosCancelaciones {
    
    public  String consultarProgramaById(int codigo) throws ExcepcionServiciosCancelaciones;
    public  List<Asignatura> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    public  String consultarImpactoByEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones;
    public void actualizarJustificacionById(int id, String justificacion) throws ExcepcionServiciosCancelaciones;
    
}
