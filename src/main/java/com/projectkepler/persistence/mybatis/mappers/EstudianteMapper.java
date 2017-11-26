/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis.mappers;

import com.projectkepler.services.entities.Estudiante;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    
    public Estudiante loadEstudianteById(@Param("codigo") int codigo);
    public Estudiante consultStudentByEmail(@Param("correo") String correo);
    public Estudiante consultStudentByRequest(@Param("codigo") int codigo);
    
}
