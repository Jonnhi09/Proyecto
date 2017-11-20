/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis.mappers;

import com.projectKepler.services.entities.Acudiente;
import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.PlanDeEstudios;
import com.projectKepler.services.entities.ProgramaAcademico;
import com.projectKepler.services.entities.Solicitud;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    
    public List<Estudiante> loadAllEstudiantes();
    public String loadMaterias(@Param("codigo") int codigo);
    public List<Asignatura> loadCoursesById(@Param("codigo") int codigo);
    public String consultImpactById (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public void updateJustification (@Param("codigo") int codigo, @Param("justificacion") String justificacion,@Param("materia") String materia, @Param("numero")int numero, @Param("acuse") boolean acuse, @Param("impacto") String impacto, @Param("proyeccion")String proyeccion);
    public String loadSyllabusPrograma(@Param("codigo")int codigo);
    public String loadProgramaPorEstudiante(@Param("codigo") int codigo);
    public int loadVersionPorEstudiante(@Param("codigo") int codigo);
    public Estudiante loadEstudianteById(@Param("codigo") int codigo);
    public List<Solicitud> loadSolicitudes();
    public String consultProyectionById (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public void updateCredits(@Param("creditos") int creditos);
    public int consultCredits();
    public Estudiante consultStudentByEmail(@Param("correo") String correo);
    public List<String> consultCourse(@Param("codigo") int codigo);
    public List<ProgramaAcademico> consultarProgramasAcademicos();
    public PlanDeEstudios consultarPlanDeEstudios(@Param("numero") int numero, @Param("programa") String programa);
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(@Param("nombre")String nombre);
    public List<Acudiente> cargarAcudientes();
    public List<Solicitud> cargarSolicitudes();
    public List<Estudiante> consultRequest(@Param("correo") String consejero);
    public List<Solicitud> consultRequestById(@Param("codigo") int codigo);
    public CourseStudent consultAsignatura(@Param("numero") int numero);
    
}
