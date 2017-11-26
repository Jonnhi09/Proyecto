/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.projectkepler.persistence.EstudianteDAO;
import com.projectkepler.persistence.SolicitudDAO;
import com.projectkepler.persistence.UniversidadDAO;
import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.algorithm.Algorithm;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import java.lang.reflect.Type;
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
    
    @Inject
    private SolicitudDAO solicitud;
    
    @Inject
    private UniversidadDAO universidad;
    
    
    @Transactional
    @Override
    public String consultarPlanDeEstudioByIdEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        String programa="";
        List<CourseStudent> asignaturas=null;
        try{
            programa=estudiante.loadEstudianteById(codigo).getAsignaturas();
            Gson g = new Gson();
            Type materias = new TypeToken<List<CourseStudent>>(){}.getType(); 
            asignaturas=g.fromJson(programa, materias);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        CourseStudent[] materiasPlan=consultarPlanDeEstudios(estudiante.loadEstudianteProgramaById(codigo),estudiante.loadEstudianteVersionById(codigo)).getCourses();
        ArrayList<String> materiasEnEstudiante=new ArrayList<>();
        for (CourseStudent c:asignaturas){
            materiasEnEstudiante.add(c.getNemonico());
        }
        for (int j=0;j<materiasPlan.length;j++){
            for (int i=0;i<asignaturas.size();i++){
                if (materiasPlan[j].getNemonico().equals(asignaturas.get(i).getNemonico())){
                    asignaturas.get(i).setCoReq(materiasPlan[j].getCoReq());
                    asignaturas.get(i).setPreReq(materiasPlan[j].getPreReq());
                    asignaturas.get(i).setCreditos(materiasPlan[j].getCreditos());
                }else{
                    if ( !(materiasEnEstudiante.contains(materiasPlan[j].getNemonico())) ){
                        asignaturas.add(materiasPlan[j]);
                        materiasEnEstudiante.add(materiasPlan[j].getNemonico());
                    }
                }  
            }  
        }
        CourseStudent[] asignaturasSyllabus = new CourseStudent[asignaturas.size()];
        asignaturas.toArray(asignaturasSyllabus);
        Gson p = new GsonBuilder().setPrettyPrinting().create();
        String update= p.toJson(new Syllabus(estudiante.loadEstudianteProgramaById(codigo),estudiante.loadEstudianteVersionById(codigo),universidad.consultarPlanDeEstudios(estudiante.loadEstudianteProgramaById(codigo), estudiante.loadEstudianteVersionById(codigo)).getTotalCreditos(),asignaturasSyllabus));
        return update;
        
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
    public String consultarImpactoByEstudianteAsignatura(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String impacto=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            impacto=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura).getImpacto();
            if (impacto==null){
                impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s)[0];
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return impacto;
    }
    
    @Transactional
    @Override
    public String consultarProyeccionByEstudianteAsignatura(int codigoEstudiante, String nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String proyeccion=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            proyeccion=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura).getProyeccion();
            if (proyeccion==null){
                proyeccion=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s)[1];
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return proyeccion;
    }

    @Transactional
    @Override
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        List<Syllabus> planesDeEstudio= new ArrayList<Syllabus>();
        String programa="";
        String syllabusEstudiante="";
        try{
            syllabusEstudiante=consultarPlanDeEstudioByIdEstudiante(codigo);
            programa=universidad.loadSyllabusByStudent(codigo).getContenido();
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
        int numero=solicitud.consultarSolicitudes().size()+1;
        String impacto=consultarImpactoByEstudianteAsignatura(id, materia);
        String proyeccion=consultarProyeccionByEstudianteAsignatura(id, materia);
        solicitud.updateJustification(id, materia, justificacion, numero, acuse, impacto,proyeccion);
        student=estudiante.loadEstudianteById(id);
        
    }   
    
    @Transactional
    @Override
    public void actualizarNumeroMaximoDeCreditos(int creditos) throws ExcepcionServiciosCancelaciones{
        try{
            universidad.updateCredits(creditos);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Transactional
    @Override
    public int consultarNumeroMaximoDeCreditos() throws ExcepcionServiciosCancelaciones{
        int credits=0;
        try{
            credits=universidad.consultUniversity().getTotalCredits();
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
        List<CourseStudent> asig=null;
        try{
            asig=solicitud.loadCoursesById(codigoEstudiante);
            if (asig==null){
                asignaturas=consultarAsignaturasByIdEstudiante(codigoEstudiante);
            }else{
                for (CourseStudent c:consultarAsignaturasByIdEstudiante(codigoEstudiante)){
                    for(CourseStudent a:asig){
                        if(!(c.getNemonico().equals(a.getNemonico()))){
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
            programas=universidad.consultarProgramasAcademicos();
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return programas;
    }

    @Override
    public Syllabus consultarPlanDeEstudios(String programa, int planDeEstudios) throws ExcepcionServiciosCancelaciones {
        PlanDeEstudios plan=null;
        try{
            plan=universidad.consultarPlanDeEstudios(programa, planDeEstudios);
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
            programaAc=universidad.consultarProgramaAcademicoPorNombre(programa);
            
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        for (PlanDeEstudios p:programaAc.getPlanDeEstudio()){
            planes.add(p.getId());
        }
        return planes;
    }
    
    @Override
    public List<Solicitud> consultarSolicitudesDeCancelaciones(String consejero) throws ExcepcionServiciosCancelaciones{
        List<Solicitud> solicitudes;
        solicitudes=solicitud.consultRequest(consejero);
        if (solicitudes==null){
            throw new ExcepcionServiciosCancelaciones("El consejero no tiene solicitudes");
        }
        return solicitudes ;
    }
    
    @Override
    public Estudiante consultarEstudiantePorSolicitud(int numero) throws ExcepcionServiciosCancelaciones{
        Estudiante student;
        student=estudiante.consultStudentByRequest(numero);
        if (student==null){
            throw new ExcepcionServiciosCancelaciones("La solicitud "+numero+" no existe");
        }
        
        return student;
    }

    @Override
    public void actualizarComentariosSolicitud(int numero,String comentarios) throws ExcepcionServiciosCancelaciones {
        try{
            if (comentarios.isEmpty()){
                throw new ExcepcionServiciosCancelaciones("Los comentarios no deben ser vacios");
            }   
            solicitud.updateComentariosSolicitud(numero,comentarios);
        }catch(PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la solicitud "+numero);
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al alctualizar la solicitud numero "+numero);
        }
        
        
    }
}
