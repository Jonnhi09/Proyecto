/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence;

import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Solicitud;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public interface SolicitudDAO {
    public void updateJustification(int codigo, String materia, String justificacion, int numero, boolean acuse, String impacto, String proyeccion) throws PersistenceException;
    public Solicitud consultRequestByStudentAndId(int codigo,String nemonico) throws PersistenceException;
    public List<Solicitud> consultarSolicitudes() throws PersistenceException;
    public List<CourseStudent> loadCoursesById(int codigo) throws PersistenceException;
    public void updateComentariosSolicitud(int numero,String comentarios) throws PersistenceException;
    public void updateStateRequest(int numero,String estado) throws PersistenceException;
    public void actualizarAcuseSolicitud(int numero)throws PersistenceException;
    public Solicitud consultarSolicitud(int numero) throws PersistenceException;
}
