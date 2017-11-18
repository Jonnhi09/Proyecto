/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.PlanDeEstudios;
import com.projectKepler.services.entities.ProgramaAcademico;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author danielagonzalez
 */
public class ServiciosCancelacionesImpl implements ServiciosCancelaciones{

    
    @Inject 
    private Algorithm algo ;
    
    @Inject 
    private GraphRectificator gRec;
    
    @Inject
    private EstudianteDAO estudiante;
    
    @Transactional
    @Override
    public List<Estudiante> cargarEstudiantes() throws ExcepcionServiciosCancelaciones {
        List<Estudiante> estudiantes=null;
        try{
            estudiantes=estudiante.loadAllEstudiantes();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return estudiantes;
    }
    
    
    @Transactional
    @Override
    public String consultarPlanDeEstudioByIdEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        String programa="";
        try{
            programa=estudiante.loadPlanDeEstudio(codigo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return programa;
        
    }

    @Transactional
    @Override
    public List<CourseStudent> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<Syllabus> planes=null;
        try{
            planes=obtenerSyllabusEstudiante(codigoEstudiante);
        }catch (ExcepcionServiciosCancelaciones e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e); 
        }
        for (CourseStudent c: planes.get(0).getCourses()){
            String cadena="V";
            char estado=cadena.charAt(0);
            if (c.getEstado()==estado){
                asignaturas.add(c);
            }
        }
        return asignaturas;
    }
    
    @Transactional
    @Override
    public String obtenerProyeccionByEstudiante(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        String impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s, s), s)[1];
        return impacto;
    }

    @Transactional
    @Override
    public String consultarImpactoByEstudianteAsignatura(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String impacto=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            impacto=estudiante.consultImpactById(codigoEstudiante, nemonicoAsignatura);
            if (impacto==null){
                impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s, s), s)[0];
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return impacto;
    }
    
    @Transactional
    @Override
    public String consultarProyeccionByEstudianteAsignatura(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String impacto="";
        try{
            impacto=estudiante.consultProyectionById(codigoEstudiante, nemonicoAsignatura);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return impacto;
    }

    @Transactional
    @Override
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        List<Syllabus> planesDeEstudio= new ArrayList<Syllabus>();
        String programa="";
        String syllabusEstudiante="";
        try{
            syllabusEstudiante=estudiante.loadPlanDeEstudio(codigo);
            programa=estudiante.loadSyllabusProgramaById(codigo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        Gson plan= new Gson();
        Syllabus planEstudiante= plan.fromJson(syllabusEstudiante, Syllabus.class);
        Syllabus syllabusPrograma= plan.fromJson(programa, Syllabus.class);
        planesDeEstudio.add(planEstudiante);
        planesDeEstudio.add(syllabusPrograma);
        
        return planesDeEstudio;
    }
    @Transactional
    @Override
    public void actualizarJustificacionById(int id, String justificacion, String materia) throws ExcepcionServiciosCancelaciones {
        Syllabus planE=obtenerSyllabusEstudiante(id).get(0);
        Estudiante student=estudiante.loadEstudianteById(id);
        boolean acuse=false;
        for (CourseStudent c:planE.getCourses()){
            if(c.getNombre().equals(materia)){
                for (int i:c.getHistorialNotas()){
                    if(i==-1){
                        acuse=true;
                    }
                }
            }
        }
        if (student.getNumeroMatriculas()<3){
            acuse=true;
        }
        int numero=estudiante.loadSolicitudes().size()+1;
        String impacto=consultarImpactoByEstudianteAsignatura(id, materia);
        String proyeccion=obtenerProyeccionByEstudiante(id, materia);
        estudiante.updateJustification(id, materia, justificacion, numero, acuse, impacto,proyeccion);
        student=estudiante.loadEstudianteById(id);
        
    }   
    
    @Transactional
    @Override
    public void actualizarNumeroMaximoDeCreditos(int creditos) throws ExcepcionServiciosCancelaciones{
        try{
            estudiante.updateCredits(creditos);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Transactional
    @Override
    public int consultarNumeroMaximoDeCreditos() throws ExcepcionServiciosCancelaciones{
        int credits=0;
        try{
            credits=estudiante.consultCredits();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return credits;
    }
    
    @Transactional
    @Override
    public Estudiante consultarEstudianteByCorreo(String correo) throws ExcepcionServiciosCancelaciones{
        Estudiante student=null;
        try{
            student=estudiante.consultStudentByEmail(correo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return student;
    }
    
    @Transactional
    @Override
    public List<CourseStudent> consultarAsignaturasSinSolicitudByIdEStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones{
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<String> asig=null;
        try{
            asig=estudiante.consultCourse(codigoEstudiante);
            if (asig==null){
                asignaturas=consultarAsignaturasByIdEstudiante(codigoEstudiante);
            }else{
                for (CourseStudent c:consultarAsignaturasByIdEstudiante(codigoEstudiante)){
                    for(String a:asig){
                        if(!(c.getNombre().equals(a))){
                            asignaturas.add(c);
                        }
                    }
                }
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return asignaturas;
    }
    
    @Override
    public List<ProgramaAcademico> consultarTodosProgramasAcademicos() throws ExcepcionServiciosCancelaciones {
        List<ProgramaAcademico> programas=null;
        try{
            programas=estudiante.consultarProgramasAcademicos();
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return programas;
    }

    @Override
    public Syllabus consultarPlanDeEstudios(String programa, int planDeEstudios) throws ExcepcionServiciosCancelaciones {
        PlanDeEstudios plan=null;
        try{
            plan=estudiante.consultarPlanDeEstudios(programa, planDeEstudios);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        Gson gson= new Gson();
        Syllabus syllabus= gson.fromJson(plan.getContenido(), Syllabus.class);
        return syllabus;
    }

    @Override
    public ArrayList<Integer> consultarPlanesDeEstudiosPorPrograma(String programa) throws ExcepcionServiciosCancelaciones {
        ArrayList<Integer> planes= new ArrayList<>();
        ProgramaAcademico programaAc = null;
        try{
            programaAc=estudiante.consultarProgramaAcademicoPorNombre(programa);
            
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        for (PlanDeEstudios p:programaAc.getPlanDeEstudio()){
            planes.add(p.getId());
        }
        return planes;
    }
}
