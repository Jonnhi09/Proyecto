/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectkepler.persistence.EstudianteDAO;
import com.projectkepler.persistence.mybatis.mappers.EstudianteMapper;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Universidad;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author diana
 */
public class EstudianteDAOMyBatis implements EstudianteDAO{

    @Inject 
    private EstudianteMapper estudiante;

    
    
    
    @Override
    public String loadEstudianteProgramaById(int codigo) throws PersistenceException {
        String student=null;
        try{
            student=estudiante.loadEstudianteById(codigo).getProgramaAcademico();
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el programa academico del estudiante con codigo:"+codigo,e);
        }
        return student;
    }
    
    @Override
    public Estudiante loadEstudianteById(int codigo) throws PersistenceException {
        Estudiante student=null;
        try{
            student=estudiante.loadEstudianteById(codigo);
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el estudiante con codigo:"+codigo,e);
        }
        return student;
    }
    
    @Override
    public int loadEstudianteVersionById(int codigo) throws PersistenceException {
        int version=0;
        try{
            version=estudiante.loadEstudianteById(codigo).getVersionPlanDeEstudio();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar la version de plan de estudio del estudiante "+codigo,e);
        }
        return version;
    }
    
    @Override
    public Estudiante consultStudentByEmail(String correo) throws PersistenceException{
        Estudiante student=null;
        try{
            student=estudiante.consultStudentByEmail(correo);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el estudiante con el correo :"+correo,e);
        }
        return student;
    }
    
    @Override
    public List<Solicitud> consultRequest(String consejero) throws PersistenceException{
        List<Solicitud> solicitudes;
        try{
            solicitudes=estudiante.consultRequest(consejero);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar las solicitudes para el consejero "+consejero,e);
        }
        return solicitudes;
    }
    
    @Override
    public Estudiante consultStudentByRequest(int numero) throws PersistenceException{
        Estudiante student;
        try{
            student=estudiante.consultStudentByRequest(numero);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar un estudiante dado el numero de una solicitud",e);
        }
        return student;
    }
    
    
}
