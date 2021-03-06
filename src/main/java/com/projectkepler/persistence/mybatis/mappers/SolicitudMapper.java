/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis.mappers;

import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Solicitud;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface SolicitudMapper {
    public Solicitud consultRequestByStudentSubject (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public List<Solicitud> cargarSolicitudes();
    public void updateJustification (@Param("codigo") int codigo, @Param("justificacion") String justificacion,@Param("materia") String materia, @Param("numero")int numero, @Param("acuse") boolean acuse, @Param("fecha")Timestamp fecha);
    public List<CourseStudent> loadCoursesById(@Param("codigo") int codigo);
    public void updateComentariosSolicitud (@Param("numero")int numero,@Param("comentarios")String comentarios);
    public void updateStateRequest(@Param("numero") int numero, @Param("estado") String estado);
    public void actualizarAcuseSolicitud(@Param("numero") int numero, @Param("acuse") boolean acuse); 
    public Solicitud cargarSolicitud(@Param("numero")int numero);
    public List<CourseStudent> consultCanceledSubjectsByStudent(@Param("codigo") int codigo);
    public List<Solicitud> consultRequest(@Param("correo") String consejero);
    public List<Solicitud> consultRequestsByCoordinator(@Param("correo") String correo);
}
