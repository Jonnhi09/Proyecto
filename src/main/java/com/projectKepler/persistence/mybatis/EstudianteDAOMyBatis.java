/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.persistence.mybatis;

import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.persistence.mybatis.mappers.EstudianteMapper;
import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.PlanDeEstudios;
import com.projectKepler.services.entities.ProgramaAcademico;
import com.projectKepler.services.entities.Solicitud;
import com.projectKepler.services.entities.Universidad;
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
    public List<CourseStudent> loadCoursesById(int codigo) throws PersistenceException {
        List<CourseStudent> asignaturas;
        try{
            asignaturas=estudiante.loadCoursesById(codigo);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar las asignaturas del estudiante "+codigo,e);
        }
        return asignaturas;
    }

    @Override
    public Solicitud consultRequestByStudentAndId(int codigo, String nemonico) throws PersistenceException {
        Solicitud solicitud;
        try{
            solicitud=estudiante.consultRequestByStudentSubject(codigo, nemonico);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar la solicitud "+nemonico+" del estudiante :"+codigo,e);
        }
        return solicitud;
    }

    
    @Override
    public PlanDeEstudios loadSyllabusByStudent(int codigoEstudiante) throws PersistenceException {
        PlanDeEstudios programa;
        try{
            programa=estudiante.loadSyllabusByStudent(codigoEstudiante);
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el plan de estudios del estudiante: "+codigoEstudiante,e);
        }
        return programa;
    }
    
    @Override
    public String loadEstudianteProgramaById(int codigo) throws PersistenceException {
        String student=null;
        try{
            student=estudiante.loadEstudianteById(codigo).getProgramaAcademico();
        }catch (Exception e){
            throw new PersistenceException("Error al cargar el estudiante con codigo:"+codigo,e);
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
            throw new PersistenceException("Error al cargar version",e);
        }
        return version;
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
    public void updateCredits(int creditos) throws PersistenceException{
        try{
            estudiante.updateCredits(creditos);
        }catch (Exception e){
            throw new PersistenceException("Error al actualizar los creditos maximos",e);
        }
    }
    
    @Override
    public Universidad consultUniversity() throws PersistenceException{
        Universidad university;
        try{
            university=estudiante.consultUniversity();
        }catch (Exception e){
            throw new PersistenceException("Error al consultar la universidad",e);
        }
        return university;
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
    public List<ProgramaAcademico> consultarProgramasAcademicos() throws PersistenceException {
        List<ProgramaAcademico> programas=null;
        try{
            programas=estudiante.consultarProgramasAcademicos();
        }catch(Exception e){
            throw new PersistenceException("Error al consultar los programas academicos",e);
        }
        return programas;
    }

    @Override
    public PlanDeEstudios consultarPlanDeEstudios(String programa, int numero) throws PersistenceException {
        PlanDeEstudios plan=null;
        try{
            plan=estudiante.consultarPlanDeEstudios(numero, programa);
        }catch(Exception e){
            throw new PersistenceException("Error al consultar el plan de estudios: "+numero+" del programa "+programa,e);
        }
        return plan;
    }

    @Override
    public ProgramaAcademico consultarProgramaAcademicoPorNombre(String nombre) throws PersistenceException {
        ProgramaAcademico plan=null;
        try{
            plan=estudiante.consultarProgramaAcademicoPorNombre(nombre);
        }catch(Exception e){
            throw new PersistenceException("Error al consultar el programa "+nombre,e);
        }
        return plan;
    }
    
    @Override 
    public List<Solicitud> consultarSolicitudes() throws PersistenceException {
        List<Solicitud> solicitudes=null;
        try{
            solicitudes=estudiante.cargarSolicitudes();
        }catch(Exception e){
            throw new PersistenceException("Error al consultar los programas academicos",e);
        }
        return solicitudes;
    }
    
    @Override
    public List<Solicitud> consultRequest(String consejero) throws PersistenceException{
        List<Solicitud> solicitudes;
        try{
            solicitudes=estudiante.consultRequest(consejero);
        }catch (Exception e){
            throw new PersistenceException("Error al consultar las solicitudes",e);
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
