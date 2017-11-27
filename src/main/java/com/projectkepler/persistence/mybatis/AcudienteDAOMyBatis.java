/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectkepler.persistence.AcudienteDAO;
import com.projectkepler.persistence.mybatis.mappers.AcudienteMapper;
import com.projectkepler.services.entities.Acudiente;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public class AcudienteDAOMyBatis implements AcudienteDAO {
    
    @Inject 
    private AcudienteMapper acudiente;

    @Override
    public Acudiente consultarAcudiantePorEstudiante(int codigo) {
        Acudiente acu;
        try{
            acu=acudiente.consultarAcudientePorEstudiante(codigo);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el acudiente del estudiante con codigo:"+codigo,e);
        }
        return acu;
    }
    
}
