/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis.mappers;

import com.projectkepler.services.entities.Acudiente;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author diana
 */
public interface AcudienteMapper {

    public Acudiente consultarAcudientePorEstudiante(@Param("codigo") int codigo);
    
}
