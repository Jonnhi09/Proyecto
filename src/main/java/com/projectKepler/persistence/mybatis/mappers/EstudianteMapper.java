/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis.mappers;

import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Solicitud;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    
    public List<Estudiante> loadAllEstudiantes();
    public String loadPlanDeEstudio(@Param("codigo") int codigo);
    public List<Asignatura> loadCoursesById(@Param("codigo") int codigo);
    public String consultImpactById (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public void updateJustification (@Param("codigo") int codigo, @Param("justificacion") String justificacion,@Param("materia") String materia, @Param("numero")int numero, @Param("acuse") boolean acuse, @Param("impacto") String impacto, @Param("proyeccion")String proyeccion);
    public String loadSyllabusPrograma(@Param("codigo")int codigo);
    public Estudiante loadEstudianteById(@Param("codigo") int codigo);
    public List<Solicitud> loadSolicitudes();
    public String consultProyectionById (@Param("codigo") int codigo, @Param("nem") String nemonico);
    public void updateCredits(@Param("creditos") int creditos, @Param("programa") String programa);
    public int consultCredits(@Param("programa") String programa);
    public Estudiante consultStudentByEmail(@Param("correo") String correo);
}
