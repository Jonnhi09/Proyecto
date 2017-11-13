/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.Asignatura;
import com.projectKepler.services.entities.Course;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public List<Course> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        List<Course> asignaturas=new ArrayList<Course>();
        List<Syllabus> planes=null;
        try{
            planes=obtenerSyllabusEstudiante(codigoEstudiante);
        }catch (ExcepcionServiciosCancelaciones e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e); 
        }
        for (Course c: planes.get(0).getCourses()){
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
    public String obtenerImpactoByEstudiante(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        String impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s, s), s)[0];
        return impacto;
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
        String impacto="";
        try{
            impacto=estudiante.consultImpactById(codigoEstudiante, nemonicoAsignatura);
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
        for (Course c:planE.getCourses()){
            if(c.getNombre()==materia){
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
        String impacto=obtenerImpactoByEstudiante(id, materia);
        String proyeccion=obtenerProyeccionByEstudiante(id, materia);
        estudiante.updateJustification(id, materia, justificacion, numero, acuse, impacto,proyeccion);
        student=estudiante.loadEstudianteById(id);
        
    }    
}

