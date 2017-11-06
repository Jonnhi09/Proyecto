/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis.mappers;

import com.projectKepler.services.entities.Acudiente;
import java.util.List;

/**
 *
 * @author diana
 */
public interface AcudienteMapper {
    public List<Acudiente> loadAllAcudientes();
}
