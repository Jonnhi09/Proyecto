/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectkepler.persistence.UniversidadDAO;
import com.projectkepler.persistence.mybatis.mappers.UniversidadMapper;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public class UniversidadDAOMyBatis implements UniversidadDAO{
    @Inject 
    private UniversidadMapper universidad;
    
    @Override
    public PlanDeEstudios loadSyllabusByStudent(int codigoEstudiante) throws PersistenceException {
        PlanDeEstudios programa;
        try{
            programa=universidad.loadSyllabusByStudent(codigoEstudiante);
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el plan de estudios del universidad: "+codigoEstudiante,e);
        }
        return programa;
    }
    
    @Override
    public void updateCredits(int creditos) throws PersistenceException{
        try{
            universidad.updateCredits(creditos);
        }catch (Exception e){
            throw new PersistenceException("Error al actualizar los creditos maximos",e);
        }
    }
    
    @Override
    public Universidad consultUniversity() throws PersistenceException{
        Universidad university;
        try{
            university=universidad.consultUniversity();
        }catch (Exception e){
            throw new PersistenceException("Error al consultar la universidad",e);
        }
        return university;
    }
    
    @Override
    public List<ProgramaAcademico> consultarProgramasAcademicos() throws PersistenceException {
        List<ProgramaAcademico> programas=null;
        try{
            programas=universidad.consultarProgramasAcademicos();
        }catch(Exception e){
            throw new PersistenceException("Error al consultar los programas academicos",e);
        }
        return programas;
    }

    @Override
    public PlanDeEstudios consultarPlanDeEstudios(String programa, int numero) throws PersistenceException {
        PlanDeEstudios plan=null;
        try{
            plan=universidad.consultarPlanDeEstudios(numero, programa);
        }catch(Exception e){
            throw new PersistenceException("Error al consultar el plan de estudios: "+numero+" del programa "+programa,e);
        }
        return plan;
    }

    @Override
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(String nombre) throws PersistenceException {
        ProgramaAcademico plan=null;
        try{
            plan=universidad.consultarProgramaAcademicoPorNombre(nombre);
        }catch(Exception e){
            throw new PersistenceException("Error al consultar el programa "+nombre,e);
        }
        return plan;
    }
}
