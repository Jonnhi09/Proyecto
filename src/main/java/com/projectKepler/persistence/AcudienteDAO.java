/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence;

import com.projectKepler.services.entities.Acudiente;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author diana
 */
public interface AcudienteDAO {
    public List<Acudiente> loadAll() throws PersistenceException;
    public void load() throws PersistenceException;
    public void loadById(int codigo) throws PersistenceException;
    public void save() throws PersistenceException;
    public void update(int codigo) throws PersistenceException;
}
