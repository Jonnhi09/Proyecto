/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence;

import com.projectkepler.services.entities.Acudiente;

/**
 *
 * @author diana
 */
public interface AcudienteDAO {
   
    public Acudiente consultarAcudiantePorEstudiante(int codigo);
}
