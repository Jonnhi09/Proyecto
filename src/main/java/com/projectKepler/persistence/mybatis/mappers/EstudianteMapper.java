/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis.mappers;

import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.PlanDeEstudios;
import com.projectKepler.services.entities.ProgramaAcademico;
import com.projectKepler.services.entities.Solicitud;
import com.projectKepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    
    public List<CourseStudent> loadCoursesById(@Param("codigo") int codigo);
    public Solicitud consultRequestByStudentSubject (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public void updateJustification (@Param("codigo") int codigo, @Param("justificacion") String justificacion,@Param("materia") String materia, @Param("numero")int numero, @Param("acuse") boolean acuse, @Param("impacto") String impacto, @Param("proyeccion")String proyeccion);
    public PlanDeEstudios loadSyllabusByStudent(@Param("codigo")int codigo);
    public Estudiante loadEstudianteById(@Param("codigo") int codigo);
    public void updateCredits(@Param("creditos") int creditos);
    public Universidad consultUniversity();
    public Estudiante consultStudentByEmail(@Param("correo") String correo);
    public List<ProgramaAcademico> consultarProgramasAcademicos();
    public PlanDeEstudios consultarPlanDeEstudios(@Param("numero") int numero, @Param("programa") String programa);
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(@Param("nombre")String nombre);
    public List<Solicitud> cargarSolicitudes();
    public List<Solicitud> consultRequest(@Param("correo") String consejero);
    public Estudiante consultStudentByRequest(@Param("codigo") int codigo);
    
}
