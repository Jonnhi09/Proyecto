/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectkepler.persistence.SolicitudDAO;
import com.projectkepler.persistence.mybatis.mappers.SolicitudMapper;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Solicitud;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public class SolicitudDAOMyBatis implements SolicitudDAO{
    
    @Inject 
    private SolicitudMapper solicitud;
    
    @Override
    public Solicitud consultRequestByStudentAndId(int codigo, String nemonico) throws PersistenceException {
        Solicitud request;
        try{
            request=solicitud.consultRequestByStudentSubject(codigo, nemonico);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar la solicitud "+nemonico+" del estudiante :"+codigo,e);
        }
        return request;
    }
    
    @Override
    public List<CourseStudent> loadCoursesById(int codigo) throws PersistenceException {
        List<CourseStudent> asignaturas;
        try{
            asignaturas=solicitud.loadCoursesById(codigo);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar las asignaturas del estudiante "+codigo,e);
        }
        return asignaturas;
    }
    
    @Override
    public void updateJustification(int codigo, String materia, String justificacion, int numero,boolean acuse, String impacto, String proyeccion) throws PersistenceException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            solicitud.updateJustification(codigo, justificacion, materia, numero, acuse, impacto,proyeccion, timestamp);

        }catch (Exception e){
            throw new PersistenceException("Error al registrar la solicitud numero:"+numero,e);
        }
    }
    
    @Override 
    public List<Solicitud> consultarSolicitudes() throws PersistenceException {
        List<Solicitud> solicitudes=null;
        try{
            solicitudes=solicitud.cargarSolicitudes();
        }catch(Exception e){
            throw new PersistenceException("Error al consultar los programas academicos",e);
        }
        return solicitudes;
    }

    @Override
    public void updateComentariosSolicitud(int numero, String comentarios) throws PersistenceException {
        try{
            solicitud.updateComentariosSolicitud(numero,comentarios);
        }catch (Exception e){
            throw new PersistenceException("Error al actualizar justificacion",e);
        }
    }
    
    @Override
    public void updateStateRequest(int numero,String estado) throws PersistenceException{
        try{
            solicitud.updateStateRequest(numero,estado);
        }catch (Exception e){
            throw new PersistenceException("Error al actualizar el estado de la solicitud "+numero,e);
        }
    }
}
