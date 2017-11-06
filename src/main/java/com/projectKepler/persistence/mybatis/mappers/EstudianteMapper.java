/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis.mappers;

import com.projectKepler.services.entities.Estudiante;
import java.util.List;

/**
 *
 * @author diana
 */
public interface EstudianteMapper {
    public List<Estudiante> loadAllEstudiantes();
}
