/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence;

import com.projectKepler.services.entities.CoordinadorCancelaciones;
import javax.persistence.PersistenceException;

/**
 *
 * @author danielagonzalez
 */
public interface CoordinadorCancelacionesDAO {
    public CoordinadorCancelaciones load() throws PersistenceException;
    public void loadById(int codigo) throws PersistenceException;
    public void save() throws PersistenceException;
    public void update(int codigo) throws PersistenceException;
}
