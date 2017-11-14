/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.persistence.mybatis.mappers.EstudianteMapper;
import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Solicitud;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author diana
 */
public class EstudianteDAOMyBatis implements EstudianteDAO{

    @Inject 
    private EstudianteMapper estudiante;
    
    @Override
    public List<Estudiante> loadAllEstudiantes() throws PersistenceException {
        List<Estudiante> allEstudiantes=null;
        try{
            allEstudiantes=estudiante.loadAllEstudiantes();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar todos los estudiantes",e);
        }
        return allEstudiantes;
    }

    @Override
    public String loadPlanDeEstudio (int codigoEstudiante) throws PersistenceException {
        String programa="";
        try{
            programa=estudiante.loadPlanDeEstudio(codigoEstudiante);
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el programa del estudiante: "+codigoEstudiante,e);
        }
        return programa;
    }

    @Override
    public List<Asignatura> loadCoursesById(int codigo) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consultImpactById(int codigo, String nemonico) throws PersistenceException {
        String impacto="";
        try{
            impacto=estudiante.consultImpactById(codigo, nemonico);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el impacto de cancelar la asignatura "+nemonico+" del estudiante :"+codigo,e);
        }
        return impacto;
    }

    
    @Override
    public String loadSyllabusProgramaById(int codigoEstudiante) throws PersistenceException {
        String programa="";
        try{
            programa=estudiante.loadSyllabusPrograma(codigoEstudiante);
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el programa del estudiante: "+codigoEstudiante,e);
        }
        return programa;
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
    public void updateJustification(int codigo, String materia, String justificacion, int numero,boolean acuse, String impacto, String proyeccion) throws PersistenceException {
        try{
            estudiante.updateJustification(codigo, justificacion, materia, numero, acuse, impacto,proyeccion);
        }catch (Exception e){
            throw new PersistenceException("Error al registrar la solicitud numero:"+numero,e);
        }
    }

    @Override
    public List<Solicitud> loadSolicitudes() throws PersistenceException {
        List<Solicitud> solicitudes=null;
    
        try{
            solicitudes=estudiante.loadSolicitudes();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar las solicitudes",e);
        }
        return solicitudes;
    }

    @Override
    public String consultProyectionById(int codigo, String nemonico) throws PersistenceException {
        String proyeccion="";
        try{
            proyeccion=estudiante.consultProyectionById(codigo, nemonico);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el impacto de cancelar la asignatura "+nemonico+" del estudiante :"+codigo,e);
        }
        return proyeccion;
    }
    
    @Override
    public void updateCredits(int creditos, String programa) throws PersistenceException{
        try{
            estudiante.updateCredits(creditos, programa);
        }catch (Exception e){
            throw new PersistenceException("Error al actualizar los creditos maximos del programa :"+programa,e);
        }
    }
    
    @Override
    public int consultCredits(String programa) throws PersistenceException{
        int credits=0;
        try{
            credits=estudiante.consultCredits(programa);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar el numero de creditos maximos del programa :"+programa,e);
        }
        return credits;
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
    public List<String> consultCourse(int codigo) throws PersistenceException{
        List<String> course=null;
        try{
            course=estudiante.consultCourse(codigo);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar la asiganturas que tienen solicitud de cancelacion del estudiante :"+codigo,e);
        }
        return course;
    }
}
