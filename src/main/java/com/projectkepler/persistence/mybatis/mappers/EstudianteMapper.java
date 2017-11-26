/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis.mappers;

import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    
    public PlanDeEstudios loadSyllabusByStudent(@Param("codigo")int codigo);
    public Estudiante loadEstudianteById(@Param("codigo") int codigo);
    public void updateCredits(@Param("creditos") int creditos);
    public Universidad consultUniversity();
    public Estudiante consultStudentByEmail(@Param("correo") String correo);
    public List<ProgramaAcademico> consultarProgramasAcademicos();
    public PlanDeEstudios consultarPlanDeEstudios(@Param("numero") int numero, @Param("programa") String programa);
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(@Param("nombre")String nombre);
    public List<Solicitud> consultRequest(@Param("correo") String consejero);
    public Estudiante consultStudentByRequest(@Param("codigo") int codigo);
    
}
